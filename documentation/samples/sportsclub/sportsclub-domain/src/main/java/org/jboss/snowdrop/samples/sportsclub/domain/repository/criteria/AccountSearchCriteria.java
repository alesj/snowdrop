package org.jboss.snowdrop.samples.sportsclub.domain.repository.criteria;

/**
 * @author <a href="mailto:mariusb@redhat.com">Marius Bogoevici</a>
 */
public class AccountSearchCriteria extends AbstractRangeCriteria
{
   private PersonSearchCriteria personSearchCriteria;

   public PersonSearchCriteria getPersonSearchCriteria()
   {
      return personSearchCriteria;
   }

   public void setPersonSearchCriteria(PersonSearchCriteria personSearchCriteria)
   {
      this.personSearchCriteria = personSearchCriteria;
   }

}
