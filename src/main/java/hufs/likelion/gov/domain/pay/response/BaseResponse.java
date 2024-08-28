package hufs.likelion.gov.domain.pay.response;

import lombok.Getter;

@Getter
public class BaseResponse<T> {
    private int statusCode;
    private String message;
    private T data;

    public BaseResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

}

