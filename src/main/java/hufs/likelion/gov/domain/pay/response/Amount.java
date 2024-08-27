package hufs.likelion.gov.domain.pay.response;

import lombok.Getter;

@Getter
public class Amount {
    private int total;
    private int tax_free;
    private int tax;
}
