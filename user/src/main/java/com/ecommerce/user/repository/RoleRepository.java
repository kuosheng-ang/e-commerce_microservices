package com.ecommerce.user.repository;

import com.ecommerce.user.models.Role;
import com.ecommerce.user.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//@EnableMongoRepositories

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByRoleName(String roleName);
}





