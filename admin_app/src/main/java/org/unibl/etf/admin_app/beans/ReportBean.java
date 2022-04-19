package org.unibl.etf.admin_app.beans;

import java.io.Serializable;

public class ReportBean implements Serializable {

    private String date;
    private String hour;
    private Integer count;

    public ReportBean(String date, String hour, Integer count) {
        this.date = date;
        this.hour = hour;
        this.count = count;
    }

    public ReportBean() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
