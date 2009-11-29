package org.jboss.snowdrop.samples.sportsclub.jsf.beans;

import javax.ejb.EJB;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import org.jboss.snowdrop.samples.sportsclub.domain.entity.Membership;
import org.jboss.snowdrop.samples.sportsclub.ejb.SubscriptionService;

/**
 * @author <a href="mailto:mariusb@redhat.com">Marius Bogoevici</a>
 */
public class ReferenceData
{

   @EJB
   SubscriptionService subscriptionService;

   Map<String, Membership> membershipTypes;

   ReentrantLock lock = new ReentrantLock();

   public Map<String, Membership> getMembershipTypes()
   {

      if (membershipTypes == null)
      {
         try
         {
            lock.lock();
            if (membershipTypes == null)
            {
               Collection<Membership> memberships = subscriptionService.getMembershipTypes();
               membershipTypes = new HashMap<String, Membership>();
               for (Membership membership: memberships)
               {
                  membershipTypes.put(membership.getCode(), membership);
               }
            }
         }
         finally
         {
            lock.unlock();
         }
      }

      return membershipTypes;
   }
}
