package security5.oauth2.github.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import security5.oauth2.github.dto.CommitDto;
import security5.oauth2.github.dto.CommitLanguage;
import security5.oauth2.github.dto.MemberDto;
import security5.oauth2.github.dto.PlantDto;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponsePlant {

    private Long id;
    private String frontName;
    private String backName;
    private String repoName;
    private String repoDesc;
    private LocalDateTime startDate;
    private LocalDateTime deadLine;
    private int decimalDay;
    private int commitCycle;
    private int commitCount;
    private int totalCommit;
    private List<CommitDto> commit;
    private List<CommitLanguage> comLang;


    @Builder
    public ResponsePlant(Long id, String frontName, String backName, String repoName, String repoDesc, LocalDateTime startDate, LocalDateTime deadLine, int decimalDay, int commitCycle, int commitCount, int totalCommit, List<CommitDto> commit, List<CommitLanguage> comLang) {
        this.id = id;
        this.frontName = frontName;
        this.backName = backName;
        this.repoName = repoName;
        this.repoDesc = repoDesc;
        this.startDate = startDate;
        this.deadLine = deadLine;
        this.decimalDay = decimalDay;
        this.commitCycle = commitCycle;
        this.commitCount = commitCount;
        this.totalCommit = totalCommit;
        this.commit = commit;
        this.comLang = comLang;
    }
}
