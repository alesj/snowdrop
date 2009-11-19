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
}
