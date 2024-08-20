package hufs.likelion.gov.domain.matching.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostCareBabyInCarePostRequest {
    private int age;
    private String feature;
    private String history;
    private String etc;
}
