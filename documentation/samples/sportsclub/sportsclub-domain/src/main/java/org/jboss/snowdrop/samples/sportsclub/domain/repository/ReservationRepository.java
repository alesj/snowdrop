package org.jboss.spring.samples.sportsclub.domain.repository;

import org.jboss.spring.samples.sportsclub.domain.entity.Reservation;
import org.jboss.spring.samples.sportsclub.domain.repository.criteria.ReservationSearchCriteria;

import java.util.List;

public interface ReservationRepository extends Repository<Reservation, Integer>
{
   List<Reservation> getByCriteria(ReservationSearchCriteria criteria);

   Integer countByCriteria(ReservationSearchCriteria criteria);
}
