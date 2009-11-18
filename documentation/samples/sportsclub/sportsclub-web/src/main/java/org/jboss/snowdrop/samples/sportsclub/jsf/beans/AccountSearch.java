package org.jboss.snowdrop.samples.sportsclub.jsf.beans;

import org.jboss.snowdrop.samples.sportsclub.domain.entity.Account;
import org.jboss.snowdrop.samples.stayfit.ejb.SubscriptionService;

import javax.ejb.EJB;
import java.util.Collections;
import java.util.List;

/**
 * @author <a href="mailto:mariusb@redhat.com">Marius Bogoevici</a>
 */
public class AccountSearch
{
   @EJB
   private SubscriptionService subscriptionService;

   private String name;

   private List<Account> accounts = Collections.<Account>emptyList();

   public String getName()
   {
      return name;
   }

   public void setName(String name)
   {
      this.name = name;
   }

   public String searchAccounts()
   {
      accounts = subscriptionService.findAccountsBySubscriberName(name);
      return "success";
   }

   public List<Account> getAccounts()
   {

      return accounts;

   }
   
}
