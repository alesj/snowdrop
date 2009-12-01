package org.jboss.snowdrop.samples.stayfit.service;

import org.jboss.snowdrop.samples.sportsclub.domain.entity.Reservation;
import org.jboss.snowdrop.samples.sportsclub.domain.repository.ReservationRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Date;

/**
 *
 */
public class ReservationServiceImpl implements ReservationService
{

   private ReservationRepository reservationRepository;

   @Transactional(readOnly = true)
   public List<Reservation> getReservations(Date fromDate, Date toDate)
   {
      return getReservationRepository().getReservations(fromDate, toDate);
   }

   @Transactional(readOnly = true)
   public List<Reservation> getReservationBefore(Date date)
   {
      return getReservationRepository().getReservationsBefore(date);
   }

   @Transactional(readOnly = true)
   public List<Reservation> getReservationAfter(Date date)
   {
      return getReservationRepository().getReservationsAfter(date);
   }

   @Transactional(readOnly = true)
   public List<Reservation> getAllReservation()
   {
      return (List<Reservation>) reservationRepository.findAll();
   }

   public ReservationRepository getReservationRepository()
   {
      return reservationRepository;
   }

   public void setReservationRepository(ReservationRepository reservationRepository)
   {
      this.reservationRepository = reservationRepository;
   }
}
