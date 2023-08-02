package kr.co.mannam.admin.webchat.dto.webchat;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class WebChatRoomMap {

    private static final WebChatRoomMap INSTANCE = new WebChatRoomMap();
    private ConcurrentMap<String, WebChatRoomDto> chatRooms = new ConcurrentHashMap<>();

    private WebChatRoomMap() {}

    public static WebChatRoomMap getInstance() {
        return INSTANCE;
    }

    public ConcurrentMap<String, WebChatRoomDto> getChatRooms() {
        return chatRooms;
    }
}
