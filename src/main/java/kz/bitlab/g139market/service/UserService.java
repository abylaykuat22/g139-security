package kz.bitlab.g139market.service;

import kz.bitlab.g139market.dto.*;
import kz.bitlab.g139market.entity.Role;
import kz.bitlab.g139market.entity.User;
import kz.bitlab.g139market.exception.NotFoundException;
import kz.bitlab.g139market.exception.UserAlreadyExistsException;
import kz.bitlab.g139market.mapper.UserMapper;
import kz.bitlab.g139market.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final JwtService jwtService;

    public AuthResponse register(UserCreateDto dto) throws BadRequestException {
        log.info("STARTED register for username {}", dto.getUsername());
        User existUser = userRepository.findByUsername(dto.getUsername()).orElse(null);
        if (existUser != null) {
            throw new BadRequestException("Username already exists");
        }

        User user = UserMapper.INSTANCE.toEntity(dto);
        user.setPasswordHash(passwordEncoder.encode(dto.getPassword()));

        Role role = roleService.getUserRole();
        user.setRoles(Set.of(role));

        userRepository.save(user);

        log.info("COMPLETED successfully registration for {}", dto.getUsername());
        return generateTokens(user);
    }

    private AuthResponse generateTokens(User user) {
        return AuthResponse.builder()
                .accessToken(jwtService.generateAccessToken(user))
                .refreshToken(jwtService.generateRefreshToken(user))
                .build();
    }

    public UserResponseDto findByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(UserMapper.INSTANCE::toDto)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    public void changePassword(ChangePasswordDto dto) throws BadRequestException {
        User user = userRepository.findById(dto.getUserId()).orElseThrow(() -> new NotFoundException("User not found"));

        if (!passwordEncoder.matches(dto.getCurrentPassword(), user.getPasswordHash())) {
            throw new BadRequestException("Incorrect current password");
        }

        user.setPasswordHash(passwordEncoder.encode(dto.getNewPassword()));

        userRepository.save(user);
    }

    public void assignRoleToUser(Long userId, Long roleId) throws BadRequestException {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        Role role = roleService.getRoleById(roleId);

        if (user.getRoles().contains(role)) {
            throw new BadRequestException("User already has this role");
        }

        user.getRoles().add(role);

        userRepository.save(user);
    }

    public void unassignRoleToUser(Long userId, Long roleId) throws BadRequestException {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        Role role = roleService.getRoleById(roleId);

        if (!user.getRoles().contains(role)) {
            throw new BadRequestException("User does not have this role");
        }

        user.getRoles().remove(role);

        userRepository.save(user);
    }

    public AuthResponse login(AuthRequest request) throws BadRequestException {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new BadRequestException("Username or password is incorrect"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new BadRequestException("Username or password is incorrect");
        }

        return generateTokens(user);
    }

    public AuthResponse refreshToken(RefreshTokenDto dto) throws BadRequestException {
        if (dto.getRefreshToken() == null) {
            throw new BadRequestException("Refresh token is missing");
        }

        String username = jwtService.extractUsername(dto.getRefreshToken());
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (!jwtService.isTokenValid(dto.getRefreshToken(), user)) {
            throw new BadRequestException("Invalid refresh token");
        }

        return generateTokens(user);
    }

    public User getCurrentUser() {
//        if (SecurityContextHolder.getContext() != null && SecurityContextHolder.getContext().getAuthentication() != null) {
//            return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        }
        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .map(User.class::cast)
                .orElseThrow(() -> new NotFoundException("Current user not found"));
    }

    public UserResponseDto updateUser(UserUpdateDto dto) {
        User currentUser = getCurrentUser();
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new UserAlreadyExistsException("Username already exists");
        }

        safetySaveValue(dto.getUsername(), currentUser::setUsername);
        safetySaveValue(dto.getBirthdate(), currentUser::setBirthdate);
        safetySaveValue(dto.getFullName(), currentUser::setFullName);


        userRepository.save(currentUser);

        return UserMapper.INSTANCE.toDto(currentUser);
    }

    public <T> void safetySaveValue(T value, Consumer<T> consumer) {
        if (value != null) consumer.accept(value);
    }

    public void deleteCurrentUser() {
        User currentUser = getCurrentUser();
        userRepository.delete(currentUser);
    }
}
