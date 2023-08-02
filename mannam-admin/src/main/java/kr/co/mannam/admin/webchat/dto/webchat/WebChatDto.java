package kr.co.mannam.admin.webchat.dto.webchat;

import kr.co.mannam.admin.webchat.type.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WebChatDto {

    private String roomId; // 방 번호
    private String sender; // 채팅을 보낸 사람
    private String message; // 메시지
    private String time; // 채팅 발송 시간
//    private String s3DataUrl; // 파일 업로드 url
//    private String fileName; // 파일 이름
//    private String fileDir; // s3 파일 경로

    private MessageType type; // 메시지 타입
}
