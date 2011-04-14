package org.jboss.spring.samples.stayfit.service;

import org.jboss.spring.samples.sportsclub.domain.entity.Reservation;

import java.util.List;
import java.util.Date;

/**
 *
 */
public interface ReservationService
{
   List<Reservation> getReservations(Date fromDate, Date toDate, Integer nim, Integer max);

   Integer countReservationsForRange(Date fromDate, Date toDate);
}
