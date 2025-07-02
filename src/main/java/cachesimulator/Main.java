package cachesimulator;

import cachesimulator.cache.Cache;
import cachesimulator.cache.enums.SubstitutionPolicy;

public class Main {
    public static void main(String[] args) {
        if (args.length < 6) {
            System.out.println("Not enough arguments");
            System.out.println("Usage: cache_simulator <nsets> <bsize> <assoc> <sub> <out_flag> cache_file");
            return;
        }

        SubstitutionPolicy policy = switch (args[3]) {
            case "l" -> SubstitutionPolicy.LRU;
            case "r" -> SubstitutionPolicy.RANDOM;
            case "f" -> SubstitutionPolicy.FIFO;
            default -> throw new IllegalArgumentException("Unexpected policy: " + args[3]);
        };

        Cache cache = new Cache(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]), policy);
    }
}