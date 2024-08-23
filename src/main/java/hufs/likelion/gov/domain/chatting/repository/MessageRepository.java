package hufs.likelion.gov.domain.chatting.repository;

import hufs.likelion.gov.domain.chatting.entity.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MessageRepository extends MongoRepository<Message, String> {
}
