package espm.account;

public record AccountOut (
    String id,
    String name,
    String email,
    String password,
    String createdAt
) {
}
