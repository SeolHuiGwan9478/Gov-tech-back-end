package hufs.likelion.gov.domain.matching.dto;

import hufs.likelion.gov.domain.matching.entity.CareBaby;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetCareBabyInCarePostResponse {
    private Long id;
    private int age;
    private String feature;
    private String history;
    private String etc;

    public static GetCareBabyInCarePostResponse toGetCareBabyInCarePostResponse(CareBaby careBaby){
        return GetCareBabyInCarePostResponse.builder()
                .id(careBaby.getId())
                .age(careBaby.getAge())
                .feature(careBaby.getFeature())
                .history(careBaby.getHistory())
                .etc(careBaby.getEtc())
                .build();
    }
}