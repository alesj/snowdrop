package org.jboss.snowdrop.samples.stayfit.dao.hibernate;

import org.jboss.snowdrop.samples.sportsclub.domain.entity.Equipment;
import org.jboss.snowdrop.samples.sportsclub.domain.repository.EquipmentRepository;

public class HibernateEquipmentRepository extends HibernateRepository<Equipment, Integer> implements EquipmentRepository {
    
    public HibernateEquipmentRepository()
    {
        super(Equipment.class);
    }
}
