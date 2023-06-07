package xf.xfvrp.model;

public record Job(
        int idx,
        String name,
        Location location,
        float[] demand,
        float[][] timeWindows,
        LoadType loadType,
        Job relatedJob,
        float serviceTime,
        int expectedSkills,
        float benefit
) implements Event {

}
