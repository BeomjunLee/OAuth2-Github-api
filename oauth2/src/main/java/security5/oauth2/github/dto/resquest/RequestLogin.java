package security5.oauth2.github.dto.resquest;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RequestLogin {
    private String loginUrl;

    @Builder
    public RequestLogin(String loginUrl) {
        this.loginUrl = loginUrl;
    }
}
