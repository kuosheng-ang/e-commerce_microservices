package com.ecommerce.user.services;

import com.ecommerce.user.dto.AddressDTO;
import com.ecommerce.user.dto.UserRequest;
import com.ecommerce.user.dto.UserResponse;
import com.ecommerce.user.models.Address;
import com.ecommerce.user.models.User;
import com.ecommerce.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public interface UserService {
    //private final UserRepository userRepository;



    List<UserResponse> fetchAllUsers();

    void addUser(UserRequest userRequest);

    /*public Optional<UserResponse> fetchUser(String id) {
        return userRepository.findById(String.valueOf(id))
                .map(this::mapToUserResponse);
    }*/
    Optional<UserResponse> fetchUser(String id);

    boolean updateUser(String id, UserRequest updatedUserRequest);


}
