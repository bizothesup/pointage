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
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.NamingException;
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

    
    
        public void remove() throws IOException, NamingException {
            if(has(users) && has(users.getId())){
               userDao.deleteUsers(users.getId());
                addDetailMessage("Users " + users.getLogin()
                    + " removed successfully");
            Faces.getFlash().setKeepMessages(true);
            Faces.redirect("user-list.jsf");
            }
    }

    public void save() throws NamingException {
        String msg;
        if(users.getId() == null){
            userDao.insertUsers(users);
            msg = "User " + users.getLogin() + " created successfully"; 
        }else{
            userDao.MiseAjourUsers(users);
          msg = "Users " + users.getLogin() + " updated successfully";
        }
        addDetailMessage(msg);
    }

    public void clear() {
        users = new Users();
        id = null;
    }
      public boolean isNew() {
          return users == null || users.getId() == null;
      }
    
}
