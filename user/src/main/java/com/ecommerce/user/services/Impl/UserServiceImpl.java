package com.ecommerce.user.services.Impl;

import com.ecommerce.user.dto.AddressDTO;
import com.ecommerce.user.dto.UserRequest;
import com.ecommerce.user.dto.UserResponse;
import com.ecommerce.user.models.Address;
import com.ecommerce.user.models.User;
import com.ecommerce.user.repository.UserRepository;
import com.ecommerce.user.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    //private final UserRepository userRepository;

    @Autowired
    private UserRepository userRepository;
//    private List<User> userList = new ArrayList<>();
//    private Long nextId = 1L;

    public List<UserResponse> fetchAllUsers(){
        return userRepository.findAll().stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }

    public void addUser(UserRequest userRequest){
//        user.setId(nextId++);
        User user = new User();
        updateUserFromRequest(user, userRequest);
        userRepository.save(user);
    }

    /*public Optional<UserResponse> fetchUser(String id) {
        return userRepository.findById(String.valueOf(id))
                .map(this::mapToUserResponse);
    }*/
    public Optional<UserResponse> fetchUser(String id) {
        return userRepository.findById(Long.valueOf(id))
                .map(this::mapToUserResponse);
    }

    public boolean updateUser(String id, UserRequest updatedUserRequest) {
        return userRepository.findById(Long.valueOf(id))
                .map(existingUser -> {
                    updateUserFromRequest(existingUser, updatedUserRequest);
                    userRepository.save(existingUser);
                    return true;
                }).orElse(false);
    }

    private void updateUserFromRequest(User user, UserRequest userRequest) {
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPhone(userRequest.getPhone());
        if (userRequest.getAddress() != null) {
            Address address = new Address();
            address.setStreet(userRequest.getAddress().getStreet());
            address.setState(userRequest.getAddress().getState());
            address.setZipcode(userRequest.getAddress().getZipcode());
            address.setCity(userRequest.getAddress().getCity());
            address.setCountry(userRequest.getAddress().getCountry());
            user.setAddresses(List.of(address));
        }
    }

    private UserResponse mapToUserResponse(User user){
        UserResponse response = new UserResponse();
        response.setId(user.getUserId());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        response.setRole(user.getRole());

        if (!user.getAddresses().isEmpty()) {
            AddressDTO addressDTO = new AddressDTO();
            addressDTO.setStreet(user.getAddresses().get(0).getStreet());
            addressDTO.setCity(user.getAddresses().get(0).getCity());
            addressDTO.setState(user.getAddresses().get(0).getState());
            addressDTO.setCountry(user.getAddresses().get(0).getCountry());
            addressDTO.setZipcode(user.getAddresses().get(0).getZipcode());
            response.setAddress(addressDTO);
        }
        return response;
    }
}
