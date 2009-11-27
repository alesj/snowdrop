package org.jboss.snowdrop.samples.stayfit.dao.hibernate;

import static org.hibernate.criterion.Restrictions.eq;
import static org.hibernate.criterion.Restrictions.eqProperty;
import static org.hibernate.criterion.Restrictions.ilike;
import static org.hibernate.criterion.Restrictions.or;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.jboss.snowdrop.samples.sportsclub.domain.entity.Account;
import org.jboss.snowdrop.samples.sportsclub.domain.repository.AccountRepository;
import org.jboss.snowdrop.samples.sportsclub.domain.repository.criteria.AccountSearchCriteria;
import org.jboss.snowdrop.samples.sportsclub.domain.repository.criteria.PersonSearchCriteria;

/**
 * @author <a href="mailto:mariusb@redhat.com">Marius Bogoevici</a>
 */
public class HibernateAccountRepository extends HibernateRepository<Account, Integer> implements AccountRepository
{

   public HibernateAccountRepository()
   {
      super(Account.class);
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


   public int countByCriteria(AccountSearchCriteria accountSearchCriteria)
   {
      Criteria criteria = convert(accountSearchCriteria);
      criteria.setProjection(Projections.count("id"));
      return (Integer)criteria.uniqueResult();
   }

   private Criteria convert(AccountSearchCriteria accountSearchCriteria)
   {
      Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Account.class);
      if (accountSearchCriteria.getPersonSearchCriteria() != null)
      {
         PersonSearchCriteria personSearchCriteria = accountSearchCriteria.getPersonSearchCriteria();
         Criteria personCriteria = criteria.createCriteria("subscriber");
         if (personSearchCriteria.getName() != null)
         {
            personCriteria.add(
                  or(ilike("name.firstName", personSearchCriteria.getName(), MatchMode.ANYWHERE),
                     or(ilike("name.lastName", personSearchCriteria.getName(), MatchMode.ANYWHERE), 
                           ilike("name.middleName", personSearchCriteria.getName(), MatchMode.ANYWHERE))));
         }
         if (personSearchCriteria.getCity() != null)
         {
            personCriteria.add(ilike("address.city", personSearchCriteria.getCity(), MatchMode.ANYWHERE));
         }
      }    
      return criteria;
   }

   public List<Account> findByCriteria(AccountSearchCriteria accountSearchCriteria)
   {
      Criteria criteria = convert(accountSearchCriteria);
      if (accountSearchCriteria.getRange()!=null)
      {
         criteria.setFirstResult(accountSearchCriteria.getRange().getMinIndex());
         criteria.setMaxResults(accountSearchCriteria.getRange().length());
      }
      return (List<Account>) criteria.list();
   }
}
