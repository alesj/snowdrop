package org.jboss.snowdrop.samples.sportsclub.domain.repository;

import java.util.Collection;

import org.jboss.snowdrop.samples.sportsclub.domain.entity.Person;
import org.jboss.snowdrop.samples.sportsclub.domain.repository.criteria.AccountSearchCriteria;

/**
 * @author <a href="mailto:mariusb@redhat.com">Marius Bogoevici</a>
 */
public interface PersonRepository extends Repository<Person, Integer>
{

}
