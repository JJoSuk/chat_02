package kr.co.mannam.admin.webchat.service;

import kr.co.mannam.admin.webchat.dto.webchat.WebChatRoomDto;
import kr.co.mannam.admin.webchat.dto.webchat.WebChatRoomMap;
import kr.co.mannam.admin.webchat.type.ChatType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Getter @Setter
@RequiredArgsConstructor
@Slf4j
public class WebChatService {

    // MSG 채팅
    private final MsgChatService msgChatService;

    // RTC 채팅
    // private final RtcChatService rtcChatService;

    // 채팅방 삭제시 내부 연동된 파일리스트 같이 삭제
    // private final FileService fileService;

    /**
     * 전체 채팅방 조회
     */
    public List<WebChatRoomDto> findAllRooms() {

        // 채팅방 생성 순서를 최근순으로 반환
        List<WebChatRoomDto> chatRooms = new ArrayList<>(WebChatRoomMap.getInstance().getChatRooms().values());
        Collections.reverse(chatRooms);
        return chatRooms;
    }

    /**
     * 채팅방 고유 번호(ID) 기준으로 채팅방 찾기
     * @param roomId 채팅방 번호
     * @return
     */
    public WebChatRoomDto findRoomById(String roomId) {
        return WebChatRoomMap.getInstance().getChatRooms().get(roomId);
    }

    /**
     * 채팅방 제목으로 채팅방 찾기
     * @param roomName 채팅방 번호
     * @param roomPass 채팅방 비밀번호
     * @param chatLock 채팅방 잠금 여부
     * @param maxUserCnt 채팅방 최대 인원
     * @param chatType 채팅 타입
     * @return
     */
    public WebChatRoomDto createChatRoom(String roomName, String roomPass, boolean chatLock, int maxUserCnt, String chatType) {

        WebChatRoomDto room;

        // 채팅방 타입에 따라서 사용되는 Service 구분
        if (chatType.equals("msgChat")) {
            room = msgChatService.createChatRoom(roomName, roomPass, chatLock, maxUserCnt);
        } else {
            // RTC 채팅 서비스를 사용하려면 해당 서비스를 주입받아서 사용
            // room = rtcChatService.createChatRoom(roomName, roomPass, chatLock, maxUserCnt);
            throw new UnsupportedOperationException("RTC 채팅 서비스는 아직 구현되지 않았습니다.");
        }

        return room;
    }

    /**
     * 채팅방 비밀번호 조회
     * @param roomId 채팅방 번호
     * @param roomPass 채팅방 비밀번호
     * @return
     */
    public boolean confirmPwd(String roomId, String roomPass) {
        // 채팅방 비밀번호 확인 로직 구현
        WebChatRoomDto room = WebChatRoomMap.getInstance().getChatRooms().get(roomId);
        return roomPass.equals(room.getRoomPass());
    }

    /**
     * 채팅방 인원 추가 +1
     * @param roomId 채팅방 번호
     */
    public void plusUserCnt(String roomId) {
        log.info("cnt {}", WebChatRoomMap.getInstance().getChatRooms().get(roomId).getUserCount());
        WebChatRoomDto room = WebChatRoomMap.getInstance().getChatRooms().get(roomId);
        room.setUserCount(room.getUserCount() + 1);
    }

    /**
     * 채팅방 인원 빼기 -1
     * @param roomId 채팅방 번호
     */
    public void minusUserCnt(String roomId) {
        WebChatRoomDto room = WebChatRoomMap.getInstance().getChatRooms().get(roomId);
        room.setUserCount(room.getUserCount() - 1);
    }

    /**
     * 채팅방 최대 인원에 따라 채팅방 입장 여부
     * @param roomId 채팅방 번호
     * @return
     */
    public boolean chkRoomUserCnt(String roomId) {
        WebChatRoomDto room = WebChatRoomMap.getInstance().getChatRooms().get(roomId);
        return room.getUserCount() + 1 <= room.getMaxUserCnt();
    }

    /**
     * 채팅방 삭제
     * @param roomId 채팅방 번호
     */
    public void delChatRoom(String roomId) {
        try {
            WebChatRoomDto room = WebChatRoomMap.getInstance().getChatRooms().get(roomId);

            if (room == null) {
                log.info("삭제할 채팅방이 없습니다. roomId: {}", roomId);
                return;
            }

            if (room.getChatType().equals(ChatType.MSG)) {
                // 파일 서비스를 사용하여 파일 삭제 처리
                // fileService.deleteFileDir(roomId);
                log.info("MSG 채팅방 삭제 처리: {}", roomId);
            } else {
                // RTC 채팅 서비스에서 파일 삭제 처리를 하려면 해당 서비스를 주입받아서 사용
                // rtcChatService.deleteFiles(roomId);
                log.info("RTC 채팅방 삭제 처리: {}", roomId);
            }

            log.info("삭제 완료 roomId: {}", roomId);
        } catch (Exception e) {
            log.error("삭제 중 오류 발생: {}", e.getMessage());
        }
    }
}