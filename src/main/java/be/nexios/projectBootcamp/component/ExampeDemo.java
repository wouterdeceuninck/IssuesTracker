package be.nexios.projectBootcamp.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ExampeDemo {
    private final ExampleMapper mapper;
    private final Logger log = LoggerFactory.getLogger(ExampeDemo.class.getName());
    public ExampeDemo( ExampleMapper mapper ) {
        this.mapper = mapper;
    }
    @PostConstruct
    public void postConstruct(){
        log.info("ExampleDemo::postConstruct");
        log.info(mapper.toString());
    }
}
