package net.horus.pointage.utils;

import net.horus.pointage.models.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.naming.InitialContext;
import javax.naming.NamingException;

public class HibernateUtils {
    private SessionFactory sessionFactory;
    private static ThreadLocal sessiontThreadLocal = new ThreadLocal();
    private String driverJdbc = "mysql";

    public  Session getSession() throws NamingException {
        Session session = (Session) sessiontThreadLocal.get();
        if(session == null){
            configuration();
            session = sessionFactory.openSession();
            sessiontThreadLocal.set(session);
        }

        return session;
    }

    private void configuration() throws NamingException {
        InitialContext initialContext = new InitialContext();
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        configuration.setProperty("hibernate.hbm2ddl.auto", "none");
        configuration.setProperty("hibernate.enable_lazy_load_no_trans", "true");
        System.out.println("***************Mapping des class**********************");
         mappingClass(configuration);
        this.sessionFactory = configuration.buildSessionFactory();
    }

    private Configuration mappingClass(Configuration paramConfig) {
      paramConfig.addClass(CardRfid.class);
      paramConfig.addClass(EmployeePointage.class);
      paramConfig.addClass(Employes.class);
      paramConfig.addClass(EmployeSortiePointage.class);
      paramConfig.addClass(Groupe.class);
      paramConfig.addClass(PointageParam.class);
      paramConfig.addClass(Services.class);
      paramConfig.addClass(Users.class);
      paramConfig.addClass(Role.class);
      return paramConfig;
    }


    public void closeSession(){
        Session session = (Session) sessiontThreadLocal.get();
        if(session != null){
            sessiontThreadLocal.remove();
            if(!sessionFactory.isClosed())
                sessionFactory.close();
            if(session.isConnected() == true)
                session.disconnect();
            if (session.isOpen() == true)
                session.close();
        }
    }

}
