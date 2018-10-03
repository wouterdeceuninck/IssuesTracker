package be.nexios.projectBootcamp.service;

import be.nexios.projectBootcamp.domain.Issue;
import be.nexios.projectBootcamp.service.dto.IssueDTO;
import org.bson.types.ObjectId;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IssueService {
    //get an issue
    Mono<IssueDTO> getIssue(ObjectId id);
    //get all issues
    Flux<IssueDTO> getAllIssues();
    //create an issue
    Mono<ObjectId> createIssue(Issue issue);
    //delete an issue
    Mono<Void> deleteIssue(ObjectId id);
    //update an issue
    Mono<Void> updateIssue(ObjectId id, Issue issue);
}
