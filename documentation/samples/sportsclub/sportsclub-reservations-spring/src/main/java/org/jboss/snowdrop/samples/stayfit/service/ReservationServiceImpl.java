package org.jboss.snowdrop.samples.stayfit.service;

import org.jboss.snowdrop.samples.sportsclub.domain.entity.Reservation;
import org.jboss.snowdrop.samples.sportsclub.domain.repository.ReservationRepository;

import java.util.List;
import java.util.Date;

/**
 * 
 */
public class ReservationServiceImpl implements ReservationService {

    private ReservationRepository reservationRepository;

    public List<Reservation> getReservations(Date fromDate, Date toDate) {
        return getReservationRepository().getReservations(fromDate, toDate);
    }

    public ReservationRepository getReservationRepository() {
        return reservationRepository;
    }

    public void setReservationRepository(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }
}
