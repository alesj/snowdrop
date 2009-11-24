package org.jboss.snowdrop.samples.stayfit.dao.hibernate;

import org.jboss.snowdrop.samples.sportsclub.domain.entity.Equipment;
import org.jboss.snowdrop.samples.sportsclub.domain.repository.EquipmentRepository;

import java.util.List;
import java.util.Date;

public class HibernateEquipmentRepository extends HibernateRepository<Equipment, Integer> implements EquipmentRepository {
    
    public HibernateEquipmentRepository()
    {
        super(Equipment.class);
    }

    public List<Equipment> getAvailableEquipments(Date fromDate, Date toDate) {
        // TODO implement fancy query
        return null;
    }
}
