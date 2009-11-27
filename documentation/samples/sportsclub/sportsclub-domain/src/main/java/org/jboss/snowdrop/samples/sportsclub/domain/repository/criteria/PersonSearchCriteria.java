package org.jboss.snowdrop.samples.sportsclub.domain.repository.criteria;

/**
 * @author <a href="mailto:mariusb@redhat.com">Marius Bogoevici</a>
 */
public class PersonSearchCriteria
{
   private String name;

   private String city;

   private Range range;

   public Range getRange()
   {
      return range;
   }

   public void setRange(Range range)
   {
      this.range = range;
   }

   public String getCity()
   {
      return city;
   }

   public void setCity(String city)
   {
      this.city = city;
   }

   public String getName()
   {
      return name;
   }

   public void setName(String name)
   {
      this.name = name;
   }
}