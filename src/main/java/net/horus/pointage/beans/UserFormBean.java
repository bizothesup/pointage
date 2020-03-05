/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.horus.pointage.beans;

import static com.github.adminfaces.starter.util.Utils.addDetailMessage;
import static com.github.adminfaces.template.util.Assert.has;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.NamingException;
import net.horus.pointage.dao.RoleDao;
import net.horus.pointage.dao.UserDao;
import net.horus.pointage.models.Users;
import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Faces;

/**
 *
 * @author mbsdev
 */
@Named
@ViewScoped
public class UserFormBean implements Serializable{
   private Integer id;
    private Users users;
    @Inject
    private UserDao userDao;
    @Inject
    private RoleDao roleDao;
    
    private List<SelectItem> listrole = new ArrayList<SelectItem>();
    private Integer selectRole;
    
     @PostConstruct
    public void loadRole(){
         listrole.clear();
        listrole = roleDao.selectRolesItems();
    }
    
      public void init() {
        if (Faces.isAjaxRequest()) {
            return;
        }
        if (has(id)) {
            users = users.findById(id);
        } else {
            users = new Users();
            System.out.println("ini");
        }
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public List<SelectItem> getListrole() {
        return listrole;
    }

    public void setListrole(List<SelectItem> listrole) {
        this.listrole = listrole;
    }

    public Integer getSelectRole() {
        return selectRole;
    }

    public void setSelectRole(Integer selectRole) {
        this.selectRole = selectRole;
    }

    public void remove() throws IOException, NamingException {
            if(has(users) && has(users.getId())){
               userDao.deleteUsers(users.getId());
                addDetailMessage("Users " + users.getLogin()
                    + " removed successfully");
            Faces.getFlash().setKeepMessages(true);
            Faces.redirect("user-list.jsf");
            }
    }

    public void save() throws NamingException, IOException {
        String msg;
        if(users.getId() == null){
            if(selectRole != null){
                users.setRole(roleDao.selectRoleOne(selectRole));
            }

            users.setIsvalide(true);
            userDao.insertUsers(users);
            msg = "User " + users.getLogin() + " created successfully"; 
        }else{
            userDao.MiseAjourUsers(users);
          msg = "Users " + users.getLogin() + " updated successfully";
        }
        addDetailMessage(msg);
        Faces.getFlash().setKeepMessages(true);
        Faces.redirect("user-list.xhtml");
    }

    public void clear() {
        users = new Users();
        id = null;
    }
      public boolean isNew() {
          return users == null || users.getId() == null;
      }
    
}
