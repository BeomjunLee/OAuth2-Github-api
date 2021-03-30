package security5.oauth2.member;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String username;
    private String name;
    private String picture;
    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @Builder
    public Member(String username, String name, String picture, MemberRole role) {
        this.username = username;
        this.name = name;
        this.picture = picture;
        this.role = role;
    }

    public Member update(Member member) {
        this.username = member.getUsername();
        this.name = member.getName();
        this.picture = member.getPicture();
        return this;
    }

    public String getRole() {
        return this.role.getRole();
    }
}
