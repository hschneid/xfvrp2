package xf.xfvrp.model;

public record Job(
        int idx,
        String name,
        Location location,
        float[] amount,
        float[][] timeWindows,
        LoadType loadType,
        int relatedJobIdx,
        float serviceTime,
        int expectedSkills,
        float benefit
) implements Event {

}
