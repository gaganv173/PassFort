package com.PasswordManager;

import java.util.List;

public class PasswordUtils {
    private static final List<String> COMMON_PASSWORDS = List.of(
            "password", "123456", "12345678", "qwerty", "abc123",
            "password1", "admin", "letmein", "welcome", "monkey"
    );

    public static int calculateStrength(String password) {
        int score = 0;

        // Length points
        if (password.length() >= 12) score += 3;
        else if (password.length() >= 8) score += 2;
        else if (password.length() >= 6) score += 1;

        // Character diversity
        boolean hasUpper = !password.equals(password.toLowerCase());
        boolean hasLower = !password.equals(password.toUpperCase());
        boolean hasDigit = password.chars().anyMatch(Character::isDigit);
        boolean hasSpecial = password.chars().anyMatch(c -> "!@#$%^&*()-_+=<>?".indexOf(c) >= 0);

        if (hasUpper) score += 1;
        if (hasLower) score += 1;
        if (hasDigit) score += 1;
        if (hasSpecial) score += 2; // Extra points for special chars

        // Deductions
        if (hasConsecutiveChars(password)) score -= 2;
        if (isCommonPassword(password)) score -= 3;

        return Math.max(score, 0); // Ensure non-negative
    }

    private static boolean hasConsecutiveChars(String password) {
        for (int i = 0; i < password.length() - 2; i++) {
            int c1 = password.charAt(i);
            int c2 = password.charAt(i + 1);
            int c3 = password.charAt(i + 2);
            if (c2 == c1 + 1 && c3 == c2 + 1) return true;
        }
        return false;
    }

    private static boolean isCommonPassword(String password) {
        return COMMON_PASSWORDS.contains(password.toLowerCase());
    }
}