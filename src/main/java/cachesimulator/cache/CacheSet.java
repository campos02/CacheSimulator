package cachesimulator.cache;

import cachesimulator.cache.enums.HitOrMiss;
import cachesimulator.cache.enums.ReplacementPolicy;
import java.util.Random;
import java.util.Queue;
import java.util.LinkedList;
import java.util.LinkedHashMap;
import java.util.Map;

public class CacheSet {
    private final Way[] ways;
    private final ReplacementPolicy replacementPolicy;
    private final Random random = new Random();

    Queue<String> fila = new LinkedList<>();
    private final LinkedHashMap<Integer, Integer> lruMap = new LinkedHashMap<>(16, 0.75f, true); // LRU tracking

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
                    lruMap.put(tag, i);
                    return HitOrMiss.HIT;
                }

                case HitOrMiss.MISS -> {
                    if (i == ways.length - 1) {
                        if (replacementPolicy == ReplacementPolicy.LRU) {
                            if (isFull()) {
                                int lruTag = lruMap.keySet().iterator().next(); 
                                lruMap.remove(lruTag); 
                                for (Way way : ways) {
                                    if (way.getTag() == lruTag) {
                                        way.clear(); 
                                        break;
                                    }
                                }
                            }

                            lruMap.put(tag, i);
                            for (Way way : ways) {
                                if (!way.isFull()) {
                                    way.storeAddress(tag);
                                    break;
                                }
                            }
                        } else if (replacementPolicy == ReplacementPolicy.FIFO) {

                            if (isFull()) {
                                int evictedTag = Integer.parseInt(fila.poll());
                                for (Way way : ways) {
                                    if (way.getTag() == evictedTag) {
                                        way.clear();
                                        break;
                                    }
                                }
                            }
                            fila.add(String.valueOf(tag));
                            for (Way way : ways) {
                                if (!way.isFull()) {
                                    way.storeAddress(tag);
                                    break;
                                }
                            }
                        } else {

                            ways[random.nextInt(ways.length)].storeAddress(tag);
                        }
                    }
                }

                case HitOrMiss.COMPULSORY_MISS -> {
                    ways[i].storeAddress(tag);
                    lruMap.put(tag, i); 
                    return HitOrMiss.COMPULSORY_MISS;
                }
            }
        }

        return HitOrMiss.MISS;
    }
}

