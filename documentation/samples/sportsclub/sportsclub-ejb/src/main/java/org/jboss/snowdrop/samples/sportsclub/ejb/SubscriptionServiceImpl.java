package org.jboss.snowdrop.samples.sportsclub.ejb;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import java.util.List;

import org.jboss.annotation.spring.Spring;
import org.jboss.snowdrop.samples.sportsclub.domain.entity.Account;
import org.jboss.snowdrop.samples.sportsclub.domain.entity.Person;
import org.jboss.snowdrop.samples.sportsclub.domain.repository.AccountRepository;
import org.jboss.snowdrop.samples.sportsclub.domain.repository.PersonRepository;
import org.jboss.snowdrop.samples.sportsclub.ejb.SubscriptionService;
import org.jboss.spring.callback.SpringLifecycleInterceptor;

/**
 * @author <a href="mailto:mariusb@redhat.com">Marius Bogoevici</a>
 */
@Stateless
@Interceptors(SpringLifecycleInterceptor.class)
public class SubscriptionServiceImpl implements SubscriptionService
{

   @Spring(bean = "accountRepository", jndiName = "SpringDao")
   private AccountRepository accountRepository;

   @Spring(bean = "personRepository", jndiName = "SpringDao")
   private PersonRepository personRepository;

   public List<Account> findAccountsBySubscriberName(String name)
   {
      return accountRepository.findByPersonName(name);
   }

   public Account createAccountForPerson(Person person)
   {
      Account account = new Account();
      account.setSubscriber(person);
      personRepository.save(person);
      accountRepository.save(account);
      return account;
   }

}
