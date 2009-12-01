package org.jboss.snowdrop.samples.sportsclub.domain.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * @author <a href="mailto:mariusb@redhat.com">Marius Bogoevici</a>
 */

@Entity
public class Reservation
{

   @Id @GeneratedValue
   private long id;

   @Column(name = "fromDT")
   private Date from;

   @Column(name = "toDT")
   private Date to;

   @ManyToOne
   private Equipment equipment;

   @ManyToOne
   private Account account;


   public long getId()
   {
      return id;
   }

   public Account getAccount()
   {
      return account;
   }

   public void setAccount(Account account)
   {
      this.account = account;
   }

   public Equipment getEquipment()
   {
      return equipment;
   }

   public void setEquipment(Equipment equipment)
   {
      this.equipment = equipment;
   }

   public Date getFrom()
   {
      return from;
   }

   public void setFrom(Date from)
   {
      this.from = from;
   }

   public Date getTo()
   {
      return to;
   }

   public void setTo(Date to)
   {
      this.to = to;
   }
}
