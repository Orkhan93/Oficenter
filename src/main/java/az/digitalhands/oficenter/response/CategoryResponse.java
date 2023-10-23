package az.digitalhands.oficenter.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategoryResponse {

    private Long id;
    private String name;
    @JsonIgnore
    private Long collectionId;

}