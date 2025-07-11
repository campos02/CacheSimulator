package cachesimulator;

import cachesimulator.cache.Cache;
import cachesimulator.cache.enums.ReplacementPolicy;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length < 6) {
            System.out.println("Not enough arguments");
            System.out.println("Usage: cache_simulator <nsets> <bsize> <assoc> <sub> <out_flag> cache_file");
            return;
        }

        ReplacementPolicy policy = switch (args[3].toLowerCase()) {
            case "l" -> ReplacementPolicy.LRU;
            case "r" -> ReplacementPolicy.RANDOM;
            case "f" -> ReplacementPolicy.FIFO;
            default -> throw new IllegalArgumentException("Unexpected policy: " + args[3]);
        };

        StatRepository statRepository = new StatRepository();
        Cache cache = new Cache(Integer.parseInt(args[0]), Integer.parseInt(args[2]), policy, statRepository);

        double offset = (Math.log(Integer.parseInt(args[1]))/Math.log(2));
        if (Math.floor(offset) != offset)
            throw new IllegalArgumentException("Block size must be a power of two");

        double indices = (Math.log(Integer.parseInt(args[0]))/Math.log(2));
        if (Math.floor(indices) != indices)
            throw new IllegalArgumentException("Number of sets must be a power of two");

        try (FileInputStream fileInputStream = new FileInputStream(args[5])) {
            DataInputStream dataInputStream = new DataInputStream(fileInputStream);

            try {
                while (true) {
                    int address = dataInputStream.readInt();
                    int tag = address >> (int)(indices + offset);
                    int index = (address >> (int)offset) & (Integer.parseInt(args[0]) - 1);
                    cache.checkAndStoreAddress(index, tag);
                }
            } catch (EOFException _) {}
        }

        System.out.println("Hits: " + statRepository.getHits());
        System.out.println("Compulsory misses: " + statRepository.getCompulsoryMisses());
        System.out.println("Conflict misses: " + statRepository.getConflictMisses());
        System.out.println("Capacity misses: " + statRepository.getCapacityMisses());
    }
}