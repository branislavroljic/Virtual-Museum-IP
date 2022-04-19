package org.unibl.etf.models.entities;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "log", schema = "ip_2022", catalog = "")
public class LogEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "user", nullable = false, length = 45)
    private String username;
    @Basic
    @Column(name = "type", nullable = false, length = 45)
    private String type;
    @Basic
    @Column(name = "message", nullable = false, length = 255)
    private String message;
    @Basic
    @Column(name = "dateTime", nullable = false)
    private Timestamp dateTime;

    public LogEntity(String username, String type, String message, Timestamp dateTime) {
        this.username = username;
        this.type = type;
        this.message = message;
        this.dateTime = dateTime;
    }
}
