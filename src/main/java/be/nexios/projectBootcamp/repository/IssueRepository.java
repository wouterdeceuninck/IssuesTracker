package be.nexios.projectBootcamp.repository;

import be.nexios.projectBootcamp.domain.Project;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface IssueRepository  extends ReactiveMongoRepository<Project, ObjectId> {

}
