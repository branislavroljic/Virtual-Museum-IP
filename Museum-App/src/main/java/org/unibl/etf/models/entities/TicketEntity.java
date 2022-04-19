package org.unibl.etf.models.entities;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ticket", schema = "ip_2022", catalog = "")
public class TicketEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "number", nullable = false, length = 255)
    private String number;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "virtual_visit_id", referencedColumnName = "id", nullable = false)
    private VirtualVisitEntity virtualVisit;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserEntity user;

    public TicketEntity(String number, VirtualVisitEntity virtualVisit, UserEntity user) {
        this.number = number;
        this.virtualVisit = virtualVisit;
        this.user = user;
    }
}
