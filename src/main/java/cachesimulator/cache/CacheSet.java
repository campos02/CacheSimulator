package cachesimulator.cache;

import cachesimulator.cache.enums.HitOrMiss;
import cachesimulator.cache.enums.ReplacementPolicy;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class CacheSet {
    private final Way[] ways;
    private final ReplacementPolicy replacementPolicy;
    private final Random random = new Random();
    private final Queue<Integer> queue = new LinkedList<>();
    private final ArrayList<Integer> lruList = new ArrayList<>();

    public CacheSet(int associativity, ReplacementPolicy policy) {
        ways = new Way[associativity];
        replacementPolicy = policy;

        for (int i = 0; i < ways.length; i++) {
            ways[i] = new Way();
        }
    }

    public boolean isFull() {
        boolean full = true;
        for (Way way : ways) {
            if (!way.isFull()) {
                full = false;
                break;
            }
        }

        return full;
    }

    public HitOrMiss checkAndStoreAddress(int tag) {
        for (int i = 0; i < ways.length; i++) {
            switch (ways[i].checkAddress(tag)) {
                case HitOrMiss.HIT -> {
                    lruList.remove((Integer) i);
                    lruList.add(i);
                    return HitOrMiss.HIT;
                }

                case HitOrMiss.MISS -> {
                    if (i == ways.length - 1) {
                        switch (replacementPolicy) {
                            case RANDOM -> ways[random.nextInt(ways.length)].storeAddress(tag);
                            case FIFO -> {
                                int wayIndex = queue.remove();
                                ways[wayIndex].storeAddress(tag);
                                queue.add(wayIndex);
                            }

                            case LRU -> {
                                int wayIndex = lruList.removeFirst();
                                ways[wayIndex].storeAddress(tag);
                                lruList.add(wayIndex);
                            }
                        }
                    }
                }

                case HitOrMiss.COMPULSORY_MISS -> {
                    ways[i].storeAddress(tag);
                    switch (replacementPolicy) {
                        case FIFO -> queue.add(i);
                        case LRU -> lruList.add(i);
                    }

                    return HitOrMiss.COMPULSORY_MISS;
                }
            }
        }

        return HitOrMiss.MISS;
    }
}
