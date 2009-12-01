package org.jboss.snowdrop.samples.stayfit.dao.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.jboss.snowdrop.samples.sportsclub.domain.repository.Repository;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author <a href="mailto:mariusb@redhat.com">Marius Bogoevici</a>
 */
public abstract class HibernateRepository<T, I extends Serializable> implements Repository<T, I>
{
   protected SessionFactory sessionFactory;

   Class<T> clazz;

   public HibernateRepository(Class<T> clazz)
   {
      this.clazz = clazz;
   }

   public void setSessionFactory(SessionFactory sessionFactory)
   {
      this.sessionFactory = sessionFactory;
   }

   protected Session getCurrentSession()
   {
      return this.sessionFactory.getCurrentSession();
   }

   
   public T findById(I id)
   {
      return (T)getCurrentSession().get(clazz, id);
   }

   public void save(T object)
   {
      getCurrentSession().saveOrUpdate(object);
   }

   public void delete(T object)
   {
      getCurrentSession().delete(object);
   }

   public Collection<T> findAll()
   {
      return (Collection<T>)getCurrentSession().createCriteria(clazz).list();
   }


   public int countAll()
   {
      return (Integer)getCurrentSession().createCriteria(clazz).setProjection(Projections.count("id")).uniqueResult();
   }


}
