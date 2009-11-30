package org.jboss.snowdrop.samples.sportsclub.domain.entity;

import org.jboss.snowdrop.samples.sportsclub.domain.entity.Person;

import javax.persistence.*;
import java.util.Date;

/**
 * @author <a href="mailto:mariusb@redhat.com">Marius Bogoevici</a>
 */
@Entity
public class Account
{
   @Id @GeneratedValue
   private Long id;

   @OneToOne
   private Person subscriber;

   private Date creationDate;

   @ManyToOne
   private Membership membership;

   private BillingType billingType;

   public long getId()
   {
      return id;
   }

   public Person getSubscriber()
   {
      return subscriber;
   }

   public void setSubscriber(Person subscriber)
   {
      this.subscriber = subscriber;
   }

   public BillingType getBillingType()
   {
      return billingType;
   }

   public void setBillingType(BillingType billingType)
   {
      this.billingType = billingType;
   }

   public Date getCreationDate()
   {
      return creationDate;
   }

   public void setCreationDate(Date creationDate)
   {
      this.creationDate = creationDate;
   }

   public Membership getMembership()
   {
      return membership;
   }

   public void setMembership(Membership membership)
   {
      this.membership = membership;
   }
}
