package be.nexios.projectBootcamp.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
//@ToString(exclude = {}) //exclude from toString
@Builder
@Document(collection="data_examples")
public class DataExample {

    @Id
    ObjectId id;

    @NonNull
    String value;
}
