package security5.oauth2.github.dto.resquest;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RequestEnrollPlant {
    private String repoName;
    private String plantName;
    private String repoDesc;
    private String deadLine;
    private int commitCycle;

    @Builder
    public RequestEnrollPlant(String repoName, String plantName, String repoDesc, String deadLine, int commitCycle) {
        this.repoName = repoName;
        this.plantName = plantName;
        this.repoDesc = repoDesc;
        this.deadLine = deadLine;
        this.commitCycle = commitCycle;
    }
}
