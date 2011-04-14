package org.jboss.spring.samples.sportsclub.domain.repository;

import org.jboss.spring.samples.sportsclub.domain.entity.Person;
import org.jboss.spring.samples.sportsclub.domain.repository.criteria.AccountSearchCriteria;

/**
 * @author <a href="mailto:mariusb@redhat.com">Marius Bogoevici</a>
 */
public interface PersonRepository extends Repository<Person, Integer>
{

}
