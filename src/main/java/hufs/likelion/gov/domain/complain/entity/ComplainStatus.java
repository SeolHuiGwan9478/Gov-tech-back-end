package hufs.likelion.gov.domain.complain.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import hufs.likelion.gov.domain.matching.entity.CarePostStatus;
import lombok.Getter;

@Getter
public enum ComplainStatus {
    WAITING("답변 대기중"),
    DONE("답변 완료");
    private final String status;

    ComplainStatus(String status) {
        this.status = status;
    }

    @JsonCreator
    public ComplainStatus deserializerComplainStatus(String status){
        for(ComplainStatus complainStatus : ComplainStatus.values()){
            if(complainStatus.getStatus().equals(status)){
                return complainStatus;
            }
        }
        return null;
    }
    @JsonValue
    public String serializerComplainStatus(){
        return status;
    }
}
