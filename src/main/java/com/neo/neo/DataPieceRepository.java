package com.neo.neo;
import org.springframework.data.repository.CrudRepository;

public interface DataPieceRepository extends CrudRepository<DataPiece, Long> {
    DataPiece findByName(String name);
}
