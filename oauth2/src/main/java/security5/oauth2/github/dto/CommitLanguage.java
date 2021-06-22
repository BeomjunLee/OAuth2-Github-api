package security5.oauth2.github.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommitLanguage {
    private String language;
    private int percentage;
    private int usedLine;

    @Builder
    public CommitLanguage(String language, int percentage, int usedLine) {
        this.language = language;
        this.percentage = percentage;
        this.usedLine = usedLine;
    }
}
