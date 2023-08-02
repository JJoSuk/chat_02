package kr.co.mannam.admin.webchat.service;

import groovy.util.logging.Slf4j;
import kr.co.mannam.admin.webchat.dto.webchat.WebChatRoomDto;
import kr.co.mannam.admin.webchat.dto.webchat.WebChatRoomMap;
import kr.co.mannam.admin.webchat.type.ChatType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class MsgChatService {

    // 채팅방 삭제시 사진도 같이 삭제하기 위한 fileService
    // private final FileService fileService;

    /**
     * 채팅방 생성
     */
    public WebChatRoomDto createChatRoom(String roomName, String roomPass, boolean chatLock, int maxUserCnt) {

        // roomName 와 roomPass 로 채팅방 빌드 후 리턴
        WebChatRoomDto room = WebChatRoomDto.builder()
                .roomId(UUID.randomUUID().toString()) // 채팅방에 고유 번호 생성
                .roomName(roomName) // 채팅방 방제
                .roomPass(roomPass) // 채팅방 비밀번호
                .chatLock(chatLock) // 채팅방 잠금여부
                .userCount(0) // 채팅방 유저 인원
                .maxUserCnt(maxUserCnt) // 채팅방 최대 인원
                .build(); // 빌드 리턴

        room.setUserList(new ConcurrentHashMap<>());
        room.setChatType(ChatType.MSG);

        // map 에 채팅방 데이터 저장
        WebChatRoomMap.getInstance().getChatRooms().put(room.getRoomId(), room);

        return room;
    }

    /**
     * 채팅방 유저 리스트에 유저 추가
     */
    public String addUser(Map<String, WebChatRoomDto> chatRoomMap, String roomId, String userName) {
        WebChatRoomDto room = chatRoomMap.get(roomId);
        String userUUID = UUID.randomUUID().toString();

        // hashmap 에서 concurrentHashMap 으로 변경
        ConcurrentHashMap<String, String> userList = (ConcurrentHashMap<String, String>)room.getUserList();
        userList.put(userUUID, userName);

        return userUUID;
    }

    /**
     * 채팅방 유저 이름 중복 확인
     */
    public String generateUniqueUserName(Map<String, WebChatRoomDto> chatRoomMap, String roomId, String username){
        WebChatRoomDto room = chatRoomMap.get(roomId);
        String tmp = username;

        // 만약 userName 이 중복이라면 랜덤한 숫자를 붙임
        // 이때 랜덤한 숫자를 붙였을 때 getUserlist 안에 있는 닉네임이라면 다시 랜덤한 숫자 붙이기!
        while(room.getUserList().containsValue(tmp)){
            int ranNum = (int) (Math.random() * 100) + 1;

            tmp = username+ranNum;
        }

        return tmp;
    }

    /**
     * 채팅방 유저 이름 조회
     */
    public String findUserNameByRoomIdAndUserUUID(Map<String, WebChatRoomDto> chatRoomMap, String roomId, String userUUID) {
        WebChatRoomDto room = chatRoomMap.get(roomId);
        return (String) room.getUserList().get(userUUID);
    }

    /**
     * 채팅방 전체 유저 리스트 조회
     */
    public ArrayList<String> getUserList(Map<String, WebChatRoomDto> chatRoomMap, String roomId){
        ArrayList<String> list = new ArrayList<>();

        WebChatRoomDto room = chatRoomMap.get(roomId);

        // hashmap 을 for 문을 돌린 후
        // value 값만 뽑아내서 list 에 저장 후 return
        room.getUserList().forEach((key, value) -> list.add((String) value));
        return list;
    }

    /**
     * 채팅방 특정 유저 삭제
     */
    public void deleteUser(Map<String, WebChatRoomDto> chatRoomMap, String roomId, String userUUID) {
        WebChatRoomDto room = chatRoomMap.get(roomId);
        room.getUserList().remove(userUUID);
    }
}