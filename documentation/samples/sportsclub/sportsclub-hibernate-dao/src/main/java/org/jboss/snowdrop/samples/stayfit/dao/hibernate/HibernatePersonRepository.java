package org.jboss.snowdrop.samples.stayfit.dao.hibernate;

import org.hibernate.SessionFactory;
import org.jboss.snowdrop.samples.sportsclub.domain.entity.Person;
import org.jboss.snowdrop.samples.sportsclub.domain.repository.PersonRepository;

/**
 * @author <a href="mailto:mariusb@redhat.com">Marius Bogoevici</a>
 */
public class HibernatePersonRepository implements PersonRepository
{
   private SessionFactory sessionFactory;

   public void setSessionFactory(SessionFactory sessionFactory)
   {
      this.sessionFactory = sessionFactory;
   }

   public void save(Person person)
   {
      sessionFactory.getCurrentSession().saveOrUpdate(person);
   }
}
