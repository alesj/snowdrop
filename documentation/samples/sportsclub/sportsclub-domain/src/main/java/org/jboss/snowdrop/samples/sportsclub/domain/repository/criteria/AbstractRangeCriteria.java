package org.jboss.snowdrop.samples.sportsclub.domain.repository.criteria;

/**
 */
public abstract class AbstractRangeCriteria
{
   private Range range;

   public Range getRange()
   {
      return range;
   }

   public void setRange(Range range)
   {
      this.range = range;
   }
}
