package kr.co.mannam.global.social;

import kr.co.mannam.admin.webchat.dto.webchat.WebChatUserDto;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class PrincipalDetails implements UserDetails, OAuth2User {

    // ChatUserDto
    private WebChatUserDto user;

    // 소셜 로그인 유저의 정보 확인을 위한 attributes
    private Map<String, Object> attributes;

    // 소셜 유저 타입 정보 -> 네이버, 카카오 일반 등
    private String provider;

    // 일반 유저
    public PrincipalDetails(WebChatUserDto user, String provider) {
        this.user = user;
        this.provider = provider;
    }

    // OAuth2User 유저 -> 소셜 로그인 유저
    public PrincipalDetails(WebChatUserDto user, Map<String, Object> attributes, String provider) {
        this.user = user;
        this.attributes = attributes;
        this.provider = provider;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return user.getNickName();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("user");
        return Collections.singleton(authority);
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return user.getNickName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}