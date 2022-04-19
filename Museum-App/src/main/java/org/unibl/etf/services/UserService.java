package org.unibl.etf.services;


import org.unibl.etf.models.entities.UserEntity;
import org.unibl.etf.models.requests.RegistrationRequest;

public interface UserService {

    UserEntity getUserByUsernameAndPassword(String username, String password);

    UserEntity findByUsername(String username);
    UserEntity findById(Integer id);

    void register(RegistrationRequest request);

    Integer countLoggedInUsers();
    Integer countRegisteredUsers();
}
