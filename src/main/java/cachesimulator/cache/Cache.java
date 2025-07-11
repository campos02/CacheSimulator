package cachesimulator.cache;

import cachesimulator.StatRepository;
import cachesimulator.cache.enums.ReplacementPolicy;

public class Cache {
    private final Way[] ways;
    private final StatRepository statRepository;

    public Cache(int numberOfSets, int associativity, ReplacementPolicy policy, StatRepository repository) {
        statRepository = repository;
        ways = new Way[associativity];

        for (int i = 0; i < ways.length; i++) {
            ways[i] = new Way(numberOfSets);
        }
    }

    public void checkAndStoreAddress(int index, int tag) {
        for (int i = 0; i < ways.length; i++) {
            switch (ways[i].checkAddress(index, tag)) {
                case HIT -> statRepository.increaseHits();
                case COMPULSORY_MISS -> {
                    statRepository.increaseCompulsoryMisses();
                    ways[i].storeAddress(index, tag);
                }

                case CONFLICT_MISS -> {
                    if (ways.length < 2) {
                        statRepository.increaseConflictMisses();
                        ways[i].storeAddress(index, tag);
                    } else if (i == ways.length - 1) {
                        // TODO: Implement replacement policies
                        statRepository.increaseConflictMisses();
                        ways[i].storeAddress(index, tag);
                    }
                }
            }
        }
    }
}
