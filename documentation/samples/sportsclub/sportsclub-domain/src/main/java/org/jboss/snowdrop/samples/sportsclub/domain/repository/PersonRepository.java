package org.jboss.snowdrop.samples.sportsclub.domain.repository;

import org.jboss.snowdrop.samples.sportsclub.domain.entity.Person;

/**
 * @author <a href="mailto:mariusb@redhat.com">Marius Bogoevici</a>
 */
public interface PersonRepository
{
   void save(Person person);
}
