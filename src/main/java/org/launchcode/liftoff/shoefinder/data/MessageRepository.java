package org.launchcode.liftoff.shoefinder.data;

import org.launchcode.liftoff.shoefinder.models.Message;
import org.launchcode.liftoff.shoefinder.models.MessageChain;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;


//For Both Paging/Sorting and CRUD we use JPA Repository
public interface MessageRepository extends JpaRepository<Message, Long>{

    void findAll(List<MessageChain> userEntityMessageChains, Pageable pageable);
}
