package org.launchcode.liftoff.shoefinder.data;

import org.launchcode.liftoff.shoefinder.models.MessageChain;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageChainRepository extends JpaRepository<MessageChain, Long> {
}
