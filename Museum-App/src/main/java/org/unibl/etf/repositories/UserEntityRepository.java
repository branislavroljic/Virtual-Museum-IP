package org.unibl.etf.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.unibl.etf.models.entities.UserEntity;
import org.unibl.etf.models.enums.UserRole;
import org.unibl.etf.models.enums.UserStatus;

import java.util.Optional;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, Integer> {

    Optional<UserEntity> findUserEntityByUsernameAndPassword (String username, String password);
    Optional<UserEntity> findUserEntityByUsername(String username);
    Optional<UserEntity> findUserEntityByUsernameAndRole(String username, UserRole role);
    Optional<UserEntity>  findUserEntityByUsernameAndStatus(String username, UserStatus status);
    Integer countUserEntitiesByLoggedInTrue();
    Integer countUserEntitiesByStatus(UserStatus status);
}
