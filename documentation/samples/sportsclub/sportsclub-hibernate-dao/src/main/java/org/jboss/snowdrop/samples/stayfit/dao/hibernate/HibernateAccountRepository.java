package org.jboss.snowdrop.samples.stayfit.dao.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jboss.snowdrop.samples.sportsclub.domain.entity.Account;
import org.jboss.snowdrop.samples.sportsclub.domain.repository.AccountRepository;

/**
 * @author <a href="mailto:mariusb@redhat.com">Marius Bogoevici</a>
 */
public class HibernateAccountRepository implements AccountRepository
{

   private SessionFactory sessionFactory;

   public void setSessionFactory(SessionFactory sessionFactory)
   {
      this.sessionFactory = sessionFactory;
   }

   public List<Account> findByPersonName(String name)
   {
      Session session = sessionFactory.getCurrentSession();
      Query query = session.createQuery(" from Account a where a.subscriber.name.firstName like :name " +
            "or a.subscriber.name.lastName like :name " +
            "or a.subscriber.name.middleName like :name");
      query.setString("name", "%" + name + "%");
      return (List<Account>)query.list();
   }

   public void save(Account account)
   {
      this.sessionFactory.getCurrentSession().saveOrUpdate(account);
   }
}
