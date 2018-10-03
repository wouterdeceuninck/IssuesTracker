package be.nexios.projectBootcamp.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

//Object managed by the spring container
@Component
public class ExampleMapper {

    private final Logger log = LoggerFactory.getLogger(ExampleMapper.class.getName());
    @PostConstruct
    public void postConstruct(){
        log.info("ExampleMappper::postConstruct");
    }
}
