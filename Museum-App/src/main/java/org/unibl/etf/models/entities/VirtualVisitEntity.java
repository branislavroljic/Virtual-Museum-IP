package org.unibl.etf.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "virtual_visit", schema = "ip_2022", catalog = "")
public class VirtualVisitEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "date", nullable = false)
    private Date date;
    @Basic
    @Column(name = "start_time", nullable = false)
    private Time startTime;
    @Basic
    @Column(name = "duration", nullable = false)
    private Integer duration;
    @Basic
    @Column(name = "price", nullable = false, precision = 2)
    private Double price;
    @JsonIgnore
    @OneToMany(mappedBy = "virtualVisit")
    private List<TicketEntity> tickets;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "museum_id", referencedColumnName = "id", nullable = false)
    private MuseumEntity museum;

}
