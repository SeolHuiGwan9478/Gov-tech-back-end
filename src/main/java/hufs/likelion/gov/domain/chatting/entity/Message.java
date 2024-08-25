package hufs.likelion.gov.domain.chatting.entity;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Document(collection = "messages")
public class Message {
    @Id
    @Field(value = "_id", targetType = FieldType.OBJECT_ID)
    private String id;
    private String roomId;
    private Long senderId;
    private String senderName;
    private String message;
    private MessageStatus status;
    @CreatedDate
    private LocalDateTime date;
}
