package security5.oauth2.github.service;

import lombok.RequiredArgsConstructor;
import org.kohsuke.github.*;
import org.springframework.stereotype.Service;
import security5.oauth2.github.domain.Plant;
import security5.oauth2.github.dto.CommitDto;
import security5.oauth2.github.dto.CommitLanguage;
import security5.oauth2.github.dto.response.ResponsePlant;
import security5.oauth2.github.repository.PlantRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GithubService {

    private final PlantRepository plantRepository;

    public ResponsePlant getPlant(String token, String name, Long id) throws IOException {
        GitHub gitHub = new GitHubBuilder()
                .withOAuthToken(token, name)
                .build();

        Plant plant = plantRepository.findById(id).orElse(Plant.builder().build());

        String repoName = "BeomjunLee/TableOrder";

        GHRepository repository = gitHub.getRepository(repoName);
        List<CommitDto> commitDtos = transferCommitDto(name, repository);

        List<CommitLanguage> comLang = getCommitLanguages(repository.listLanguages());

        int decimalDay = Period.between(plant.getStartDate().toLocalDate(), plant.getDeadLine().toLocalDate()).getDays();
        int commitCount = decimalDay / plant.getCommitCycle();
        return ResponsePlant.builder()
                .id(plant.getId())
                .frontName(plant.getFrontName())
                .backName(plant.getBackName())
                .repoName(plant.getRepoName())
                .repoDesc(plant.getRepoDesc())
                .startDate(plant.getStartDate())
                .deadLine(plant.getDeadLine())
                .decimalDay(decimalDay)
                .commitCycle(plant.getCommitCycle())
                .commitCount(commitCount)
                .totalCommit(commitDtos.size())
                .commit(commitDtos)
                .comLang(comLang)
                .build();
    }

    private List<CommitLanguage> getCommitLanguages(Map<String, Long> languages) {
        List<CommitLanguage> comLang = new ArrayList<>();

        for (String key : languages.keySet()) {
            CommitLanguage commitLanguage = CommitLanguage.builder()
                    .language(key)
                    .percentage(0)
                    .usedLine(0)
                    .build();
            comLang.add(commitLanguage);
        }
        return comLang;
    }

    private List<CommitDto> transferCommitDto(String name, GHRepository repository) throws IOException {
        return repository.listCommits().toList().stream().filter(commit -> {
            try {
                return commit.getAuthor().getLogin().equals(name);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }).map(commit -> {
            try {
                return CommitDto.builder()
                        .date(LocalDateTime.ofInstant(commit.getCommitShortInfo().getCommitDate().toInstant(), ZoneId.systemDefault()))
                        .message(commit.getCommitShortInfo().getMessage())
                        .build();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());
    }
}
