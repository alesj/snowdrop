package org.jboss.snowdrop.samples.stayfit.service;

import org.jboss.snowdrop.samples.sportsclub.domain.entity.Reservation;
import org.jboss.snowdrop.samples.sportsclub.domain.repository.ReservationRepository;
import org.jboss.snowdrop.samples.sportsclub.domain.repository.criteria.ReservationSearchCriteria;
import org.jboss.snowdrop.samples.sportsclub.domain.repository.criteria.Range;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Date;

/**
 *
 */
@Transactional
public class ReservationServiceImpl implements ReservationService
{

   private ReservationRepository reservationRepository;

   @Transactional(readOnly = true)
   public List<Reservation> getReservations(Date fromDate, Date toDate, Integer min, Integer max)
   {
      ReservationSearchCriteria criteria = new ReservationSearchCriteria();
      criteria.setFromDate(fromDate);
      criteria.setToDate(toDate);
      if (min != null && max != null)
      {
         Range range = new Range(min, max);
         criteria.setRange(range);
      }
      return reservationRepository.getByCriteria(criteria);
   }

   public Integer countReservationsForRange(Date fromDate, Date toDate)
   {
      ReservationSearchCriteria criteria = new ReservationSearchCriteria();
      criteria.setFromDate(fromDate);
      criteria.setToDate(toDate);
      return reservationRepository.countByCriteria(criteria);
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
