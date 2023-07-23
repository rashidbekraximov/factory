package uz.cluster.enums.auth;

public enum SystemRoleName {
    SYSTEM_ROLE_SUPER_ADMIN(1),
    SYSTEM_ROLE_ADMIN(2),
    SYSTEM_ROLE_MODERATOR(3),
    SYSTEM_ROLE_MAIN_AUDITOR(4),
    SYSTEM_ROLE_AUDITOR(5),
    SYSTEM_ROLE_FORM_MEMBER(6),
    SYSTEM_ROLE_MEMBER(7);

    private final int value;

    SystemRoleName(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
