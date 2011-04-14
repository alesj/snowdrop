package org.jboss.spring.samples.sportsclub.jsf.beans;

import javax.faces.convert.EnumConverter;

import org.jboss.spring.samples.sportsclub.domain.entity.BillingType;

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
