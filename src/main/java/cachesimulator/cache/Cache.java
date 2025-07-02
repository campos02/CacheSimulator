package cachesimulator.cache;

import cachesimulator.cache.enums.SubstitutionPolicy;

public class Cache {
    private final Way[] ways;

    public Cache(int numberOfSets, int blockSize, int associativity, SubstitutionPolicy policy) {
        ways = new Way[associativity];
    }
}
