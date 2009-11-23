package org.jboss.snowdrop.samples.sportsclub.domain.repository;

import org.jboss.snowdrop.samples.sportsclub.domain.entity.Reservation;
import org.jboss.snowdrop.samples.sportsclub.domain.entity.Equipment;

import java.util.List;
import java.util.Date;

/**
 *
 */
public interface ReservationRepository extends Repository<Reservation, Integer> {

    List<Reservation> getReservations(Date fromDate, Date toDate);

    /**
     * Return all reservations for particular {@link Equipment} within given time period.
     *  
     * @param equipment
     * @param fromDate
     * @param toDate
     * @return
     */
//    List<Reservation> getReservations(Equipment equipment, Date fromDate, Date toDate);

    /**
     * Return all free {@link Equipment}s within given time period.
     * @param fromDate
     * @param toDate
     * @return
     */
//    List<Equipment> getFreeEquipments(Date fromDate, Date toDate);
}
