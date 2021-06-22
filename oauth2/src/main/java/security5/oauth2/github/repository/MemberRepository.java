package security5.oauth2.github.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import security5.oauth2.github.domain.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByUsername(String username);
}
