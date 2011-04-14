package org.jboss.spring.samples.sportsclub.jsf.beans;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.model.SelectItem;
import java.util.List;

import org.jboss.spring.samples.sportsclub.domain.entity.BillingType;
import org.jboss.spring.samples.sportsclub.domain.entity.Membership;
import org.jboss.spring.samples.sportsclub.ejb.SubscriptionService;

/**
 * @author <a href="mailto:mariusb@redhat.com">Marius Bogoevici</a>
 */
public class ReferenceDataHelper
{

   @EJB
   SubscriptionService subscriptionService;

   private SelectItem[] billingTypes;

   @PostConstruct
   void init()
   {
      initBillingTypes();
   }

   private void initBillingTypes()
   {
      billingTypes = new SelectItem[BillingType.values().length];
      int i=0;
      for (BillingType billingType: BillingType.values())
      {
         billingTypes[i++] = new SelectItem(billingType);
      }
   }

   public SelectItem[] getBillingTypes()
   {
      return billingTypes;
   }



   public SelectItem[] getMembershipCodes()
   {
      List<String> membershipCodes = subscriptionService.getMembershipTypes();

      SelectItem[] selectItems = new SelectItem[membershipCodes.size()];
      int i = 0;
      for (String membershipCode: membershipCodes)
      {
         selectItems[i++] = new SelectItem(membershipCode);
      }
      
      return selectItems;
   }

}
