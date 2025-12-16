package org.qnu.cpl.collaborativepersonalizedlearningbe.util;

import java.util.regex.Pattern;

public class ValidationUtil {

    private static final String EMAIL_REGEX =
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    // Phone regex (Vietnam)
    private static final String PHONE_REGEX =
            "^(\\+?84|0)(3|5|7|8|9)\\d{8}$";
    private static final Pattern PHONE_PATTERN = Pattern.compile(PHONE_REGEX);

    private ValidationUtil() {}

    public static boolean isValidEmail(String email) {
        if (email == null || email.isBlank()) return false;
        return EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean isValidPhone(String phone) {
        if (phone == null || phone.isBlank()) return false;
        return PHONE_PATTERN.matcher(phone).matches();
    }
}

