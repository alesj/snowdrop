package org.jboss.snowdrop.samples.stayfit.dao.hibernate;

import org.jboss.snowdrop.samples.sportsclub.domain.repository.ReservationRepository;
import org.jboss.snowdrop.samples.sportsclub.domain.repository.criteria.ReservationSearchCriteria;
import org.jboss.snowdrop.samples.sportsclub.domain.entity.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;
import java.util.Locale;
import java.util.Date;
import java.util.List;

/**
 */
@ContextConfiguration(locations = {"classpath:test-infrastructure.xml", "classpath:dao-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class TestHibernateReservationRepository
{
   @Autowired
   ReservationRepository reservationRepository;

   @Test
   @Transactional
   public void testReservationRepository()
   {

      Date from = getDate(2009,1,1);
      Date to = getDate(2009,12,31);

      ReservationSearchCriteria criteria = new ReservationSearchCriteria();
      criteria.setFromDate(from);
      criteria.setToDate(to);

      List<Reservation> reservations = reservationRepository.getByCriteria(criteria);
      Assert.assertEquals(3, reservations.size());

      criteria = new ReservationSearchCriteria();
      criteria.setFromDate(getDate(2009,02,01));

      reservations = reservationRepository.getByCriteria(criteria);
      Assert.assertEquals(2, reservations.size());

      criteria = new ReservationSearchCriteria();
      criteria.setToDate(getDate(2009,11,30));

      reservations = reservationRepository.getByCriteria(criteria);
      Assert.assertEquals(2, reservations.size());
      
   }

   private Date getDate(int year, int month, int day)
   {
      Calendar cal = Calendar.getInstance(Locale.US);
      cal.clear();
      cal.set(year, month-1, day);
      return cal.getTime();
   }
}
