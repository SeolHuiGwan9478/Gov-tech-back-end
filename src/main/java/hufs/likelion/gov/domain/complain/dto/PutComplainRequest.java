package hufs.likelion.gov.domain.complain.dto;

import lombok.Data;

@Data
public class PutComplainRequest {
    private String title;
    private String type;
    private String content;
}
