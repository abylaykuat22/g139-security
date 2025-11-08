package kz.bitlab.g139market.repository;

import kz.bitlab.g139market.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("SELECT r FROM Role r WHERE r.name = 'ROLE_USER'")
    Role findUserRole();
}
