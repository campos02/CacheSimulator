package cachesimulator.cache;

public class CacheSet {
    private byte validity;
    private int tag;

    public byte getValidity() {
        return validity;
    }

    public int getTag() {
        return tag;
    }

    public void setValidityToOne() {
        this.validity = 1;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }
}
