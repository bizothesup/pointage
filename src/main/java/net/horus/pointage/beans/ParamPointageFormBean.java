package net.horus.pointage.beans;

import net.horus.pointage.dao.GroupeDao;
import net.horus.pointage.dao.PointageParamDao;
import net.horus.pointage.models.Groupe;
import net.horus.pointage.models.PointageParam;
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
public class ParamPointageFormBean  implements Serializable {
    private Integer id;
    private PointageParam pointageParam;

    @Inject
    private PointageParamDao pointageParamDao;


    public void init() {
        if (Faces.isAjaxRequest()) {
            return;
        }
        if (has(id)) {
            pointageParam = pointageParamDao.findById(id);
        } else {
            pointageParam = new PointageParam();
            System.out.println("ini");
        }
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PointageParam getPointageParam() {
        return pointageParam;
    }

    public void setPointageParam(PointageParam pointageParam) {
        this.pointageParam = pointageParam;
    }

    public void remove() throws IOException, NamingException {
        if(has(pointageParam) && has(pointageParam.getId())){
            pointageParamDao.deletePointageParam(pointageParam.getId());
            addDetailMessage("PointageParam " + pointageParam.getId()
                    + " removed successfully");
            Faces.getFlash().setKeepMessages(true);
            Faces.redirect("pointageParam.xhtml");
        }
    }

    public void save() throws NamingException, IOException {
        String msg;
        if(pointageParam.getId() == null){

            pointageParamDao.insertParamPointage(pointageParam);
            msg = "Pointage Parametre " + pointageParam.getId() + " created successfully";
        }else{
            pointageParamDao.MiseAjourPointageParam(pointageParam);
            msg = "Pointage Parametre " + pointageParam.getId() + " updated successfully";
        }
        addDetailMessage(msg);
        Faces.getFlash().setKeepMessages(true);
        Faces.redirect("pointageParam.xhtml");
    }

    public void clear() {
        pointageParam = new PointageParam();
        id = null;
    }
    public boolean isNew() {
        return pointageParam == null || pointageParam.getId() == null;
    }
}



