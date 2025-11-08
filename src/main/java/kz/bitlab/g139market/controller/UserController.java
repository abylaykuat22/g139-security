package kz.bitlab.g139market.controller;

import kz.bitlab.g139market.dto.UserResponseDto;
import kz.bitlab.g139market.exception.NotFoundException;
import kz.bitlab.g139market.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        if (authentication == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        UserResponseDto dto = userService.findByUsername(authentication.getName());
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/find-by-username")
    public ResponseEntity<?> findByUsername(@RequestParam String username) {
        try {
            return ResponseEntity.ok(userService.findByUsername(username));
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
