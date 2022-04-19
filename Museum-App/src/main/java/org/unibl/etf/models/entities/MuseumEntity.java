package org.unibl.etf.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@Table(name = "museum", schema = "ip_2022", catalog = "")
public class MuseumEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "name", nullable = false, length = 255)
    private String name;
    @Basic
    @Column(name = "address", nullable = false, length = 255)
    private String address;
    @Basic
    @Column(name = "tel", nullable = false, length = 20)
    private String tel;
    @Basic
    @Column(name = "city", nullable = false, length = 255)
    private String city;
    @Basic
    @Column(name = "contry", nullable = false, length = 255)
    private String country;
    @Basic
    @Column(name = "geolat", nullable = false, precision = 6)
    private Double geolat;
    @Basic
    @Column(name = "geolng", nullable = false, precision = 6)
    private Double geolng;
    @Basic
    @Column(name = "type", nullable = false, length = 45)
    private String type;
    @JsonIgnore
    @OneToMany(mappedBy = "museum")
    private List<VirtualVisitEntity> virtualVisits;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MuseumEntity museum = (MuseumEntity) o;
        return Objects.equals(id, museum.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
