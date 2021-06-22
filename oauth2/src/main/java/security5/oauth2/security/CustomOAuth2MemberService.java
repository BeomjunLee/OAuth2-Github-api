package security5.oauth2.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import security5.oauth2.github.domain.Member;
import security5.oauth2.github.repository.MemberRepository;
import security5.oauth2.github.domain.MemberRole;
import javax.servlet.http.HttpSession;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomOAuth2MemberService implements OAuth2UserService{

    private final MemberRepository memberRepository;
    private final HttpSession session;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        DefaultOAuth2UserService service = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = service.loadUser(userRequest);

        Member member = saveOrUpdate(oAuth2User);

        session.setAttribute("oAuthToken", userRequest.getAccessToken().getTokenValue());

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(member.getRole())),
                oAuth2User.getAttributes(), "login");
    }

    public Member saveOrUpdate(OAuth2User oAuth2User) {
        Member oAuthMember = Member.builder()
                .username(oAuth2User.getAttribute("login"))
                .name(oAuth2User.getAttribute("name"))
                .picture(oAuth2User.getAttribute("avatar_url"))
                .role(MemberRole.USER)
                .build();

        Member member = memberRepository.findByUsername(oAuthMember.getUsername())
                .map(entity -> entity.update(oAuthMember))
                .orElse(oAuthMember);
        return memberRepository.save(member);
    }
}
