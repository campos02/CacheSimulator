package cachesimulator.cache;

import cachesimulator.cache.enums.HitOrMiss;

public class Way {
    private final Block block = new Block();
    
    public int getTag() {
        return block.getTag();
    }

    public void clear() {
        block.setValidityToOne(); // Reset validity to 0
        block.setTag(0); // Reset tag to 0
    }

    public boolean isFull() {
        return block.getValidity() == 1;
    }

    public HitOrMiss checkAddress(int tag) {
        if (block.getValidity() == 0)
            return HitOrMiss.COMPULSORY_MISS;

        if (block.getTag() == tag)
            return HitOrMiss.HIT;
        else
            return HitOrMiss.MISS;
    }

    public void storeAddress(int tag) {
        block.setValidityToOne();
        block.setTag(tag);
    }
}
