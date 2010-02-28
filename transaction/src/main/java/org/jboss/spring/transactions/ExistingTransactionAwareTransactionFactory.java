package org.jboss.spring.transactions;

import javax.transaction.NotSupportedException;
import javax.transaction.Status;
import javax.transaction.SystemException;
import javax.transaction.Transaction;

import org.springframework.transaction.jta.JtaTransactionManager;
import org.springframework.transaction.jta.TransactionFactory;

/**
 * A {@link org.springframework.transaction.jta.TransactionFactory} implementation that
 * will check for an existing JTA transaction before creating a new one.
 *
 * To be used in JCA/JMS scenarios, where Spring will try to create a transaction
 * before processing the message (although JBoss creates one already).
 * 
 * @author Marius Bogoevici
 */
public class ExistingTransactionAwareTransactionFactory implements TransactionFactory
{

   private JtaTransactionManager wrappedJtaTransactionManager;


   public JtaTransactionManager getWrappedJtaTransactionManager()
   {
      return wrappedJtaTransactionManager;
   }

   public void setWrappedJtaTransactionManager(JtaTransactionManager wrappedJtaTransactionManager)
   {
      this.wrappedJtaTransactionManager = wrappedJtaTransactionManager;
   }

   public Transaction createTransaction(String name, int timeout)
         throws NotSupportedException, SystemException
   {
      Transaction transaction = this.getWrappedJtaTransactionManager().getTransactionManager().getTransaction();

      if (transaction != null && (transaction.getStatus() != Status.STATUS_NO_TRANSACTION))
      {
         return transaction;
      }
      else
      {
         return getWrappedJtaTransactionManager().createTransaction(name, timeout);
      }
   }


}