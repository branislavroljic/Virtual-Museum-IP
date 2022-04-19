package org.unibl.etf.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.unibl.etf.exceptions.ConflictException;
import org.unibl.etf.exceptions.InvalidLoginException;
import org.unibl.etf.exceptions.NotFoundException;
import org.unibl.etf.models.entities.UserEntity;
import org.unibl.etf.models.enums.UserRole;
import org.unibl.etf.models.enums.UserStatus;
import org.unibl.etf.models.requests.RegistrationRequest;
import org.unibl.etf.repositories.UserEntityRepository;
import org.unibl.etf.services.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final UserEntityRepository userEntityRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserEntityRepository userEntityRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userEntityRepository = userEntityRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserEntity getUserByUsernameAndPassword(String username, String password) {
       return userEntityRepository.findUserEntityByUsernameAndPassword(username, password).orElseThrow(InvalidLoginException::new);
    }

    @Override
    public UserEntity findByUsername(String username) {
       return userEntityRepository.findUserEntityByUsername(username).orElseThrow(() -> new NotFoundException("Username not found!"));
    }

    @Override
    public UserEntity findById(Integer id) {
        return userEntityRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public void register(RegistrationRequest request) {
        if(userEntityRepository.findUserEntityByUsername(request.getUsername()).isPresent())
            throw new ConflictException("Username already exists!");

        UserEntity user = modelMapper.map(request, UserEntity.class);

        user.setRole(UserRole.USER);
        user.setStatus(UserStatus.REQUESTED);
        user.setLoggedIn(false);
        System.out.println(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userEntityRepository.save(user);
    }

    @Override
    public Integer countLoggedInUsers() {
       return userEntityRepository.countUserEntitiesByLoggedInTrue();
    }

    @Override
    public Integer countRegisteredUsers() {
        return userEntityRepository.countUserEntitiesByStatus(UserStatus.ACTIVE);
    }
}
