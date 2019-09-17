package com.neo.neo;
import org.springframework.data.repository.CrudRepository;

public interface PieceRepository extends CrudRepository<Piece, Long> {
    Piece findByName(String name);
}
