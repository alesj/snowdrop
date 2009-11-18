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

   @Basic
   private String name;

   @Basic
   private String description;

}
