package com.github.adminfaces.starter.infra.security;

import com.github.adminfaces.template.session.AdminSession;
import net.horus.pointage.models.Users;
import net.horus.pointage.utils.HibernateUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.omnifaces.util.Faces;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Specializes;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;

import static com.github.adminfaces.starter.util.Utils.addDetailMessage;
import com.github.adminfaces.template.config.AdminConfig;
import org.omnifaces.util.Messages;

import javax.inject.Inject;
import javax.naming.NamingException;
import org.mindrot.jbcrypt.BCrypt;

/**
 * Created by rmpestano on 12/20/14.
 *
 * This is just a login example.
 *
 * AdminSession uses isLoggedIn to determine if user must be redirect to login page or not.
 * By default AdminSession isLoggedIn always resolves to true so it will not try to redirect user.
 *
 * If you already have your authorization mechanism which controls when user must be redirect to initial page or logon
 * you can skip this class.
 */
@Named
@SessionScoped
@Specializes
public class LogonMB extends AdminSession implements Serializable {

    private Users currentUser ,users;
    private String login;
    private String password;
    private boolean remember;
    private final HibernateUtils hibernateUtils;

    public LogonMB(){
        hibernateUtils = new HibernateUtils();
       // password = hibernateUtils.hashPassword(password);
        
    }

    @Inject
    private AdminConfig adminConfig;


    public void login() throws IOException, NamingException {
        
        
        
        Session session = hibernateUtils.getSession();
       // System.out.println("mot de passe "+hibernateUtils.password);

        Query query = session.createQuery("from "+ Users.class.getName()+" where login =:login ").setString("login", login);
        System.out.println("resultat de la requette"+query.list().size());
        if(query.list().size() > 0){
            for(int i=0; i<query.list().size(); i++){
                users = (Users) query.list().get(i);
                if (BCrypt.checkpw(password, users.getPassword()))
                {
                    if(users.getIsvalide()==true){
                        currentUser = users;
                        addDetailMessage("Logged in successfully as <b>" + currentUser.getLogin() + "</b>");
                        Faces.getExternalContext().getFlash().setKeepMessages(true);
                        Faces.redirect(adminConfig.getIndexPage());
                    }
                    else{
                        Messages.addGlobalError("Compte inactif.");
                        Messages.addInfo(null,"Utilisateur n'existe pas ");
                        Faces.redirect(adminConfig.getIndexPage());
                    }
                }
                else {
                    Messages.addGlobalError("Unknown login, please try again.");
                    Messages.addInfo(null,"Utilisateur n'existe pas ");
                    Faces.redirect(adminConfig.getIndexPage());
                }
            }
        }
    }

    @Override
    public boolean isLoggedIn() {

        return currentUser != null;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isRemember() {
        return remember;
    }

    public void setRemember(boolean remember) {
        this.remember = remember;
    }

    public Users getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(Users currentUser) {
        this.currentUser = currentUser;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }
}
