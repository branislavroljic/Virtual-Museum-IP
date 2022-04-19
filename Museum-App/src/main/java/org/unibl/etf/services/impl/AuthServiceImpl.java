package org.unibl.etf.services.impl;

import io.jsonwebtoken.Claims;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.unibl.etf.exceptions.InvalidLoginException;
import org.unibl.etf.exceptions.NotFoundException;
import org.unibl.etf.models.dto.UserDetailsImpl;
import org.unibl.etf.models.entities.UserEntity;
import org.unibl.etf.models.enums.UserRole;
import org.unibl.etf.models.requests.AuthenticationRequest;
import org.unibl.etf.models.requests.RefreshTokenRequest;
import org.unibl.etf.models.responses.AuthenticationResponse;
import org.unibl.etf.models.responses.RefreshTokenResponse;
import org.unibl.etf.repositories.UserEntityRepository;
import org.unibl.etf.services.AuthService;
import org.unibl.etf.services.UserService;
import org.unibl.etf.util.JwtUtil;

import javax.xml.bind.DatatypeConverter;
import java.security.SecureRandom;

@Service
public class AuthServiceImpl implements AuthService {


    final
    AuthenticationManager authenticationManager;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final UserEntityRepository userEntityRepository;
    private final SecureRandom secureRandom = new SecureRandom();

    public AuthServiceImpl(AuthenticationManager authenticationManager, UserService userService, ModelMapper modelMapper, UserEntityRepository userEntityRepository) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.userEntityRepository = userEntityRepository;
    }

    @Override
    public AuthenticationResponse login(AuthenticationRequest request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        UserDetailsImpl jwtUser = (UserDetailsImpl) authentication.getPrincipal();

        UserEntity user = userService.findById(jwtUser.getId());
        user.setLoggedIn(true);
        userEntityRepository.save(user);

        AuthenticationResponse response = modelMapper.map(user, AuthenticationResponse.class);
        response.setJwtToken(JwtUtil.generateJwt(user));
        response.setRefreshToken(JwtUtil.generateRefresh(user));
        return response;
    }

    @Override
    public void logout(Authentication principal) {

        UserEntity user = userService.findById(((UserDetailsImpl)principal.getPrincipal()).getId());
        user.setLoggedIn(false);
        userEntityRepository.save(user);
    }

    @Override
    public RefreshTokenResponse refreshToken(RefreshTokenRequest request) {
        //get refresh token
        String refreshToken = request.getRefreshToken();
        Claims claims = JwtUtil.parseJwt(refreshToken);

        //get user from claims
        UserEntity user = userService.findByUsername(claims.getSubject());

        //generate new JWT
        String jwt = JwtUtil.generateJwt(user);

        RefreshTokenResponse response = new RefreshTokenResponse();
        response.setJwtToken(jwt);
        return response;
    }

    @Override
    public String generateOtpForAdmin(Authentication principal) {
        UserEntity user = userEntityRepository.findUserEntityByUsernameAndRole(principal.getName(), UserRole.ADMIN).orElseThrow(() -> new NotFoundException("User does not exist or access is denied!"));
        String otp = null;
        user.setToken(otp = DatatypeConverter.printHexBinary(generateOtp()));
        userEntityRepository.save(user);
        return otp;
    }

    private byte[] generateOtp(){
        byte[] otp = new byte[128];
        secureRandom.nextBytes(otp);
        return otp;
    }
}
