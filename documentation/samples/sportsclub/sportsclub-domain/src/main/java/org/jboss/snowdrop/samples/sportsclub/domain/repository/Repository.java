package org.jboss.snowdrop.samples.sportsclub.domain.repository;

import java.util.Collection;

/**
 * @author <a href="mailto:mariusb@redhat.com">Marius Bogoevici</a>
 */
public interface Repository<T, I>
{
   T findById(I id);

   void save(T object);

   void delete(T object);

   Collection<T> findAll();
   
   int countAll();
}
