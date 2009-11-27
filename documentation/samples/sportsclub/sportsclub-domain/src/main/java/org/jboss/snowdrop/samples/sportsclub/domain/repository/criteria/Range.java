package org.jboss.snowdrop.samples.sportsclub.domain.repository.criteria;

/**
 * @author <a href="mailto:mariusb@redhat.com">Marius Bogoevici</a>
 */
public class Range
{
   private int minIndex;

   private int maxIndex;

   public Range(int minIndex, int length)
   {
      this.maxIndex = minIndex + length;
      this.minIndex = minIndex;
   }

   public int getMaxIndex()
   {
      return maxIndex;
   }

   public int getMinIndex()
   {
      return minIndex;
   }

   public int length()
   {
      return maxIndex - minIndex;
   }
}
