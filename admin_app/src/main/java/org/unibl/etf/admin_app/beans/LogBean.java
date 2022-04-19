package org.unibl.etf.admin_app.beans;

import java.io.Serializable;

public class LogBean implements Serializable {

    private String type;
    private String message;
    private String dateTime;

    public LogBean(String type, String message, String dateTime) {
        this.type = type;
        this.message = message;
        this.dateTime = dateTime;
    }

    public LogBean() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

}
