package security5.oauth2.member;

import lombok.RequiredArgsConstructor;
import org.kohsuke.github.*;
import org.springframework.core.ResolvableType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import security5.oauth2.rest.Response;
import security5.oauth2.rest.LoginResource;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Principal;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private static String authorizationRequestBaseUri = "oauth2/authorization";

    private final ClientRegistrationRepository clientRegistrationRepository;
    Map<String, String> oauth2AuthenticationUrls = new HashMap<>();

    public String getLoginPage() {
        Iterable<ClientRegistration> clientRegistrations = null;
        ResolvableType type = ResolvableType.forInstance(clientRegistrationRepository).as(Iterable.class);
        if (type != ResolvableType.NONE && ClientRegistration.class.isAssignableFrom(type.resolveGenerics()[0])) {
            clientRegistrations = (Iterable<ClientRegistration>) clientRegistrationRepository;
        }

        clientRegistrations.forEach(registration -> oauth2AuthenticationUrls.put(registration.getClientName(),
                authorizationRequestBaseUri + "/" + registration.getRegistrationId()));
        return oauth2AuthenticationUrls.get("GitHub");
    }

    @GetMapping("/login")
    public ResponseEntity login() {
        Response res = Response.builder()
                .status(200)
                .success("success")
                .message("로그인 페이지")
                .build();

        LoginResource resource = new LoginResource(res);
        resource.add(linkTo(LoginController.class).slash(getLoginPage()).withRel("login_url"));

        return ResponseEntity.ok(resource);
    }

    @GetMapping("/")
    public ResponseEntity main(@AuthenticationPrincipal OAuth2User oAuth2User, HttpSession session) throws IOException {

        GitHub gitHub = new GitHubBuilder()
                .withOAuthToken(session.getAttribute("oAuthToken").toString(), oAuth2User.getName()).build();

        GHUser user = gitHub.getUser(oAuth2User.getName());
        System.out.println("user = " + user);
        Map<String, GHRepository> repositories = user.getRepositories();
        System.out.println("repositories = " + repositories);

        MemberDto dto = MemberDto.builder()
                .username(user.getLogin())
                .name(user.getName())
                .avatarUrl(user.getAvatarUrl())
                .followersCount(user.getFollowersCount())
                .location(user.getLocation())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();

        return ResponseEntity.ok(dto);
    }
}
