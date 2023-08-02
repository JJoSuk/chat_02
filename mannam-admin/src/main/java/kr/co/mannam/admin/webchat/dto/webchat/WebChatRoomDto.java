package kr.co.mannam.admin.webchat.dto.webchat;

import com.sun.istack.NotNull;
import kr.co.mannam.admin.webchat.type.ChatType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WebChatRoomDto {

    // 채팅방 생성
    @NotNull
    private String roomId; // 채팅방 아이디
    private String roomName; // 채팅방 이름
    private String roomPass; // 채팅방 생성 및 삭제시 필요한 pass
    private int userCount; // 채팅방 인원수
    private int maxUserCnt; // 채팅방 최대 인원 제한
    private boolean chatLock; // 채팅방 잠금 여부

    private ChatType chatType;

    // ChatRoomDto 클래스에 서비스를 이렇게 나눠도 괜찮나?
    // 일단 수정대기일단 수정대기일단 수정대기일단 수정대기일단 수정대기일단 수정대기일단 수정대기일단 수정대기일단 수정대기일단 수정대기일단 수정대기
    // 일단 수정대기일단 수정대기일단 수정대기일단 수정대기일단 수정대기일단 수정대기일단 수정대기일단 수정대기일단 수정대기일단 수정대기일단 수정대기
    public ConcurrentMap<String, ?> userList = new ConcurrentHashMap<>();
}
