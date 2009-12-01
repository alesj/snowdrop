package org.jboss.snowdrop.samples.stayfit.service;

import org.jboss.snowdrop.samples.sportsclub.domain.entity.Reservation;

import java.util.List;
import java.util.Date;

/**
 *
 */
public interface ReservationService
{
    
    List<Reservation> getReservations(Date fromDate, Date toDate);
    List<Reservation> getReservationBefore(Date date);
    List<Reservation> getReservationAfter(Date date);
    List<Reservation> getAllReservation();

}
