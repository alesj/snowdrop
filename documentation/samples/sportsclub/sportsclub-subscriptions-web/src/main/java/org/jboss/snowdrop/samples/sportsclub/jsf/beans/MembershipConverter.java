package org.jboss.snowdrop.samples.sportsclub.jsf.beans;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItem;
import javax.faces.component.UISelectOne;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.jboss.snowdrop.samples.sportsclub.domain.entity.Membership;

/**
 * @author <a href="mailto:mariusb@redhat.com">Marius Bogoevici</a>
 */
public class MembershipConverter implements Converter
{
   public Object getAsObject(FacesContext context, UIComponent component, String value)
   {
      if (value == null)
         return null;
      if (component instanceof UISelectOne)
      {
         return ((UISelectOne)component);
      }
      return null;
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
