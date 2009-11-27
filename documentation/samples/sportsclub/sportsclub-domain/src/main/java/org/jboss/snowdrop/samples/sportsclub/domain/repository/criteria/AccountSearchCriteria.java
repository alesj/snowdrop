package org.jboss.snowdrop.samples.sportsclub.domain.repository.criteria;

/**
 * @author <a href="mailto:mariusb@redhat.com">Marius Bogoevici</a>
 */
public class AccountSearchCriteria
{
   private PersonSearchCriteria personSearchCriteria;

   private Range range;


   public PersonSearchCriteria getPersonSearchCriteria()
   {
      return personSearchCriteria;
   }

   public void setPersonSearchCriteria(PersonSearchCriteria personSearchCriteria)
   {
      this.personSearchCriteria = personSearchCriteria;
   }

   public Range getRange()
   {
      return range;
   }

   public void setRange(Range range)
   {
      this.range = range;
   }


}
