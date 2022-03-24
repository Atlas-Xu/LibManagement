package cn.xchub.web.face.entity;

import lombok.Data;

import java.util.concurrent.ConcurrentHashMap;

public class ReaderRamCache {

    private static ConcurrentHashMap<Long, ReaderInfo> userInfoMap = new ConcurrentHashMap<>();
    public static void addUser(ReaderInfo userInfo) {
        userInfoMap.put(userInfo.getReaderId(), userInfo);
    }

    @Data
    public static class ReaderInfo {
        private Long readerId;
        private byte[] faceFeature;

    }
}
