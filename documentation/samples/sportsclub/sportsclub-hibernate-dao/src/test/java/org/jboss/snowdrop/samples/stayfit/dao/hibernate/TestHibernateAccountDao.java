package org.jboss.snowdrop.samples.stayfit.dao.hibernate;

import java.util.Collection;

import org.jboss.snowdrop.samples.sportsclub.domain.entity.Account;
import org.jboss.snowdrop.samples.sportsclub.domain.repository.AccountRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author <a href="mailto:mariusb@redhat.com">Marius Bogoevici</a>
 */
@ContextConfiguration(locations = {"classpath:test-infrastructure.xml", "classpath:dao-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class TestHibernateAccountDao
{

   @Autowired
   AccountRepository accountRepository;

   @Test
   @Transactional
   public void testAccountRepository()
   {
      Collection<Account> accounts = accountRepository.findAll();
      Assert.assertEquals(12, accounts.size());
   }
}
