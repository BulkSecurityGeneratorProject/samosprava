package frido.samosprava.repository;

import frido.samosprava.domain.Vote;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the Vote entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VoteRepository extends MongoRepository<Vote, String> {

}
