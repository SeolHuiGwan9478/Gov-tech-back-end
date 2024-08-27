package hufs.likelion.gov.domain.review.dto;

import hufs.likelion.gov.domain.authentication.dto.request.GetMemberData;
import hufs.likelion.gov.domain.authentication.entity.Member;
import hufs.likelion.gov.domain.review.entity.MemberReview;
import hufs.likelion.gov.domain.review.entity.MemberReviewKeyword;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetMemberReviewsData {
    private Long id;
    private int score;
    private String content;
    // writer info
    private GetMemberData writer;
    // keywords
    private List<String> keywords;

    public static GetMemberReviewsData toGetMemberReviewsData(MemberReview review){
        Member writer = review.getWriter();
        List<MemberReviewKeyword> memberReviewKeywords = review.getKeywords();
        return GetMemberReviewsData.builder()
                .id(review.getId())
                .score(review.getScore())
                .content(review.getContent())
                .writer(GetMemberData.toGetMemberData(writer))
                .keywords(memberReviewKeywords.stream().map(MemberReviewKeyword::getKeyword).toList())
                .build();
    }
}