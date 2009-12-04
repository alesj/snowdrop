package org.jboss.snowdrop.samples.stayfit.dao.hibernate;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.jboss.snowdrop.samples.sportsclub.domain.entity.Membership;
import org.jboss.snowdrop.samples.sportsclub.domain.repository.MembershipRepository;

import java.util.List;

/**
 * @author <a href="mailto:mariusb@redhat.com">Marius Bogoevici</a>
 */
public class HibernateMembershipRepository extends HibernateRepository<Membership, String> implements MembershipRepository
{

   public HibernateMembershipRepository()
   {
      super(Membership.class);
   }

   public List<Membership> findAllActiveMembershipTypes()
   {
      Criteria query = getCurrentSession().createCriteria(Membership.class).add(Restrictions.eq("active", true));
      return query.list();
   }

   public int countAll()
   {
      return (Integer)getCurrentSession().createCriteria(Membership.class).setProjection(Projections.count("code")).uniqueResult();
   }

   public List<String> findAllMembershipCodes()
   {
      Criteria query = getCurrentSession().createCriteria(Membership.class).add(Restrictions.eq("active", true)).setProjection(Projections.property("code"));
      return query.list();
   }
}
