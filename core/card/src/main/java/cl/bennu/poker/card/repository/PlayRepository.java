package cl.bennu.poker.card.repository;

import cl.bennu.poker.card.model.Play;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayRepository extends MongoRepository<Play, String> {

    @Query(value = "{'id' : ?0 }")
    Play getById(String uuid);

}
