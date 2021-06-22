package security5.oauth2.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Response {
    private int status;
    private String success;
    private String message;
}
