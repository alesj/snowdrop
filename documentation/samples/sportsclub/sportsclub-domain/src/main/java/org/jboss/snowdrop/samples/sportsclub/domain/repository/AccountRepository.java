package org.jboss.snowdrop.samples.sportsclub.domain.repository;

import java.util.List;

import org.jboss.snowdrop.samples.sportsclub.domain.entity.Account;

/**
 * @author <a href="mailto:mariusb@redhat.com">Marius Bogoevici</a>
 */
public interface AccountRepository extends Repository<Account, Integer>
{
   List<Account> findByPersonName(String name);
}
