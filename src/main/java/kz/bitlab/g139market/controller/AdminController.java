package kz.bitlab.g139market.controller;

import kz.bitlab.g139market.exception.NotFoundException;
import kz.bitlab.g139market.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    @GetMapping
    public String welcome() {
        return "Welcome, Admin!";
    }

    @PutMapping("/assign-role")
    public ResponseEntity<?> assignRole(@RequestParam Long userId, @RequestParam Long roleId) throws BadRequestException {
        userService.assignRoleToUser(userId, roleId);
        return ResponseEntity.ok("Role assigned successfully");
    }

    @PutMapping("/unassign-role")
    public ResponseEntity<?> unassignRole(@RequestParam Long userId, @RequestParam Long roleId) throws BadRequestException {
        userService.unassignRoleToUser(userId, roleId);
        return ResponseEntity.ok("Role unassigned successfully");
    }
}
