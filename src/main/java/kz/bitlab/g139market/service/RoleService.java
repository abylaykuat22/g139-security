package kz.bitlab.g139market.service;

import kz.bitlab.g139market.entity.Role;
import kz.bitlab.g139market.exception.NotFoundException;
import kz.bitlab.g139market.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Role getUserRole() {
        return roleRepository.findUserRole();
    }

    public Role getRoleById(Long id) {
        return roleRepository.findById(id).orElseThrow(() -> new NotFoundException("Role not found"));
    }
}
