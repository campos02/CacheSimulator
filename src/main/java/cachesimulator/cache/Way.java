package cachesimulator.cache;

import cachesimulator.cache.enums.SubstitutionPolicy;

public class Way {
    private final Block[] blocks;

    public Way(int numberOfSets, SubstitutionPolicy policy) {
        blocks = new Block[numberOfSets];
    }
}
