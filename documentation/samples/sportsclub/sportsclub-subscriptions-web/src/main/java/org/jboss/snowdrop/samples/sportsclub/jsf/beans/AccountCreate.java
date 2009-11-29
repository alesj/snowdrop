package org.jboss.snowdrop.samples.sportsclub.jsf.beans;

import org.jboss.snowdrop.samples.sportsclub.domain.entity.Account;
import org.jboss.snowdrop.samples.sportsclub.domain.entity.Address;
import org.jboss.snowdrop.samples.sportsclub.domain.entity.Membership;
import org.jboss.snowdrop.samples.sportsclub.domain.entity.Name;
import org.jboss.snowdrop.samples.sportsclub.domain.entity.Person;
import org.jboss.snowdrop.samples.sportsclub.ejb.SubscriptionService;

import javax.ejb.EJB;

/**
 * @author <a href="mailto:mariusb@redhat.com">Marius Bogoevici</a>
 */
public class AccountCreate
{
   @EJB
   private SubscriptionService subscriptionService;

   private ReferenceData referenceData;

   private String firstName;

   private String middleName;

   private String lastName;

   private String city;

   private String provinceOrState;

   private String address;

   private Account account = null;

   private Membership membership = null;

   public void setReferenceData(ReferenceData referenceData)
   {
      this.referenceData = referenceData;
   }

   public String getAddress()
   {
      return address;
   }

   public void setAddress(String address)
   {
      this.address = address;
   }

   public String getCity()
   {
      return city;
   }

   public void setCity(String city)
   {
      this.city = city;
   }

   public String getProvinceOrState()
   {
      return provinceOrState;
   }

   public void setProvinceOrState(String provinceOrState)
   {
      this.provinceOrState = provinceOrState;
   }

   public String getFirstName()
   {
      return firstName;
   }

   public void setFirstName(String firstName)
   {
      this.firstName = firstName;
   }

   public String getLastName()
   {
      return lastName;
   }

   public void setLastName(String lastName)
   {
      this.lastName = lastName;
   }

   public Account getAccount()
   {
      return account;
   }

   public String create()
   {
      Person person = new Person();
      person.setAddress(new Address());
      person.setName(new Name());

      person.getName().setFirstName(firstName);
      person.getName().setLastName(lastName);


      person.getAddress().setCity(city);
      person.getAddress().setProvinceOrState(this.provinceOrState);
      person.getAddress().setStreetAddress(address);


      account = subscriptionService.createAccountForPerson(person, membership);
      return "success";
   }

   public void setMiddleName(String middleName)
   {
      this.middleName = middleName;
   }

   public String getMiddleName()
   {
      return middleName;
   }

   public void setMembership(Membership membership)
   {
      this.membership = membership;
   }

   public Membership getMembership()
   {
      return membership;
   }
}
