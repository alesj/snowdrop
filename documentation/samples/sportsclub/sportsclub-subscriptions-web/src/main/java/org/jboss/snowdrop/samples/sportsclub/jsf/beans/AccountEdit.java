package org.jboss.spring.samples.sportsclub.jsf.beans;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.model.SelectItem;

import org.jboss.spring.samples.sportsclub.domain.entity.Account;
import org.jboss.spring.samples.sportsclub.domain.entity.BillingType;
import org.jboss.spring.samples.sportsclub.ejb.SubscriptionService;

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
