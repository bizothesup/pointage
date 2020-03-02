/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.horus.pointage.beans;

import com.github.adminfaces.starter.model.Car;
import static com.github.adminfaces.starter.util.Utils.addDetailMessage;
import static com.github.adminfaces.template.util.Assert.has;
import java.io.IOException;
import java.io.Serializable;
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.NamingException;
import net.horus.pointage.dao.RoleDao;
import net.horus.pointage.models.Role;
import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Faces;


/**
 *
 * @author mbs dev
 */
@Named
@ViewScoped
public class RoleFomBean implements Serializable {

    private Integer id;
    private Role role;
    @Inject
    private RoleDao roleDao;
    
      public void init() {
        if (Faces.isAjaxRequest()) {
            return;
        }
        if (has(id)) {
            role = roleDao.findById(id);
        } else {
            role = new Role();
            System.out.println("ini");
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
    
        public void remove() throws IOException, NamingException {
            if(has(role) && has(role.getId())){
                roleDao.deleteRole(role.getId());
                addDetailMessage("Role " + role.getName()
                    + " removed successfully");
            Faces.getFlash().setKeepMessages(true);
            Faces.redirect("role-list.jsf");
            }
    }

    public void save() throws NamingException {
        String msg;
        if(role.getId() == null){
            roleDao.insertRole(role);
            msg = "Role " + role.getName() + " created successfully"; 
        }else{
            roleDao.MiseAjourRole(role);
          msg = "Role " + role.getName() + " updated successfully";
        }
        addDetailMessage(msg);
    }

    public void clear() {
        role = new Role();
        id = null;
    }
      public boolean isNew() {
          return role == null || role.getId() == null;
      }
    
}
