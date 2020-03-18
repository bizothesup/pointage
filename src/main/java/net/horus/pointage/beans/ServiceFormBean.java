/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.horus.pointage.beans;

import net.horus.pointage.dao.ServiceDao;
import net.horus.pointage.models.Services;
import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Faces;

import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.NamingException;
import java.io.IOException;
import java.io.Serializable;

import static com.github.adminfaces.starter.util.Utils.addDetailMessage;
import static com.github.adminfaces.template.util.Assert.has;

/**
 *
 * @author mbsdev
 */
@Named
@ViewScoped
public class ServiceFormBean implements Serializable {
    private Integer id;
    private Services services;

    @Inject
    private ServiceDao serviceDao;


    public void init() {
        if (Faces.isAjaxRequest()) {
            return;
        }
        if (has(id)) {
            services = serviceDao.findById(id);
        } else {
            services = new Services();
            System.out.println("ini");
        }
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Services getServices() {
        return services;
    }

    public void setServices(Services services) {
        this.services = services;
    }

    public void remove() throws IOException, NamingException {
        if(has(services) && has(services.getId())){
            serviceDao.deleteService(services.getId());
            addDetailMessage("Services " + services.getName()
                    + " removed successfully");
            Faces.getFlash().setKeepMessages(true);
            Faces.redirect("service-list.xhtml");
        }
    }

    public void save() throws NamingException, IOException {
        String msg;
        if(services.getId() == null){

            serviceDao.insertService(services);
            msg = "Service " + services.getName() + " created successfully";
        }else{
            serviceDao.MiseAjourService(services);
            msg = "Service " + services.getName() + " updated successfully";
        }
        addDetailMessage(msg);
        Faces.getFlash().setKeepMessages(true);
        Faces.redirect("service-list.xhtml");
    }

    public void clear() {
        services = new Services();
        id = null;
    }
    public boolean isNew() {
        return services == null || services.getId() == null;
    }
}
