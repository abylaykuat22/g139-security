package kz.bitlab.g139market.service;

import kz.bitlab.g139market.dto.*;
import kz.bitlab.g139market.entity.Role;
import kz.bitlab.g139market.entity.User;
import kz.bitlab.g139market.exception.NotFoundException;
import kz.bitlab.g139market.mapper.UserMapper;
import kz.bitlab.g139market.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    public AuthResponse register(UserCreateDto dto) throws BadRequestException {
        User existUser = userRepository.findByUsername(dto.getUsername()).orElse(null);
        if (existUser != null) {
            throw new BadRequestException("Username already exists");
        }

        User user = UserMapper.INSTANCE.toEntity(dto);
        user.setPasswordHash(passwordEncoder.encode(dto.getPassword()));

        Role role = roleService.getUserRole();
        user.setRoles(Set.of(role));

        user = userRepository.save(user);

        return generateTokens(user);
    }

    private AuthResponse generateTokens(User user) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());

        String accessToken = jwtService.generateAccessToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public UserResponseDto findByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(u -> UserMapper.INSTANCE.toDto(u))
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

    public AuthResponse login(AuthRequest dto) throws BadRequestException {
        User user = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new BadRequestException("Invalid username or password"));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPasswordHash())) {
            throw new BadRequestException("Invalid username or password");
        }

        return generateTokens(user);
    }
}
