package org.jboss.snowdrop.samples.sportsclub.jsf.beans;

import org.jboss.snowdrop.samples.stayfit.service.ReservationService;
import org.jboss.snowdrop.samples.sportsclub.domain.entity.Reservation;

import java.util.Date;
import java.util.List;

/**
 */
public class ReservationsBean
{

   private ReservationService reservationService;
   private Date fromDate;
   private Date toDate;
   private List<Reservation> availableReservations;

   public String populateReservations()
   {
      if (fromDate != null && toDate != null)
      {
         availableReservations = reservationService.getReservations(fromDate, toDate);
      } else if (fromDate == null && toDate != null)
      {
         availableReservations = reservationService.getReservationBefore(toDate);
      } else if (fromDate != null && toDate == null)
      {
         availableReservations = reservationService.getReservationAfter(fromDate);
      } else
      {
         availableReservations = reservationService.getAllReservation();
      }
      return "success";
   }

   public ReservationService getReservationService()
   {
      return reservationService;
   }

   public void setReservationService(ReservationService reservationService)
   {
      this.reservationService = reservationService;
   }

   public Date getFromDate()
   {
      return fromDate;
   }

   public void setFromDate(Date fromDate)
   {
      this.fromDate = fromDate;
   }

   public Date getToDate()
   {
      return toDate;
   }

   public void setToDate(Date toDate)
   {
      this.toDate = toDate;
   }

   public List<Reservation> getAvailableReservations()
   {
      return availableReservations;
   }

   public void setAvailableReservations(List<Reservation> availableReservations)
   {
      this.availableReservations = availableReservations;
   }
}
