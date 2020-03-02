/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.horus.pointage.beans;

import net.horus.pointage.dao.RoleDao;
import org.omnifaces.cdi.ViewScoped;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.NamingException;
import java.io.Serializable;


/**
 *
 * @author mbs dev
 */
@Named
@ViewScoped
public class UserBean implements Serializable {

    /**
     * Creates a new instance of UserBean
     */
    @Inject
    RoleDao roleDao;
    private String id;

    public UserBean() {
    }

    @PostConstruct
    public void initController(){
        try {
            id = String.valueOf(roleDao.selectRoles().size());
        } catch (NamingException e) {
            e.printStackTrace();
        }

    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
