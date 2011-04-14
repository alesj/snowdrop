package org.jboss.spring.samples.stayfit.dao.hibernate;

import org.jboss.spring.samples.sportsclub.domain.entity.Equipment;
import org.jboss.spring.samples.sportsclub.domain.repository.EquipmentRepository;

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
