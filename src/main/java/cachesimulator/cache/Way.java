package cachesimulator.cache;

import cachesimulator.cache.enums.HitOrMissType;

public class Way {
    private final CacheSet[] cacheSets;

    public Way(int numberOfSets) {
        cacheSets = new CacheSet[numberOfSets];

        for (int i = 0; i < cacheSets.length; i++) {
            cacheSets[i] = new CacheSet();
        }
    }

    public HitOrMissType checkAddress(int index, int tag) {
        if (cacheSets[index].getValidity() == 0)
            return HitOrMissType.COMPULSORY_MISS;

        if (cacheSets[index].getTag() == tag)
            return HitOrMissType.HIT;
        else
            return HitOrMissType.CONFLICT_MISS;
    }

    public void storeAddress(int index, int tag) {
        cacheSets[index].setValidityToOne();
        cacheSets[index].setTag(tag);
    }
}
