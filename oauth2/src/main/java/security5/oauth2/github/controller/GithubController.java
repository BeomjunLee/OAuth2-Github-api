package security5.oauth2.github.controller;

import lombok.RequiredArgsConstructor;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import security5.oauth2.github.dto.response.ResponseMyPage;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class GithubController {

    @GetMapping("/users/myPage")
    public ResponseMyPage myPage(@AuthenticationPrincipal OAuth2User oAuth2User, HttpSession session) throws IOException {
        GitHub gitHub = new GitHubBuilder()
                .withOAuthToken(session.getAttribute("oAuthToken").toString(), oAuth2User.getName())
                .build();
        
        return null;
    }
}
