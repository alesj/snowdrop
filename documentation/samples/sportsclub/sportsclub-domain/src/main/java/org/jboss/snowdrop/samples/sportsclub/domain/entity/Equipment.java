package org.jboss.snowdrop.samples.sportsclub.domain.entity;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author <a href="mailto:mariusb@redhat.com">Marius Bogoevici</a>
 */

@Entity
public class Equipment
{
   @Id
   private long id;

   private String name;

   private String description;

   private EquipmentType equipmentType;


   public long getId()
   {
      return id;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription(String description)
   {
      this.description = description;
   }

   public String getName()
   {
      return name;
   }

   public void setName(String name)
   {
      this.name = name;
   }
}
