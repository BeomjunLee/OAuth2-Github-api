package security5.oauth2.response;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class MemberDto {
    private String username;
    private String name;
    private String avatarUrl;
    private int followersCount;
    private String location;
    private Date createdAt;
    private Date updatedAt;
}
