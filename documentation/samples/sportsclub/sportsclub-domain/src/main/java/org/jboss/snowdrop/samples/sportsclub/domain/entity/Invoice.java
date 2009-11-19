package org.jboss.snowdrop.samples.sportsclub.domain.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.sql.Date;

/**
 * @author <a href="mailto:mariusb@redhat.com">Marius Bogoevici</a>
 */
@Entity
public class Invoice
{

   @Id
   private long id;

   @ManyToOne
   private Account account;

   private Date date;

   private BigDecimal amount;

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

   public BigDecimal getAmount()
   {
      return amount;
   }

   public void setAmount(BigDecimal amount)
   {
      this.amount = amount;
   }

   public Date getDate()
   {
      return date;
   }

   public void setDate(Date date)
   {
      this.date = date;
   }
}
