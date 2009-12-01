package org.jboss.snowdrop.samples.sportsclub.jsf.beans;

import javax.faces.convert.EnumConverter;

import org.jboss.snowdrop.samples.sportsclub.domain.entity.BillingType;

/**
 * @author Marius Bogoevici
 */
public class BillingTypeConverter extends EnumConverter
{
   public BillingTypeConverter()
   {
      super(BillingType.class);  
   }
}
