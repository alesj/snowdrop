package org.jboss.snowdrop.samples.sportsclub.ejb;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.interceptor.Interceptors;

import java.util.Date;
import java.util.List;

import org.jboss.annotation.spring.Spring;
import org.jboss.snowdrop.samples.sportsclub.domain.entity.Account;
import org.jboss.snowdrop.samples.sportsclub.domain.entity.BillingType;
import org.jboss.snowdrop.samples.sportsclub.domain.entity.Membership;
import org.jboss.snowdrop.samples.sportsclub.domain.entity.Person;
import org.jboss.snowdrop.samples.sportsclub.domain.repository.AccountRepository;
import org.jboss.snowdrop.samples.sportsclub.domain.repository.MembershipRepository;
import org.jboss.snowdrop.samples.sportsclub.domain.repository.PersonRepository;
import org.jboss.snowdrop.samples.sportsclub.domain.repository.criteria.AccountSearchCriteria;
import org.jboss.snowdrop.samples.sportsclub.domain.repository.criteria.PersonSearchCriteria;
import org.jboss.snowdrop.samples.sportsclub.domain.repository.criteria.Range;
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

   @Spring(bean = "membershipRepository", jndiName = "SpringDao")
   private MembershipRepository membershipRepository;

   public List<Account> findAccountsBySubscriberName(String name, int minIndex, int maxIndex)
   {
      PersonSearchCriteria personSearchCriteria = new PersonSearchCriteria();
      personSearchCriteria.setName(name);
      AccountSearchCriteria accountSearchCriteria = new AccountSearchCriteria();
      accountSearchCriteria.setPersonSearchCriteria(personSearchCriteria);
      accountSearchCriteria.setRange(new Range(minIndex, maxIndex));
      return accountRepository.findByCriteria(accountSearchCriteria);
   }

   public int countAccountsBySubscriberName(String name)
   {
      PersonSearchCriteria personSearchCriteria = new PersonSearchCriteria();
      personSearchCriteria.setName(name);
      AccountSearchCriteria accountSearchCriteria = new AccountSearchCriteria();
      accountSearchCriteria.setPersonSearchCriteria(personSearchCriteria);
      return accountRepository.countByCriteria(accountSearchCriteria);
   }

   @TransactionAttribute
   public Account createAccount(Person person, String membershipCode, BillingType billingType)
   {
      Account account = new Account();
      account.setSubscriber(person);
      membershipRepository.findById(membershipCode);
      account.setMembership(membershipRepository.findById(membershipCode));
      account.setBillingType(billingType);
      account.setCreationDate(new Date());
      personRepository.save(person);
      accountRepository.save(account);
      return account;
   }

   public List<String> getMembershipTypes()
   {
      return membershipRepository.findAllMembershipCodes();
   }
}
