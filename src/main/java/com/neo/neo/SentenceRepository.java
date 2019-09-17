package com.neo.neo;
import org.springframework.data.repository.CrudRepository;

public interface SentenceRepository extends CrudRepository<Sentence, Long> {
    Sentence findByName(String name);
}
