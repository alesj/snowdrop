package org.jboss.snowdrop.samples.sportsclub.jsf.beans;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.jboss.snowdrop.samples.sportsclub.domain.entity.Membership;

/**
 * @author <a href="mailto:mariusb@redhat.com">Marius Bogoevici</a>
 */
public class MembershipConverter implements Converter
{

   private ReferenceData referenceData;

   public void setReferenceData(ReferenceData referenceData)
   {
      this.referenceData = referenceData;
   }

   public Object getAsObject(FacesContext context, UIComponent component, String value)
   {
      if (value == null)
         return null;
      if (!referenceData.getMembershipTypes().containsKey(value))
      {
         FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Wrong code", "Membership code " + value + " does not exist or is not active.");
         throw new ConverterException(message);
      }
      return referenceData.getMembershipTypes().get(value);
   }

   public String getAsString(FacesContext context, UIComponent component, Object value)
   {
      if (value == null)
         return null;
      if (!(value instanceof Membership))
      {
         FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Wrong type", "Object" + value + " is not of " + Membership.class.getName() + " type");
         throw new ConverterException(message);
      }
      return ((Membership)value).getCode();
   }
}
