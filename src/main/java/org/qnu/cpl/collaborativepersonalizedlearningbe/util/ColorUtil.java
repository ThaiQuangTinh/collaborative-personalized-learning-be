package org.qnu.cpl.collaborativepersonalizedlearningbe.util;

import java.util.regex.Pattern;

public class ColorUtil {

    private static final Pattern HEX_COLOR_PATTERN =
            Pattern.compile("^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{8})$");

    public static boolean isValidHexColor(String color) {
        if (color == null) return false;
        return HEX_COLOR_PATTERN.matcher(color).matches();
    }

    public static String normalizeColor(String color) {
        if (!isValidHexColor(color)) return null;
        return color.toUpperCase();
    }

}
