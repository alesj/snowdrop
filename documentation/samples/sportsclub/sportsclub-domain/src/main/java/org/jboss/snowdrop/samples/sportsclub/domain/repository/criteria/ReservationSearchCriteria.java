package org.jboss.snowdrop.samples.sportsclub.domain.repository.criteria;

import java.util.Date;

/**
 */
public class ReservationSearchCriteria extends AbstractRangeCriteria
{
   private Date fromDate;

   private Date toDate;

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
}
