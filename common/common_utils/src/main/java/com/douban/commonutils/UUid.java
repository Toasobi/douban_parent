package com.douban.commonutils;

import java.util.UUID;

public class UUid {
    public static String getUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("-","");
    }
}
