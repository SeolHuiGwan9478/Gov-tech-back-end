package hufs.likelion.gov.domain.pay.dto;

import lombok.Getter;

@Getter
public class PayReadyResDto {
    private String tid;
    private String nextRedirectPcUrl;
    private String createdAt;
}
