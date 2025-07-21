package cachesimulator.cache;

import cachesimulator.StatRepository;
import cachesimulator.cache.enums.ReplacementPolicy;
import java.util.Random;

public class Cache {
    private final Way[] ways;
    private final StatRepository statRepository;
    private final Random random = new Random();
    private final ReplacementPolicy replacementPolicy;

    public Cache(int numberOfSets, int associativity, ReplacementPolicy policy, StatRepository repository) {
        statRepository = repository;
        ways = new Way[associativity];
        replacementPolicy = policy;

        for (int i = 0; i < ways.length; i++) {
            ways[i] = new Way(numberOfSets);
        }
    }

    public void checkAndStoreAddress(int index, int tag) {
        statRepository.increaseAccesses();

        for (int i = 0; i < ways.length; i++) {
            switch (ways[i].checkAddress(index, tag)) {
                case HIT -> {
                    statRepository.increaseHits();
                    return;
                }

                case COMPULSORY_MISS -> {
                    statRepository.increaseCompulsoryMisses();
                    ways[i].storeAddress(index, tag);
                    return;
                }

                case CONFLICT_MISS -> {
                    if (ways.length < 2) {
                        statRepository.increaseConflictMisses();
                        ways[i].storeAddress(index, tag);
                    } else if (i == ways.length - 1) {
                        if (ways[i].getNumberOfSets() != ways.length)
                            statRepository.increaseConflictMisses();
                        else
                            statRepository.increaseCapacityMisses();

                        // TODO: Implement FIFO and LRU
                        ways[random.nextInt(ways.length)].storeAddress(index, tag);
                    }
                }
            }
        }
    }
}
