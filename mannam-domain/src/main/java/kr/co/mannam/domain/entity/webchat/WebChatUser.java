package kr.co.mannam.domain.entity.webchat;

import kr.co.mannam.domain.type.webchat.ProviderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import javax.persistence.*;

@Entity
@Table
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WebChatUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Comment("사용자 닉네임")
    @Column(name = "NICKNAME", length = 40)
    private String nickName;

    @Comment("사용자 이메일")
    @Column(name = "E-MAIL", length = 100)
    private String email;

    @Comment("사용자 비밀번호")
    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private ProviderType provider; // 이메일 관련인데 사용할련지 일단 대기

//    // Setter를 제공하지 않음
//    public void setEncryptedPassword(String password) {
//        this.passwd = encryptPassword(password);
//    }
//
//    private String encryptPassword(String password) {
//        // 패스워드 암호화 로직 적용
//        // 예: BCrypt 라이브러리를 사용하여 암호화
//        // return BCrypt.hashpw(password, BCrypt.gensalt());
//    }
}
