package org.jboss.snowdrop.samples.sportsclub.jsf.beans;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.model.SelectItem;

import org.jboss.snowdrop.samples.sportsclub.domain.entity.Account;
import org.jboss.snowdrop.samples.sportsclub.domain.entity.BillingType;
import org.jboss.snowdrop.samples.sportsclub.ejb.SubscriptionService;

/**
 * @author Marius Bogoevici
 */
public class AccountEdit extends AccountBase
{

  @PostConstruct
  public void initAccount()
  {
     
  }

}
