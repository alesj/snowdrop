package org.jboss.snowdrop.samples.sportsclub.domain.repository;

import java.util.List;

import org.jboss.snowdrop.samples.sportsclub.domain.entity.Account;

/**
 * @author <a href="mailto:mariusb@redhat.com">Marius Bogoevici</a>
 */
public interface AccountRepository
{
   List<Account> findByPersonName(String name);

   void save(Account account);
}
