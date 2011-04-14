package org.jboss.spring.samples.sportsclub.domain.repository;

import org.jboss.spring.samples.sportsclub.domain.entity.Membership;

import java.util.List;

/**
 * @author <a href="mailto:mariusb@redhat.com">Marius Bogoevici</a>
 */
public interface MembershipRepository extends Repository<Membership, String>
{
   List<Membership> findAllActiveMembershipTypes();

   List<String> findAllMembershipCodes();

}
