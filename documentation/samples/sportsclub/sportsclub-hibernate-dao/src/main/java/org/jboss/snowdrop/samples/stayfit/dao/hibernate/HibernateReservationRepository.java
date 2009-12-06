package org.jboss.snowdrop.samples.stayfit.dao.hibernate;

import org.jboss.snowdrop.samples.sportsclub.domain.repository.ReservationRepository;
import org.jboss.snowdrop.samples.sportsclub.domain.repository.criteria.ReservationSearchCriteria;
import org.jboss.snowdrop.samples.sportsclub.domain.entity.Reservation;
import org.hibernate.Criteria;
import static org.hibernate.criterion.Restrictions.*;
import org.hibernate.criterion.Projections;

import java.util.List;
import java.util.Date;

/**
 */
public class HibernateReservationRepository extends HibernateRepository<Reservation, Integer> implements ReservationRepository
{

   public HibernateReservationRepository()
   {
      super(Reservation.class);
   }

   private Criteria convert(ReservationSearchCriteria reservationSearchCriteria)
   {
      Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Reservation.class);

      Date from = reservationSearchCriteria.getFromDate();
      Date to = reservationSearchCriteria.getToDate();

      if (from != null && to != null) criteria.add(and(ge("from", from), le("to", to)));
      else
      {
         if (from != null) criteria.add(ge("from", from));
         if (to != null) criteria.add(le("to", to));
      }

      if (reservationSearchCriteria.getRange() != null)
      {
         criteria.setFirstResult(reservationSearchCriteria.getRange().getMinIndex());
         criteria.setMaxResults(reservationSearchCriteria.getRange().length());
      }

      return criteria;
   }

   public List<Reservation> getByCriteria(ReservationSearchCriteria criteria)
   {
      Criteria cri = convert(criteria);
      return cri.list();
   }

   public Integer countByCriteria(ReservationSearchCriteria criteria)
   {
      Criteria cri = convert(criteria);
      cri.setProjection(Projections.count("id"));
      return (Integer) cri.uniqueResult();
   }

}
