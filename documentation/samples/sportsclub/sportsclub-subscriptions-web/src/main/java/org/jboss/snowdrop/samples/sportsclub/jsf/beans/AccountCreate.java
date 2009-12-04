package org.jboss.snowdrop.samples.sportsclub.jsf.beans;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.model.SelectItem;

import org.jboss.snowdrop.samples.sportsclub.domain.entity.Account;
import org.jboss.snowdrop.samples.sportsclub.domain.entity.Address;
import org.jboss.snowdrop.samples.sportsclub.domain.entity.BillingType;
import org.jboss.snowdrop.samples.sportsclub.domain.entity.Name;
import org.jboss.snowdrop.samples.sportsclub.domain.entity.Person;
import org.jboss.snowdrop.samples.sportsclub.ejb.SubscriptionService;

/**
 * @author <a href="mailto:mariusb@redhat.com">Marius Bogoevici</a>
 */
public class AccountCreate extends AccountBase
{

   private Person person;

   private BillingType billingType;

   @PostConstruct
   public void initPerson()
   {
      person = new Person();
      person.setAddress(new Address());
      person.setName(new Name());
   }

   // reference data

   private String membershipType;

   public Person getPerson()
   {
      return person;
   }

   public void setPerson(Person person)
   {
      this.person = person;
   }

   public String create()
   {
      account = subscriptionService.createAccount(person, membershipType, billingType);

      return "success";
   }

   public void setMembershipType(String membershipType)
   {
      this.membershipType = membershipType;
   }

   public String getMembershipType()
   {
      return membershipType;
   }

   public void setBillingType(BillingType billingType)
   {
      this.billingType = billingType;
   }

   public BillingType getBillingType()
   {
      return billingType;
   }

}
