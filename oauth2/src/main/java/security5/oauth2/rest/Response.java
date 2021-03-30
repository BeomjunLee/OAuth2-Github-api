package security5.oauth2.rest;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@Builder
public class Response {
    private int status;
    private String success;
    private String message;
}
