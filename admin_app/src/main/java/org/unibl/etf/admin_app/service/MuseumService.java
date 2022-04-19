package org.unibl.etf.admin_app.service;

import org.unibl.etf.admin_app.beans.MuseumBean;
import org.unibl.etf.admin_app.dao.MuseumDAO;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class MuseumService implements Serializable {

    public List<MuseumBean> getMuseums(){
        try {
            return MuseumDAO.selectAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public boolean addMuseum(MuseumBean museum){
        try {
            return  MuseumDAO.add(museum);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean editMuseum(MuseumBean museum){
        try {
            return  MuseumDAO.updateMuseum(museum);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteMuseum(String id){
        try {
            return MuseumDAO.deleteById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
