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
    public ResponseEntity<?> assignRole(@RequestParam Long userId, @RequestParam Long roleId) {
        try {
            userService.assignRoleToUser(userId, roleId);
            return ResponseEntity.ok("Role assigned successfully");
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (BadRequestException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Internal server error");
        }
    }

    @PutMapping("/unassign-role")
    public ResponseEntity<?> unassignRole(@RequestParam Long userId, @RequestParam Long roleId) {
        try {
            userService.unassignRoleToUser(userId, roleId);
            return ResponseEntity.ok("Role unassigned successfully");
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (BadRequestException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Internal server error");
        }
    }
}
