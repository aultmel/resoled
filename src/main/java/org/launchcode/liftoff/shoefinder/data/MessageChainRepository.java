package org.launchcode.liftoff.shoefinder.data;

import org.launchcode.liftoff.shoefinder.models.MessageChain;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


//For Both Paging/Sorting and CRUD we use JPA Repository
    public interface MessageChainRepository extends JpaRepository<MessageChain, Long>{

}
