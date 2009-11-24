package org.jboss.snowdrop.samples.sportsclub.ejb;

import javax.ejb.Local;
import java.util.List;

import org.jboss.snowdrop.samples.sportsclub.domain.entity.Account;
import org.jboss.snowdrop.samples.sportsclub.domain.entity.Person;

/**
 * @author <a href="mailto:mariusb@redhat.com">Marius Bogoevici</a>
 */
@Local
public interface SubscriptionService
{
   List<Account> findAccountsBySubscriberName(String name);

   Account createAccountForPerson(Person person);
}
