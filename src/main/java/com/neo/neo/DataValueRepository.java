package com.neo.neo;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DataValueRepository extends CrudRepository<DataValue, Long> {
    DataValue findByName(String name);


}
