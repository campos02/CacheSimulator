package cachesimulator.cache;

import cachesimulator.StatRepository;
import cachesimulator.cache.enums.HitOrMiss;
import cachesimulator.cache.enums.ReplacementPolicy;

public class Cache {
    private final CacheSet[] sets;
    private final StatRepository statRepository;
    private boolean full;

    public Cache(int numberOfSets, int associativity, ReplacementPolicy policy, StatRepository repository) {
        sets = new CacheSet[numberOfSets];
        statRepository = repository;

        for (int i = 0; i < sets.length; i++) {
            sets[i] = new CacheSet(associativity, policy);
        }
    }

    public void checkAndStoreAddress(int index, int tag) {
        statRepository.increaseAccesses();
        HitOrMiss result = sets[index].checkAndStoreAddress(tag);

        switch (result) {
            case HitOrMiss.HIT -> statRepository.increaseHits();
            case HitOrMiss.MISS -> {
                if (!full)
                    statRepository.increaseConflictMisses();
                else
                    statRepository.increaseCapacityMisses();
            }

            case HitOrMiss.COMPULSORY_MISS -> statRepository.increaseCompulsoryMisses();
        }

        if (result != HitOrMiss.HIT && !full) {
            boolean full = true;
            for (CacheSet set : sets) {
                if (!set.isFull()) {
                    full = false;
                    break;
                }
            }

            this.full = full;
        }
    }
}
