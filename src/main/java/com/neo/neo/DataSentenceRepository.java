package com.neo.neo;
import org.springframework.data.repository.CrudRepository;

public interface DataSentenceRepository extends CrudRepository<DataSentence, Long> {
    DataSentence findByName(String name);
}
