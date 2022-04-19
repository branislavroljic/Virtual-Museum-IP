package org.unibl.etf.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.unibl.etf.models.dto.UserDetailsImpl;
import org.unibl.etf.models.entities.UserEntity;
import org.unibl.etf.models.enums.UserStatus;
import org.unibl.etf.repositories.UserEntityRepository;
import org.unibl.etf.services.JwtUserDetailsService;

import java.util.Collections;

@Service
public class JwtUserDetailsServiceImpl implements JwtUserDetailsService {

    private final
    UserEntityRepository userEntityRepository;
    private final ModelMapper modelMapper;

    public JwtUserDetailsServiceImpl(UserEntityRepository userEntityRepository, ModelMapper modelMapper) {
        this.userEntityRepository = userEntityRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
        return modelMapper.map(userEntityRepository.findUserEntityByUsernameAndStatus(username, UserStatus.ACTIVE).orElseThrow(() -> new UsernameNotFoundException(username)), UserDetailsImpl.class);
    }
}
