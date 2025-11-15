package kz.bitlab.g139market.controller;

import kz.bitlab.g139market.dto.*;
import kz.bitlab.g139market.exception.NotFoundException;
import kz.bitlab.g139market.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserCreateDto dto) throws BadRequestException {
        AuthResponse response = userService.register(dto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) throws BadRequestException {
        AuthResponse response = userService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenDto dto) throws BadRequestException {
        AuthResponse response = userService.refreshToken(dto);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDto dto) throws BadRequestException {
        userService.changePassword(dto);
        return ResponseEntity.ok("Password changed successfully");
    }
}
