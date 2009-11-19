package org.jboss.snowdrop.samples.stayfit.dao.hibernate;

import org.hibernate.SessionFactory;
import org.jboss.snowdrop.samples.sportsclub.domain.entity.Person;
import org.jboss.snowdrop.samples.sportsclub.domain.repository.PersonRepository;

/**
 * @author <a href="mailto:mariusb@redhat.com">Marius Bogoevici</a>
 */
public class HibernatePersonRepository extends HibernateRepository<Person, Integer> implements PersonRepository
{

   public HibernatePersonRepository()
   {
      super(Person.class);
   }

}
