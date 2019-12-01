package com.ryit.commons.util;

import java.util.Random;
import java.util.UUID;

/**
 * @author : samphin
 * @since : 2019/8/26 17:48
 * 生成随机码
 */
public class RandomUtil {

    public static String getRandom(Integer length) {
        int i = new Random().nextInt(32 - length);
        return UUID.randomUUID().toString().replace("-", "").substring(i, i + length);
    }

    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
