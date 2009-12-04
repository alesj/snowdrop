package org.jboss.snowdrop.samples.sportsclub.jsf.beans;

import org.ajax4jsf.model.DataVisitor;
import org.ajax4jsf.model.ExtendedDataModel;
import org.ajax4jsf.model.Range;
import org.ajax4jsf.model.SequenceRange;
import org.jboss.snowdrop.samples.sportsclub.domain.entity.Account;
import org.jboss.snowdrop.samples.sportsclub.ejb.SubscriptionService;

import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:mariusb@redhat.com">Marius Bogoevici</a>
 */
public class AccountSearch extends ExtendedDataModel
{
   @EJB
   private SubscriptionService subscriptionService;

   private String name;

   private int currentPage;
   private int currentRow;
   private Long currentId;

   private Map<Long, Account> accountsMap = new HashMap<Long, Account>();
   private Integer rowCount;

   public String getName()
   {
      return name;
   }

   public void setName(String name)
   {
      this.name = name;
   }

   public String searchAccounts()
   {
      rowCount = subscriptionService.countAccountsBySubscriberName(name);
      currentPage = 1;
      return "success";
   }

   public int getCurrentPage()
   {
      return currentPage;
   }

   public void setCurrentPage(int currentPage)
   {
      this.currentPage = currentPage;
   }

   @Override
   public Object getRowKey()
   {
      return currentId;
   }

   @Override
   public void setRowKey(Object key)
   {
      if (key != null)
         currentId = (Long) key;
   }

   @Override
   public boolean isRowAvailable()
   {
      if (currentId == null)
         return false;
      if (accountsMap.containsKey(currentId))
         return true;
      return false;
   }

   @Override
   public int getRowCount()
   {
      if (rowCount == null)
      {
         rowCount = subscriptionService.countAccountsBySubscriberName(name);
      }
      return rowCount;
   }

   public boolean isSearchInfoAvailable()
   {
      return name != null;
   }

   @Override
   public Object getRowData()
   {
      return accountsMap.get(currentId);
   }

   @Override
   public void walk(FacesContext context, DataVisitor visitor, Range range, Object argument) throws IOException
   {
      int firstResult = ((SequenceRange) range).getFirstRow();
      int maxResults = ((SequenceRange) range).getRows();
      List<Account> list = subscriptionService.findAccountsBySubscriberName(name, firstResult, maxResults);
      accountsMap = new HashMap<Long, Account>();
      for (Account row : list)
      {
         Long id = row.getId();
         accountsMap.put(id, row);
         visitor.process(context, id, argument);
      }
   }

   @Override
   public int getRowIndex()
   {
      return currentRow;
   }

   @Override
   public void setRowIndex(int rowIndex)
   {
      this.currentRow = rowIndex;
   }

   @Override
   public Object getWrappedData()
   {
      throw new UnsupportedOperationException("Not supported");
   }

   @Override
   public void setWrappedData(Object data)
   {
      throw new UnsupportedOperationException("Not supported");
   }
}
