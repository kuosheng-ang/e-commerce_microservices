package com.ecommerce.product.repositories;

import com.ecommerce.product.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserName(String username);

    Boolean existsByUserName(String username);

    Boolean existsByEmail(String email);

    List<User> findByProductProductName(String productName);
        // This method is not defined in the original code snippet, but it seems to be a placeholder.

}
