package com.pengyu.magnet.service.user;

import com.pengyu.magnet.domain.User;
import com.pengyu.magnet.dto.UserLoginRequest;
import com.pengyu.magnet.dto.UserRegisterRequest;
import com.pengyu.magnet.dto.UserRequest;
import com.pengyu.magnet.dto.UserResponse;
import com.pengyu.magnet.exception.ApiException;
import com.pengyu.magnet.exception.ResourceNotFoundException;
import com.pengyu.magnet.mapper.UserMapper;
import com.pengyu.magnet.repository.UserRepository;
import com.pengyu.magnet.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Implement business features for User domain
 */

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;


    /**
     * Register
     * @param registerRequest
     * @return
     */
    @Override
    public UserResponse register(UserRegisterRequest registerRequest) {

        // Check if email already exist
        User byEmail = userRepository.findByEmail(registerRequest.getEmail());
        if(byEmail != null)
            throw new ApiException("The email is already existed!");

        // Do register
        User user = User.builder()
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(registerRequest.getRole())
                .createdAt(LocalDateTime.now())
                // During Dev stage, set default status to ACTIVE
                .status(User.Status.ACTIVE).build();
        user =  userRepository.save(user);

        UserResponse userResponse = UserMapper.INSTANCE.mapUserToUserResponse(user);
        return userResponse;
    }


    /**
     * Login
     * @param loginRequest
     * @return
     */
    @Override
    public UserResponse login(UserLoginRequest loginRequest) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        User user = userRepository.findByEmail(loginRequest.getEmail());
        String jwtToken = jwtService.generateToken(user);
        UserResponse authResponse = UserMapper.INSTANCE.mapUserToUserResponse(user);
        authResponse.setToken(jwtToken);
        return authResponse;
    }

    @Override
    public long count() {
        return userRepository.count();
    }

    @Override
    public List<UserResponse> findAll(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(user -> UserMapper.INSTANCE.mapUserToUserResponse(user))
                .toList();
    }

    /**
     * Find one user
     * @param id
     * @return
     */
    @Override
    public UserResponse find(Long id) {
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No such user found with id " + id));
        return UserMapper.INSTANCE.mapUserToUserResponse(user);
    }

    /**
     * Add or update user
     * @param userRequest
     * @return
     */
    @Override
    public UserResponse save(UserRequest userRequest) {
        User oldUser = null;
        if(userRequest.getId() != null) {
            oldUser = userRepository
                    .findById(userRequest.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("No such user found with id "+ userRequest.getId()));
        }
        // If update
        if(oldUser != null) {
            oldUser.setRole(userRequest.getRole());
            oldUser.setEmail(userRequest.getEmail());
            oldUser.setStatus(userRequest.getStatus());
            if(StringUtils.isNotBlank(userRequest.getPassword()))
                oldUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));

            // Save
            userRepository.save(oldUser);

            // Return
            UserResponse userResponse = UserMapper.INSTANCE.mapUserToUserResponse(oldUser);
            return userResponse;
        }

        // If create
        User user = User.builder()
                .id(userRequest.getId())
                .email(userRequest.getEmail())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .role(userRequest.getRole())
                .status(userRequest.getStatus())
                .createdAt(LocalDateTime.now())
                // During Dev stage, set default status to ACTIVE
                .status(User.Status.ACTIVE).build();
        // Save
        user =  userRepository.save(user);

        UserResponse userResponse = UserMapper.INSTANCE.mapUserToUserResponse(user);
        return userResponse;
    }

    @Override
    public List<Long> getRegistrationCounts() {
        return userRepository.getRegistrationCounts();
    }
}
