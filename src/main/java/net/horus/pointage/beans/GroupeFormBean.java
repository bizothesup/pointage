package net.horus.pointage.beans;

import net.horus.pointage.dao.GroupeDao;
import net.horus.pointage.dao.ServiceDao;
import net.horus.pointage.models.Groupe;
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

@Named
@ViewScoped
public class GroupeFormBean implements Serializable {
    private Integer id;
    private Groupe groupe;

    @Inject
    private GroupeDao groupeDao;


    public void init() {
        if (Faces.isAjaxRequest()) {
            return;
        }
        if (has(id)) {
            groupe = groupeDao.findById(id);
        } else {
            groupe = new Groupe();
            System.out.println("ini");
        }
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Groupe getGroupe() {
        return groupe;
    }

    public void setGroupe(Groupe groupe) {
        this.groupe = groupe;
    }

    public void remove() throws IOException, NamingException {
        if(has(groupe) && has(groupe.getId())){
            groupeDao.deleteGroupe(groupe.getId());
            addDetailMessage("groupe " + groupe.getName()
                    + " removed successfully");
            Faces.getFlash().setKeepMessages(true);
            Faces.redirect("service-list.xhtml");
        }
    }

    public void save() throws NamingException, IOException {
        String msg;
        if(groupe.getId() == null){

            groupeDao.insertGroupe(groupe);
            msg = "Service " + groupe.getName() + " created successfully";
        }else{
            groupeDao.MiseAjourGroupe(groupe);
            msg = "Service " + groupe.getName() + " updated successfully";
        }
        addDetailMessage(msg);
        Faces.getFlash().setKeepMessages(true);
        Faces.redirect("service-list.xhtml");
    }

    public void clear() {
        groupe = new Groupe();
        id = null;
    }
    public boolean isNew() {
        return groupe == null || groupe.getId() == null;
    }
}


