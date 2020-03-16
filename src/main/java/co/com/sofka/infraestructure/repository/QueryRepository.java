package co.com.sofka.infraestructure.repository;

import co.com.sofka.domain.generic.Query;

import java.util.Optional;
import java.util.stream.Stream;

public interface QueryRepository<E> {
    enum Sort {
        ASC, DESC
    }
    default Stream<E> findAll(){
        return Stream.empty();
    }
    default Stream<E> findAll(Sort sort){
        return Stream.empty();
    }
    default Stream<E> find(Query query){
        return Stream.empty();
    }
    default Stream<E> find(Query query, Sort sort){
        return Stream.empty();
    }
    default Optional<E> get(Query query){
        return Optional.empty();
    }
}
