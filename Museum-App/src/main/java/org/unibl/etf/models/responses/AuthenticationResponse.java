package org.unibl.etf.models.responses;

import lombok.*;
import org.unibl.etf.models.enums.UserRole;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private String name;
    private String surname;
    private String username;
    private String email;
    private UserRole role;
    private String jwtToken;
    private String refreshToken;

}
