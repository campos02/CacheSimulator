package cachesimulator;

public class StatRepository {
    private int compulsoryMisses;
    private int conflictMisses;
    private int capacityMisses;
    private int hits;

    public int getCompulsoryMisses() {
        return compulsoryMisses;
    }

    public int getConflictMisses() {
        return conflictMisses;
    }

    public int getCapacityMisses() {
        return capacityMisses;
    }

    public int getHits() {
        return hits;
    }

    public void increaseCompulsoryMisses() {
        compulsoryMisses++;
    }

    public void increaseConflictMisses() {
        conflictMisses++;
    }

    public void increaseCapacityMisses() {
        capacityMisses++;
    }

    public void increaseHits() {
        hits++;
    }
}
