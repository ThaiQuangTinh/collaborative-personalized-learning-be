package org.qnu.cpl.collaborativepersonalizedlearningbe.util;

import java.util.UUID;

public class UUIDUtil {

    public static String generate() {
        return UUID.randomUUID().toString();
    }

}
