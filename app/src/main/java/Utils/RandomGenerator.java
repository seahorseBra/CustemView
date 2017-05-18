package utils;

import java.util.Random;


public class RandomGenerator {
    private static final Random RANDOM = new Random();


    public static float getRandom(float lower, float upper) {

        float min = Math.min(lower, upper);
        float max = Math.max(lower, upper);
        return getRandom(max - min) + min;
    }

    public static float getRandom(float upper) {
        return RANDOM.nextFloat() * upper;
    }

    public static int getRandom(int upper) {
        return RANDOM.nextInt(upper);
    }
}

