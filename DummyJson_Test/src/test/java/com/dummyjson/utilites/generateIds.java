package com.dummyjson.utilites;

import java.util.concurrent.ThreadLocalRandom;

public class generateIds {

    public static int getRandomUserId() {
        return ThreadLocalRandom.current().nextInt(1, 208);
    }
    public static int getInvalidRandomUserId() {
        return ThreadLocalRandom.current().nextInt(209, 1000);
    }
}
