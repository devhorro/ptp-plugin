package com.pathtopvm.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class BossKillParser {

    private static final Pattern BOSS_KILL_PATTERN = Pattern.compile("Your (.+) kill count is: (\\d+)\\.");

    public static BossKillInfo parseBossKillMessage(String message) {
        Matcher matcher = BOSS_KILL_PATTERN.matcher(message);
        if (matcher.find()) {
            String bossName = matcher.group(1);
            int killCount = Integer.parseInt(matcher.group(2));
            return new BossKillInfo(bossName, killCount);
        }
        return null;
    }

    public static class BossKillInfo {
        private final String bossName;
        private final int killCount;

        public BossKillInfo(String bossName, int killCount) {
            this.bossName = bossName;
            this.killCount = killCount;
        }

        public String getBossName() {
            return bossName;
        }

        public int getKillCount() {
            return killCount;
        }
    }
}

