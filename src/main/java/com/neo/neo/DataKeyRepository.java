package com.neo.neo;
import org.springframework.data.repository.CrudRepository;

public interface DataKeyRepository extends CrudRepository<DataKey, Long> {
    DataKey findByName(String name);
}
