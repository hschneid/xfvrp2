package xf.xfvrp.model;

public record Model(
        Vehicle[] vehicles,
        Job[] jobs,
        Depot[] depots,
        Metric metric,
        Skill[] skills
) {
}
