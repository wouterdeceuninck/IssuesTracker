package be.nexios.projectBootcamp.controller;

import be.nexios.projectBootcamp.exception.BadRequestException;
import be.nexios.projectBootcamp.exception.NotFoundException;
import be.nexios.projectBootcamp.service.IssueService;
import be.nexios.projectBootcamp.service.dto.IssueDTO;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.xml.ws.Response;
import java.net.URI;

@RestController
public class IssueController {

    private final IssueService issueService;

    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }
//
//    @PostMapping("/api/project/{id}/issue")
//    public Mono<ResponseEntity<IssueDTO>> createIssue(@PathVariable("id") ObjectId id,
//                                                      @Valid @RequestBody IssueDTO issueDTO) {
//        return this.issueService.createIssue(id, issueDTO).map(issueId ->
//                ResponseEntity.created(URI.create("/api/"+id.toHexString()+"/issue/"+issueId))
//                .build());
//    }
//
//    @GetMapping("/api/project/{projectId}/issue/{issueId}")
//    public Mono<IssueDTO> getIssue(@PathVariable("projectId") ObjectId projectId,
//                               @PathVariable("issueId") ObjectId issueId) {
//        return this.issueService.getIssue(projectId, issueId);
//    }
//
//    @GetMapping("/api/project/{id}/issue")
//    public Flux<IssueDTO> getAllIssues(@PathVariable("id") ObjectId id) {
//        return this.issueService.getAllIssues(id);
//    }
//
//    @PutMapping("/api/project/{projectId}/issue/{issueId}")
//    public Mono<Void> updateIssue(@PathVariable("projectId") ObjectId projectId,
//                                  @PathVariable("issueid") ObjectId issueId,
//                                  @Valid @RequestBody IssueDTO issueDTO) {
//        return this.issueService.updateIssue(projectId, issueId, issueDTO);
//    }
//
//    @DeleteMapping("/api/project/{projectId}/issue/{issueId}")
//    public Mono<Void> deleteIssue(@PathVariable("projectId") ObjectId projectId,
//                                  @PathVariable("issueId") ObjectId issueId) {
//        return this.issueService.deleteIssue(projectId, issueId);
//    }

    @ExceptionHandler
    public ResponseEntity handleNotFoundException(NotFoundException e) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler
    public ResponseEntity handleBadRequestException(BadRequestException e) {
        return ResponseEntity.badRequest().build();
    }

}
