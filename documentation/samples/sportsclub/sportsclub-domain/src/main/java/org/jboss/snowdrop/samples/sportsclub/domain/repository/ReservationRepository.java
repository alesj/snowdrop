package org.jboss.snowdrop.samples.sportsclub.domain.repository;

import org.jboss.snowdrop.samples.sportsclub.domain.entity.Reservation;

import java.util.List;
import java.util.Date;

/**
 *
 */
public interface ReservationRepository extends Repository<Reservation, Integer> {

    /**
     * Return all reservations in given date range.
     *
     * @see #getReservationsAfter(java.util.Date)
     * @see #getReservationsBefore(java.util.Date)
     * 
     * @param fromDate
     * @param toDate
     * @return
     */
    List<Reservation> getReservations(Date fromDate, Date toDate);

    /**
     * Return all reservations before given date.
     * This means all reservations whose toDate.before(date) is true.    
     * @param date
     * @return
     */
    List<Reservation> getReservationsBefore(Date date);

    /**
     * Return all reservations after given date.
     * This means all reservations whose fromDate.after(date) is true. 
     * @param date
     * @return
     */
    List<Reservation> getReservationsAfter(Date date);
}
