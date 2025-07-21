package cachesimulator.cache;

import cachesimulator.StatRepository;
import cachesimulator.cache.enums.HitOrMiss;
import cachesimulator.cache.enums.ReplacementPolicy;
import java.util.Random;

public class CacheSet {
    private final Way[] ways;
    private final ReplacementPolicy replacementPolicy;
    private final Random random = new Random();
    private final StatRepository statRepository;

    public CacheSet(int associativity, ReplacementPolicy policy, StatRepository repository) {
        ways = new Way[associativity];
        replacementPolicy = policy;
        statRepository = repository;

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
                    statRepository.increaseHits();
                    return HitOrMiss.HIT;
                }

                case HitOrMiss.MISS -> {
                    if (i == ways.length - 1) {
                        // TODO: Implement LRU and FIFO
                        ways[random.nextInt(ways.length)].storeAddress(tag);
                    }
                }

                case HitOrMiss.COMPULSORY_MISS -> {
                    statRepository.increaseCompulsoryMisses();
                    ways[i].storeAddress(tag);
                    return HitOrMiss.COMPULSORY_MISS;
                }
            }
        }

        return HitOrMiss.MISS;
    }
}
