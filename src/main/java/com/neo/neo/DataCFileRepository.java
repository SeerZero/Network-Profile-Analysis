package com.neo.neo;
import org.springframework.data.repository.CrudRepository;

public interface DataCFileRepository extends CrudRepository<DataCFile, Long> {
    DataCFile findByName(String name);
}
