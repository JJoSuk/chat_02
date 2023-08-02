package kr.co.mannam.admin.webchat.controller;

import kr.co.mannam.admin.webchat.dto.webchat.WebChatRoomDto;
import kr.co.mannam.admin.webchat.service.WebChatService;
import kr.co.mannam.global.social.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@Slf4j
public class WebChatRoomController {

    // ChatService Bean
    private final WebChatService webChatService;

    /**
     * 채팅방 생성
     * @param name 채팅방 제목
     * @param roomPass 채팅방 비밀번호
     * @param chatLock 채팅방 잠금 여부
     * @param maxUserCnt 채팅방 최대 인원
     * @param chatType 채팅 타입
     * @return
     */
    @PostMapping("/chat/createroom")
    public String createRoom(@RequestParam("roomName") String name,
                             @RequestParam("roomPass") String roomPass,
                             @RequestParam("chatLock") String chatLock,
                             @RequestParam(value = "maxUserCnt", defaultValue = "2") String maxUserCnt,
                             @RequestParam("chatType") String chatType,
                             RedirectAttributes redirect) {
        // 매개변수 : 방 이름, 패스워드, 방 잠금 여부, 방 인원수
        WebChatRoomDto room;
        room = webChatService.createChatRoom(name, roomPass, Boolean.parseBoolean(chatLock), Integer.parseInt(maxUserCnt), chatType);

        log.info("Create Chat Room [{}]", room);

        redirect.addFlashAttribute("roomName", room);
        return "redirect:/";
    }

    @GetMapping("/chat/room")
    public String roomDetail(Model model, String roomId, @AuthenticationPrincipal PrincipalDetails)
}
