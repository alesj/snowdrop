package org.jboss.snowdrop.samples.sportsclub.jsf.beans;

import org.jboss.snowdrop.samples.sportsclub.domain.repository.EquipmentRepository;
import org.jboss.snowdrop.samples.sportsclub.domain.entity.Equipment;

import java.util.List;
import java.util.Collections;
import java.util.Date;

/**
 */
public class AvailableEquipments {

    private static String SUCCESS = "success"; 

    private EquipmentRepository equipmentRepository;

    private List<Equipment> equipments = Collections.<Equipment>emptyList();
    private Date fromDate = new Date();
    private Date toDate = new Date();

    public String getAllEquipments() {
        equipments = (List<Equipment>) equipmentRepository.findAll();
        return SUCCESS;
    }

    public String getAvailableEquipments() {
        equipments = equipmentRepository.getAvailableEquipments(fromDate, toDate);
        return SUCCESS;
    }

    public EquipmentRepository getEquipmentRepository() {
        return equipmentRepository;
    }

    public void setEquipmentRepository(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }

    public List<Equipment> getEquipments() {
        return equipments;
    }

    public void setEquipments(List<Equipment> equipments) {
        this.equipments = equipments;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }
}
