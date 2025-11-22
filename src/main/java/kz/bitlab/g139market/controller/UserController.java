package kz.bitlab.g139market.controller;

import kz.bitlab.g139market.dto.UserResponseDto;
import kz.bitlab.g139market.dto.UserUpdateDto;
import kz.bitlab.g139market.exception.NotFoundException;
import kz.bitlab.g139market.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser() {
        return ResponseEntity.ok(userService.getCurrentUser());
    }

    @GetMapping("/find-by-username")
    public ResponseEntity<?> findByUsername(@RequestParam String username) {
        return ResponseEntity.ok(userService.findByUsername(username));
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody UserUpdateDto dto) {
        UserResponseDto updatedUser = userService.updateUser(dto);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser() {
        userService.deleteCurrentUser();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
