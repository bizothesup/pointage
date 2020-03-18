package net.horus.pointage.beans;

import net.horus.pointage.dao.EmployesDao;
import net.horus.pointage.dao.ServiceDao;
import net.horus.pointage.models.Employes;
import net.horus.pointage.models.Employes;
import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Faces;
import org.primefaces.util.DateUtils;

import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.NamingException;
import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static com.github.adminfaces.starter.util.Utils.addDetailMessage;
import static com.github.adminfaces.template.util.Assert.has;

@Named
@ViewScoped
public class EmployeFormBean implements Serializable {

    private Integer id;
    private Employes employes;

    @Inject
    private EmployesDao employesDao;

    public String genMatricule() throws NamingException {
        int num = (int) employesDao.countEmploye();
        Calendar cal = new GregorianCalendar();
        cal.setTime(new Date());
        int year = cal.get(Calendar.YEAR) ;
        int nmb = num+1;
        return "EMP000"+nmb+"/"+year;
    }

    public void init() {
        if (Faces.isAjaxRequest()) {
            return;
        }
        if (has(id)) {
            employes = employesDao.findById(id);
        } else {
            employes = new Employes();
            try {
                employes.setMarticule(genMatricule());
            } catch (NamingException e) {
                e.printStackTrace();
            }
            System.out.println("ini");
        }
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Employes getEmployes() {
        return employes;
    }

    public void setEmployes(Employes employes) {
        this.employes = employes;
    }

    public void remove() throws IOException, NamingException {
        if(has(employes) && has(employes.getId())){
            employesDao.deleteEmploye(employes.getId());
            addDetailMessage("Employes " + employes.getMarticule()
                    + " removed successfully");
            Faces.getFlash().setKeepMessages(true);
            Faces.redirect("Employes-list.xhtml");
        }
    }

    public void save() throws NamingException, IOException {
        String msg;
        if(employes.getId() == null){
            employesDao.insertEmployes(employes);
            msg = "Employes " + employes.getMarticule() + " created successfully";
        }else{
            employesDao.MiseAjourEmployes(employes);
            msg = "Employes " + employes.getMarticule() + " updated successfully";
        }
        addDetailMessage(msg);
        Faces.getFlash().setKeepMessages(true);
        Faces.redirect("employes-list.xhtml");
    }

    public void clear() {
        employes = new Employes();
        id = null;
    }
    public boolean isNew() {
        return employes == null || employes.getId() == null;
    }
}

