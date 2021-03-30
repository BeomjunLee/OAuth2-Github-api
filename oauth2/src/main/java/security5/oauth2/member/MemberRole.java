package security5.oauth2.member;

import lombok.Getter;
@Getter
public enum MemberRole {
    USER("ROLE_USER"), ADMIN("ROLE_ADMIN");

    private String role;

    MemberRole(String role) {
        this.role = role;
    }
}
