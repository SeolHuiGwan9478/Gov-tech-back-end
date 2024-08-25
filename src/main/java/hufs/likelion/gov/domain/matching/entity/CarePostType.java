package hufs.likelion.gov.domain.matching.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum CarePostType {
    ONE_TO_ONE("1:1"),
    ONE_TO_MANY("1:N");
    private final String type;

    CarePostType(String type){
        this.type = type;
    }
    @JsonCreator
    public CarePostType deserializerCarePostType(String value){
        for(CarePostType carePostType : CarePostType.values()){
            if(carePostType.getType().equals(value)){
                return carePostType;
            }
        }
        return null;
    }
    @JsonValue
    public String serializerCarePostType(){
        return type;
    }
}