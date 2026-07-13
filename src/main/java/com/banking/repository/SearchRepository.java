package com.banking.repository;

import com.banking.model.Search;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SearchRepository extends JpaRepository<Search, String>{
    List<Search> findById(int Id); /*Will circle back on this, need to ask team what we will do*/
}
