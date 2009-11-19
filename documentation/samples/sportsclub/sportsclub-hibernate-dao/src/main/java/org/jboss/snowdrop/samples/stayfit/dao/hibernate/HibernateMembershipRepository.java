package org.jboss.snowdrop.samples.stayfit.dao.hibernate;

import org.hibernate.Query;
import org.jboss.snowdrop.samples.sportsclub.domain.entity.Membership;
import org.jboss.snowdrop.samples.sportsclub.domain.repository.MembershipRepository;

import java.util.List;

/**
 * @author <a href="mailto:mariusb@redhat.com">Marius Bogoevici</a>
 */
public class HibernateMembershipRepository extends HibernateRepository<Membership, Integer> implements MembershipRepository
{

   public HibernateMembershipRepository()
   {
      super(Membership.class);
   }

   public List<Membership> findAllActiveMembershipTypes()
   {
      Query query = getCurrentSession().createQuery("from Membership m where m.active");
      return query.list();
   }
}
