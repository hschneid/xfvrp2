package xf.xfvrp.model;

public record Depot (
        int idx,
        String name,
        Location location,
        float[][] timeWindows,
        float serviceTime,
        int providedSkills
) implements Event {

}
