package org.jboss.spring.samples.sportsclub.ejb;

import javax.ejb.Local;
import javax.ejb.TransactionAttribute;
import java.util.List;

import org.jboss.spring.samples.sportsclub.domain.entity.Account;
import org.jboss.spring.samples.sportsclub.domain.entity.BillingType;
import org.jboss.spring.samples.sportsclub.domain.entity.Membership;
import org.jboss.spring.samples.sportsclub.domain.entity.Person;

/**
 * @author <a href="mailto:mariusb@redhat.com">Marius Bogoevici</a>
 */
@Local
public interface SubscriptionService
{
   int countAccountsBySubscriberName(String name);

   List<Account> findAccountsBySubscriberName(String name, int minIndex, int maxIndex);

   List<String> getMembershipTypes();

   Account createAccount(Person person, String membershipCode, BillingType billingType);
}
