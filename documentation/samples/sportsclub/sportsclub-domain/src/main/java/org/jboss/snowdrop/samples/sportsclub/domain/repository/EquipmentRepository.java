package org.jboss.snowdrop.samples.sportsclub.domain.repository;

import org.jboss.snowdrop.samples.sportsclub.domain.entity.Equipment;

import java.util.List;
import java.util.Date;

/**
 *
 */
public interface EquipmentRepository extends Repository<Equipment, Integer>
{
    public List<Equipment> getAvailableEquipments(Date fromDate, Date toDate);
}
