package org.unibl.etf.admin_app.beans;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class MuseumBean implements Serializable {
    private String id;
    private String name;
    private String address;
    private String tel;
    private String city;
    private String country;
    private Double geolat;
    private Double geolng;
    private String type;

    public MuseumBean(String id, String name, String address, String tel, String city, String country, Double geolat, Double geolng, String type) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.tel = tel;
        this.city = city;
        this.country = country;
        this.geolat = geolat;
        this.geolng = geolng;
        this.type = type;
    }

    public MuseumBean(String name, String address, String tel, String city, String country, Double geolat, Double geolng, String type) {
        this.name = name;
        this.address = address;
        this.tel = tel;
        this.city = city;
        this.country = country;
        this.geolat = geolat;
        this.geolng = geolng;
        this.type = type;
    }

    public MuseumBean() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Double getGeolat() {
        return geolat;
    }

    public void setGeolat(Double geolat) {
        this.geolat = geolat;
    }

    public Double getGeolng() {
        return geolng;
    }

    public void setGeolng(Double geolng) {
        this.geolng = geolng;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MuseumBean that = (MuseumBean) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "MuseumBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", tel='" + tel + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", geolat=" + geolat +
                ", geolng=" + geolng +
                ", type='" + type + '\'' +
                '}';
    }
}
