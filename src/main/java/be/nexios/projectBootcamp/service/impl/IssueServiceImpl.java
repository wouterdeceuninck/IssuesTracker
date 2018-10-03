package be.nexios.projectBootcamp.service.impl;

import be.nexios.projectBootcamp.domain.Issue;
import be.nexios.projectBootcamp.service.IssueService;
import be.nexios.projectBootcamp.service.dto.IssueDTO;
import org.bson.types.ObjectId;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class IssueServiceImpl implements IssueService {

    @Override
    public Mono<IssueDTO> getIssue(ObjectId id) {
        return null;
    }

    @Override
    public Flux<IssueDTO> getAllIssues() {
        return null;
    }

    @Override
    public Mono<ObjectId> createIssue(Issue issue) {
        return null;
    }

    @Override
    public Mono<Void> deleteIssue(ObjectId id) {
        return null;
    }

    @Override
    public Mono<Void> updateIssue(ObjectId id, Issue issue) {
        return null;
    }
}
