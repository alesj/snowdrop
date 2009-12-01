package org.jboss.snowdrop.samples.stayfit.dao.hibernate;

import org.jboss.snowdrop.samples.sportsclub.domain.repository.ReservationRepository;
import org.jboss.snowdrop.samples.sportsclub.domain.entity.Reservation;
import org.hibernate.Criteria;
import static org.hibernate.criterion.Restrictions.*;

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

   public List<Reservation> getReservations(Date fromDate, Date toDate)
   {

      Criteria cri = getCurrentSession().createCriteria(Reservation.class);
      cri.add(and(ge("from", fromDate), le("to", toDate)));

      return cri.list();
   }

   public List<Reservation> getReservationsBefore(Date date)
   {
      return getCurrentSession().createCriteria(Reservation.class).add(le("to", date)).list();
   }

   public List<Reservation> getReservationsAfter(Date date)
   {
      return getCurrentSession().createCriteria(Reservation.class).add(ge("from", date)).list();
   }
}
