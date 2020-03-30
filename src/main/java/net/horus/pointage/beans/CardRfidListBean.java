/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.horus.pointage.beans;

import com.github.adminfaces.starter.infra.model.Filter;
import com.github.adminfaces.template.exception.BusinessException;
import org.omnifaces.cdi.ViewScoped;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.NamingException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import net.horus.pointage.dao.CardRfidDao;
import net.horus.pointage.dao.UserDao;
import net.horus.pointage.models.CardRfid;
import net.horus.pointage.models.Users;
import net.horus.pointage.utils.Reporting;
import net.sf.jasperreports.engine.JRException;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;


/**
 *
 * @author mbs dev
 */
@Named
@ViewScoped
public class CardRfidListBean implements Serializable {

    @Inject
    private CardRfidDao cardRfidDao;
    
    @Inject 
    private Reporting rep;
    
    
    private CardRfid cardRfid;
    private Integer id;
    private String numCard;
    private LazyDataModel<CardRfid> cardRfids;

    private Filter<CardRfid> filter = new Filter<>(new CardRfid());

    List<CardRfid> selectedCardRfid; //cars selected in checkbox column

    private List<CardRfid> filteredValue;// datatable filteredValue attribute (column filters)
    private String action;

    @PostConstruct
    public void InitCardRfid(){
        cardRfids = new LazyDataModel<CardRfid>() {
            @Override
            public List<CardRfid> load(int first, int pageSize,
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
                List<CardRfid> list = null;
                try {
                    list = cardRfidDao.paginate(filter);
                    setRowCount((int) cardRfidDao.count(filter));
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
            public CardRfid getRowData(String key) {
                return cardRfidDao.findById(new Integer(key));
            }
        };
    }
    
    public void imprimer() throws JRException{
        rep.imprimer();
    }
    
   public void findCarByNumCardOrMatEmploye(String numCard) {
        if (numCard == null) {
            throw new BusinessException("Provide Car ID to load");
        }
        selectedCardRfid.add(cardRfidDao.findByNumCard(numCard));
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

    public String getNumCard() {
        return numCard;
    }

    public void setNumCard(String numCard) {
        this.numCard = numCard;
    }
    
    
    

    public CardRfidDao getCardRfidDao() {
        return cardRfidDao;
    }

    public void setCardRfidDao(CardRfidDao cardRfidDao) {
        this.cardRfidDao = cardRfidDao;
    }

    public CardRfid getCardRfid() {
        return cardRfid;
    }

    public void setCardRfid(CardRfid cardRfid) {
        this.cardRfid = cardRfid;
    }

    public LazyDataModel<CardRfid> getCardRfids() {
        return cardRfids;
    }

    public void setCardRfids(LazyDataModel<CardRfid> cardRfids) {
        this.cardRfids = cardRfids;
    }

    public Filter<CardRfid> getFilter() {
        return filter;
    }

    public void setFilter(Filter<CardRfid> filter) {
        this.filter = filter;
    }

    public List<CardRfid> getSelectedCardRfid() {
        return selectedCardRfid;
    }

    public void setSelectedCardRfid(List<CardRfid> selectedCardRfid) {
        this.selectedCardRfid = selectedCardRfid;
    }

    public List<CardRfid> getFilteredValue() {
        return filteredValue;
    }

    public void setFilteredValue(List<CardRfid> filteredValue) {
        this.filteredValue = filteredValue;
    }

    
    
}
