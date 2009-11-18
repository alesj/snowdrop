package org.jboss.snowdrop.samples.sportsclub.domain.entity;

import javax.persistence.Basic;
import javax.persistence.Embeddable;

/**
 * @author <a href="mailto:mariusb@redhat.com">Marius Bogoevici</a>
 */
@Embeddable
public class Name
{

   @Basic
   private String firstName;

   @Basic
   private String middleName;

   @Basic
   private String lastName;

   public String getFirstName()
   {
      return firstName;
   }

   public void setFirstName(String firstName)
   {
      this.firstName = firstName;
   }

   public String getLastName()
   {
      return lastName;
   }

   public void setLastName(String lastName)
   {
      this.lastName = lastName;
   }

   public String getMiddleName()
   {
      return middleName;
   }

   public void setMiddleName(String middleName)
   {
      this.middleName = middleName;
   }
}
