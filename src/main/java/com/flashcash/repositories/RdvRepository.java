package com.flashcash.repositories;

import com.flashcash.models.RDV;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface RdvRepository extends CrudRepository<RDV, Integer> {

    @Query(value = "CALL spRdvList(:userId, :year)", nativeQuery = true)
    List<Object[]> callSpRdvList(@Param("userId") Integer userId,
                                         @Param("year") Integer year);


}
