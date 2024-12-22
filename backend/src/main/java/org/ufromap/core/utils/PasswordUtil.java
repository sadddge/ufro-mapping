package org.ufromap.core.utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {
    public static String encodePassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
    public static boolean matchesPassword(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }
}
