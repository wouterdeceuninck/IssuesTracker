package be.nexios.projectBootcamp.service.impl;

import be.nexios.projectBootcamp.domain.Issue;
import be.nexios.projectBootcamp.domain.Project;
import be.nexios.projectBootcamp.exception.BadRequestException;
import be.nexios.projectBootcamp.exception.NotFoundException;
import be.nexios.projectBootcamp.repository.IssueRepository;
import be.nexios.projectBootcamp.service.IssueService;
import be.nexios.projectBootcamp.service.dto.IssueDTO;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class IssueServiceImpl implements IssueService {

    private final IssueRepository issueRepository;

    public IssueServiceImpl (IssueRepository issueRepository ){
        this.issueRepository = issueRepository;
    }

    private Mono<IssueDTO> getIssueThrowWhenAbsent(ObjectId id) {
        return issueRepository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("The issue with the id " + id + " cannot be found!")))
                .map(found -> mapToIssueDTO(found));
    }

    private Issue mapToIssue(IssueDTO issueDTO) {
        return Issue.builder()
                .assigneeId(issueDTO.getAssigneeId())
                .creationTimestamp(issueDTO.getCreationTimestamp())
                .creatorId(issueDTO.getCreatorId())
                .description(issueDTO.getDescription())
                .finishedDate(issueDTO.getFinishedDate())
                .id(new ObjectId(issueDTO.getId()))
                .priority(issueDTO.getPriority())
                .status(issueDTO.getStatus())
                .build();
    }

    public IssueDTO mapToIssueDTO(Issue issue) {
        return IssueDTO.builder().assigneeId(issue.getAssigneeId())
                .creationTimestamp(issue.getCreationTimestamp())
                .creatorId(issue.getCreatorId())
                .description(issue.getDescription())
                .id(issue.getId().toHexString())
                .finishedDate(issue.getFinishedDate())
                .priority(issue.getPriority())
                .status(issue.getStatus())
                .build();
    }

    @Override
    public Mono<IssueDTO> getIssue(ObjectId id) {
        return getIssueThrowWhenAbsent(id);
    }

    @Override
    public Flux<IssueDTO> getAllIssues() {
        return issueRepository.findAll()
                .map(found -> mapToIssueDTO(found));
    }

    @Override
    public Mono<ObjectId> createIssue(IssueDTO issueDTO) {
        return issueRepository.insert(mapToIssue(issueDTO)).map(Issue::getId );
    }

    @Override
    public Mono<Void> deleteIssue(ObjectId id) {
        return getIssueThrowWhenAbsent(id)
                .flatMap(existing -> issueRepository.deleteById(id).then());
    }

    @Override
    public Mono<Void> updateIssue(ObjectId id, IssueDTO issueDTO) {
        return getIssueThrowWhenAbsent(id)
                .flatMap(existing -> {
                    if (!existing.getId().equals(id)){
                        return Mono.error(new BadRequestException("Bad Request"));
                    }
                    return issueRepository.save(mapToIssue(issueDTO)).then();
                });
    }
}
