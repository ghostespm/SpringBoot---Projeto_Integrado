package espm.account;

import java.text.SimpleDateFormat;

public class AccountParser {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    public static Account to(AccountIn in) {
        if (in == null) return null;
        // Converte AccountIn (password em texto puro) para Account (hashPassword)
        return new Account(
            null, // id
            in.getName(),
            in.getEmail(),
            in.getPassword(), // será hasheado no AccountService
            null // createdAt
        );
    }

    public static AccountOut to(Account a) {
        if (a == null) return null;
        return new AccountOut(
            a.getId(),
            a.getName(),
            a.getEmail(),
            a.getHashPassword(),
            a.getCreatedAt() != null ? sdf.format(a.getCreatedAt()) : null
        );
    }
}