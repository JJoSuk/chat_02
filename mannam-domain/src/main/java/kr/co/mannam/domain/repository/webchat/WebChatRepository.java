package kr.co.mannam.domain.repository.webchat;

import kr.co.mannam.domain.entity.webchat.WebChatUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WebChatRepository extends JpaRepository<WebChatUser, Long> {

    WebChatUser findByEmail(String email);
}
