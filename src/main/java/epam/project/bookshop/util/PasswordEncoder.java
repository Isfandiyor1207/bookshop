package epam.project.bookshop.util;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class PasswordEncoder {
    private static final PasswordEncoder instance = new PasswordEncoder();

    public static PasswordEncoder getInstance() {
        return instance;
    }

    public String encode(String password){
        return BCrypt.withDefaults().hashToString(6, password.toCharArray());
    }

    public boolean checkPassword(String password, String hashPassword){
        return BCrypt.verifyer().verify(password.toCharArray(), hashPassword).verified;
    }
}
