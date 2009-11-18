package org.jboss.snowdrop.samples.sportsclub.domain.entity;

import org.jboss.snowdrop.samples.sportsclub.domain.entity.Person;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * @author <a href="mailto:mariusb@redhat.com">Marius Bogoevici</a>
 */
@Entity
public class Account
{
   @Id @GeneratedValue
   private long id;

   @OneToOne
   private Person subscriber;


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
}
