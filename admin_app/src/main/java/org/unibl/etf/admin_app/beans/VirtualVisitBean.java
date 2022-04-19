package org.unibl.etf.admin_app.beans;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.util.Objects;

public class VirtualVisitBean implements Serializable {
    private String id;
    private Date date;
    private Time startTime;
    private Integer duration;
    private String museumId;
    private Double price;

    public VirtualVisitBean() {
    }

    public VirtualVisitBean(String id, Date date, Time startTime, Integer duration, String museumId, Double price) {
        this.id = id;
        this.date = date;
        this.startTime = startTime;
        this.duration = duration;
        this.museumId = museumId;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getMuseumId() {
        return museumId;
    }

    public void setMuseumId(String museumId) {
        this.museumId = museumId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VirtualVisitBean that = (VirtualVisitBean) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "VirtualVisitBean{" +
                "id='" + id + '\'' +
                ", date=" + date +
                ", startTime=" + startTime +
                ", duration=" + duration +
                ", museumId='" + museumId + '\'' +
                ", price=" + price +
                '}';
    }
}
