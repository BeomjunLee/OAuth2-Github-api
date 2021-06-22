package security5.oauth2.github.dto.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import security5.oauth2.github.domain.Repo;
import security5.oauth2.github.dto.MemberDto;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseMyPage {
    private MemberDto member;
    private Repo repo;
    private List<Repo> repos;
}
