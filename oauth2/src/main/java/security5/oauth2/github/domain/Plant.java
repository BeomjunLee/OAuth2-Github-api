package security5.oauth2.github.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Plant {

    @Id @GeneratedValue
    private Long id;

    private String frontName;
    private String backName;
    private String repoName;
    private String repoDesc;
    private LocalDateTime startDate;
    private LocalDateTime deadLine;
    private int commitCycle;

    @Builder
    public Plant(String frontName, String backName, String repoName, String repoDesc, LocalDateTime startDate, LocalDateTime deadLine, int commitCycle) {
        this.frontName = frontName;
        this.backName = backName;
        this.repoName = repoName;
        this.repoDesc = repoDesc;
        this.startDate = startDate;
        this.deadLine = deadLine;
        this.commitCycle = commitCycle;
    }
}
