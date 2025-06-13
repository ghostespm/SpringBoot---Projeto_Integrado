package espm.account;

import java.util.Date;
import jakarta.persistence.*;

@Entity
@Table(name = "account")
public class AccountModel {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "hash_password", nullable = false)
    private String hashPassword;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    public AccountModel() {
        // Construtor padrão
    }

    public AccountModel(Account a) {
        if (a != null) {
            this.id = a.getId();
            this.name = a.getName();
            this.email = a.getEmail();
            this.hashPassword = a.getHashPassword();
            this.createdAt = a.getCreatedAt();
        }
    }

    @PrePersist
    public void prePersist() {
        if (this.id == null) {
            this.id = java.util.UUID.randomUUID().toString();
        }
        if (this.createdAt == null) {
            this.createdAt = new Date();
        }
    }

    public Account to() {
        Account a = new Account();
        a.setId(this.id);
        a.setName(this.name);
        a.setEmail(this.email);
        a.setHashPassword(this.hashPassword);
        a.setCreatedAt(this.createdAt);
        return a;
    }

    // Getters e setters (opcional, mas recomendado)
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getHashPassword() { return hashPassword; }
    public void setHashPassword(String hashPassword) { this.hashPassword = hashPassword; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}
