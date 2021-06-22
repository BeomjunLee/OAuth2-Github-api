package security5.oauth2.github.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import security5.oauth2.github.dto.response.ResponsePlant;
import security5.oauth2.github.service.GithubService;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class GithubController {

    private final GithubService githubService;

    @GetMapping("/plants/{id}")
    public ResponsePlant getPlant(@AuthenticationPrincipal OAuth2User oAuth2User,
                                  @PathVariable Long id,
                                  HttpSession session) throws IOException {
        return githubService.getPlant(session.getAttribute("oAuthToken").toString(), oAuth2User.getName(), id);
    }

//    @PostMapping("/plants")
//    public Response enrollPlant(@RequestBody RequestEnrollPlant requestEnrollPlant) {
//
//    }
}
