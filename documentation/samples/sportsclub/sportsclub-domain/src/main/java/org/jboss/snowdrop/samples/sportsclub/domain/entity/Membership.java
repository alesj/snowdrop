package org.jboss.snowdrop.samples.sportsclub.domain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * @author <a href="mailto:mariusb@redhat.com">Marius Bogoevici</a>
 */
@Entity
public class Membership
{

   @Id
   private String code;

   private BigDecimal annualFee;

   private boolean active;

   private Membership()
   {}

   public Membership(String code)
   {
      this.code = code;
   }

   public String getCode()
   {
      return code;
   }

   public BigDecimal getAnnualFee()
   {
      return annualFee;
   }

   public void setAnnualFee(BigDecimal annualFee)
   {
      this.annualFee = annualFee;
   }

   public boolean isActive()
   {
      return active;
   }

   public void setActive(boolean active)
   {
      this.active = active;
   }

   @Override
   public boolean equals(Object o)
   {
      if (this == o)
      {
         return true;
      }
      if (o == null || getClass() != o.getClass())
      {
         return false;
      }

      Membership that = (Membership) o;

      if (code != null ? !code.equals(that.code) : that.code != null)
      {
         return false;
      }

      return true;
   }

   @Override
   public int hashCode()
   {
      return code != null ? code.hashCode() : 0;
   }
   
}
