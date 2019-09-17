package com.neo.neo;
import org.springframework.data.repository.CrudRepository;

public interface CFileRepository extends CrudRepository<CFile, Long> {
    CFile findByName(String name);
}
