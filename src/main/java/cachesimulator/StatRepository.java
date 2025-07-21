package cachesimulator;

public class StatRepository {
    private int accesses;
    private int compulsoryMisses;
    private int conflictMisses;
    private int capacityMisses;
    private int hits;

    public int getAccesses() {
        return accesses;
    }

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

    public double hitRate() {
        return (double) hits / accesses;
    }

    public double missRate() {
        return (double) (compulsoryMisses + conflictMisses + capacityMisses) / accesses;
    }

    public double compulsoryMissRate() {
        return (double) compulsoryMisses / (compulsoryMisses + conflictMisses + capacityMisses);
    }

    public double conflictMissRate() {
        return (double) conflictMisses / (compulsoryMisses + conflictMisses + capacityMisses);
    }

    public double capacityMissRate() {
        return (double) capacityMisses / (compulsoryMisses + conflictMisses + capacityMisses);
    }

    public void increaseAccesses() {
        accesses++;
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
