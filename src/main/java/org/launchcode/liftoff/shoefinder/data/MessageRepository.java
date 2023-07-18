package org.launchcode.liftoff.shoefinder.data;

import org.launchcode.liftoff.shoefinder.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;


//For Both Paging/Sorting and CRUD we use JPA Repository
public interface MessageRepository extends JpaRepository<Message, Long>{

}
