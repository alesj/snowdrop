package org.jboss.snowdrop.samples.stayfit.dao.hibernate.initializer;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jboss.snowdrop.samples.sportsclub.domain.entity.*;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.PostConstruct;
import java.lang.reflect.Member;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author <a href="mailto:mariusb@redhat.com">Marius Bogoevici</a>
 */
public class DatabaseInitializer implements InitializingBean
{

   private SessionFactory sessionFactory;

   private PlatformTransactionManager transactionManager;

   public void setSessionFactory(SessionFactory sessionFactory)
   {
      this.sessionFactory = sessionFactory;
   }

   public void setTransactionManager(PlatformTransactionManager transactionManager)
   {
      this.transactionManager = transactionManager;
   }

   public void afterPropertiesSet() throws Exception
   {
      new TransactionTemplate(transactionManager).execute(new TransactionCallback()
      {
         public Object doInTransaction(TransactionStatus status)
         {
            Session session = sessionFactory.getCurrentSession();

            Query query = session.createQuery("select count(m) from Membership m");

            if (((Long) query.uniqueResult()) > 0 )
               return null;

            Membership silverMembership = createMembership("SILVER", "600.0");
            save(session, silverMembership);
            Membership goldMembership = createMembership("GOLD", "900.0");
            save(session, goldMembership);
            Membership platinumMembership = createMembership("PLATINUM", "1200.0");
            save(session, platinumMembership);


            Person person = createPerson("Samuel", "Vimes", "1 Yonge", "Toronto", "Ontario", "Canada");
            save(session, person);
            save(session,createAccount(silverMembership, BillingType.MONTHLY, person));

            person = createPerson("Sibyl", "Vimes", "1 Yonge", "Toronto", "Ontario", "Canada");
            save(session,person);
            save(session, createAccount(goldMembership, BillingType.WEEKLY, person));

            person = createPerson("Havelock", "Vetinari", "1 Bloor", "Toronto", "Ontario", "Canada");
            save(session, person);
            save(session, createAccount(platinumMembership, BillingType.BIWEEKLY, person));

            person = createPerson("Nobby", "Nobbs", "1 Dufferin", "Toronto", "Ontario", "Canada");
            save(session, person);
            save(session, createAccount(goldMembership, BillingType.BIWEEKLY, person));

            person = createPerson("Carrot", "Ironfoundersson", "1 King", "Toronto", "Ontario", "Canada");
            save(session, person);
            save(session, createAccount(platinumMembership, BillingType.BIWEEKLY, person));



            return null;
         }
      });
   }

   private static void save(Session session, Object entity)
   {
      session.save(entity);
      session.flush();
   }

   private static Account createAccount(Membership silverMembership, BillingType billingType, Person person)
   {
      Account account = new Account();
      account.setSubscriber(person);
      account.setCreationDate(new Date());
      account.setBillingType(billingType);
      account.setMembership(silverMembership);
      return account;
   }

   private static Person createPerson(String firstname, String lastname, String street, String city, String province, String country)
   {
      Person person = new Person();
      person.setName(new Name());
      person.setAddress(new Address());

      person.getName().setFirstName(firstname);
      person.getName().setLastName(lastname);
      person.getAddress().setStreetAddress(street);
      person.getAddress().setCity(city);
      person.getAddress().setProvinceOrState(province);
      person.getAddress().setCountry(country);
      person.getAddress().setPostalCode("H0H0H0");
      return person;
   }

   private static Membership createMembership(String code, String amount)
   {
      Membership membership = new Membership(code);
      membership.setActive(true);
      membership.setAnnualFee(new BigDecimal(amount));
      return membership;
   }
}
