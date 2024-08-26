package hufs.likelion.gov.domain.matching.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum CarePostStatus {
    WAITING("WAITING"),
    MATCHED("MATCHED");
    private final String status;

    CarePostStatus(String status) {
        this.status = status;
    }

    @JsonCreator
    public CarePostStatus deserializerCarePostStatus(String status){
        for(CarePostStatus carePostStatus : CarePostStatus.values()){
            if(carePostStatus.getStatus().equals(status)){
                return carePostStatus;
            }
        }
        return null;
    }
    @JsonValue
    public String serializerCarePostStatus(){
        return status;
    }
}
