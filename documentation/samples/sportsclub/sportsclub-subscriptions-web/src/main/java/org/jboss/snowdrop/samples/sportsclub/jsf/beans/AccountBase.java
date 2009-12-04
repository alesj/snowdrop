package org.jboss.snowdrop.samples.sportsclub.jsf.beans;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.model.SelectItem;

import org.jboss.snowdrop.samples.sportsclub.domain.entity.Account;
import org.jboss.snowdrop.samples.sportsclub.domain.entity.Address;
import org.jboss.snowdrop.samples.sportsclub.domain.entity.Name;
import org.jboss.snowdrop.samples.sportsclub.domain.entity.Person;
import org.jboss.snowdrop.samples.sportsclub.ejb.SubscriptionService;

/**
 * @author Marius Bogoevici
 */
public class AccountBase
{
   @EJB
   protected SubscriptionService subscriptionService;
   private ReferenceDataHelper referenceDataHelper;
   protected Account account = null;
   private SelectItem[] billingTypeItems;
   private SelectItem[] membershipCodes;

   public void setReferenceDataHelper(ReferenceDataHelper referenceDataHelper)
   {
      this.referenceDataHelper = referenceDataHelper;
   }

   @PostConstruct
   void init()
   {
      membershipCodes = referenceDataHelper.getMembershipCodes();
      billingTypeItems = referenceDataHelper.getBillingTypes();
   }

   public Account getAccount()
   {
      return account;
   }

   public SelectItem[] getBillingTypeItems()
   {
      return billingTypeItems;
   }

   public SelectItem[] getSelectableMembershipCodes()
   {
      return membershipCodes;
   }
}
