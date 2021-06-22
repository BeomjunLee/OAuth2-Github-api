package security5.oauth2.github.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommitDto {
    private LocalDateTime date;
    private String message;

    @Builder
    public CommitDto(LocalDateTime date, String message) {
        this.date = date;
        this.message = message;
    }
}
