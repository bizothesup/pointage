package net.horus.pointage.beans;



import com.github.adminfaces.starter.infra.model.Filter;
import net.horus.pointage.dao.ServiceDao;
import net.horus.pointage.models.Services;
import org.omnifaces.cdi.ViewScoped;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.NamingException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;


@Named
@ViewScoped
public class ServiceListBean  implements Serializable {

    @Inject
    ServiceDao serviceDao;
    private Services service;
    Integer id;
    LazyDataModel<Services> services;

    Filter<Services> filter = new Filter<>(new Services());

    List<Services> selectedServices; //cars selected in checkbox column

    List<Services> filteredValue;// datatable filteredValue attribute (column filters)
    private String action;

    @PostConstruct
    public void InitServices(){
        services = new LazyDataModel<Services>() {
            @Override
            public List<Services> load(int first, int pageSize,
                                   String sortField, SortOrder sortOrder,
                                   Map<String, Object> filters) {

                com.github.adminfaces.starter.infra.model.SortOrder order = null;
                if (sortOrder != null) {
                    order = sortOrder.equals(SortOrder.ASCENDING) ? com.github.adminfaces.starter.infra.model.SortOrder.ASCENDING
                            : sortOrder.equals(SortOrder.DESCENDING) ? com.github.adminfaces.starter.infra.model.SortOrder.DESCENDING
                            : com.github.adminfaces.starter.infra.model.SortOrder.UNSORTED;
                }
                filter.setFirst(first).setPageSize(pageSize)
                        .setSortField(sortField).setSortOrder(order)
                        .setParams(filters);
                List<Services> list = null;
                try {
                    list = serviceDao.paginate(filter);
                    setRowCount((int) serviceDao.count(filter));
                } catch (NamingException e) {
                    e.printStackTrace();
                }
                return list;
            }

            @Override
            public int getRowCount() {
                return super.getRowCount();
            }

            @Override
            public Services getRowData(String key) {
                return serviceDao.findById(new Integer(key));
            }
        };
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ServiceDao getServiceDao() {
        return serviceDao;
    }

    public void setServiceDao(ServiceDao serviceDao) {
        this.serviceDao = serviceDao;
    }

    public Services getService() {
        return service;
    }

    public void setService(Services service) {
        this.service = service;
    }

    public LazyDataModel<Services> getServices() {
        return services;
    }

    public void setServices(LazyDataModel<Services> services) {
        this.services = services;
    }

    public Filter<Services> getFilter() {
        return filter;
    }

    public void setFilter(Filter<Services> filter) {
        this.filter = filter;
    }

    public List<Services> getSelectedServices() {
        return selectedServices;
    }

    public void setSelectedServices(List<Services> selectedServices) {
        this.selectedServices = selectedServices;
    }

    public List<Services> getFilteredValue() {
        return filteredValue;
    }

    public void setFilteredValue(List<Services> filteredValue) {
        this.filteredValue = filteredValue;
    }

   

   
}

