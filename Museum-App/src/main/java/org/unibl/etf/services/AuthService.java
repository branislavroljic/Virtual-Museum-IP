package org.unibl.etf.services;

import org.springframework.security.core.Authentication;
import org.unibl.etf.models.requests.AuthenticationRequest;
import org.unibl.etf.models.requests.RefreshTokenRequest;
import org.unibl.etf.models.responses.AuthenticationResponse;
import org.unibl.etf.models.responses.RefreshTokenResponse;

public interface AuthService {

    AuthenticationResponse login(AuthenticationRequest request);

    void logout(Authentication principal);

    RefreshTokenResponse refreshToken(RefreshTokenRequest request);

    String generateOtpForAdmin(Authentication principal);
}
