package org.unibl.etf.models.entities;

import lombok.*;
import org.hibernate.annotations.Type;
import org.unibl.etf.models.enums.UserRole;
import org.unibl.etf.models.enums.UserStatus;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@Table(name = "user", schema = "ip_2022", catalog = "")
public class UserEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "name", nullable = false, length = 255)
    private String name;
    @Basic
    @Column(name = "surname", nullable = false, length = 255)
    private String surname;
    @Basic
    @Column(name = "username", nullable = false, length = 45)
    private String username;
    @Basic
    @Column(name = "password", nullable = false, length = 100)
    private String password;
    @Basic
    @Column(name = "email", nullable = false, length = 255)
    private String email;
    @Basic
    @Column(name = "token", nullable = true, length = 255)
    private String token;
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('USER', 'ADMIN')")
    private UserRole role;
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('REQUESTED', 'ACTIVE', 'BLOCKED')")
    private UserStatus status;
    @Basic
    @Column(name = "logged_in", nullable = false)
    private Boolean loggedIn;
    @OneToMany(mappedBy = "user")
    private List<TicketEntity> tickets;

}
