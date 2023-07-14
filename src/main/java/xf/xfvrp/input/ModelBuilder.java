package xf.xfvrp.input;

import xf.xfvrp.model.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ModelBuilder {

    public static Model build(
            List<DepotData> inputDepots,
            List<JobData> inputJobs,
            List<VehicleData> inputVehicles,
            Metric metric
    ) {
        var skills = buildSkills(inputDepots, inputJobs, inputVehicles);
        var depots = buildDepots(inputDepots, skills);
        var jobs = buildJobs(inputJobs, skills);
        var vehicles = buildVehicles(inputVehicles, depots, skills);

        return buildModel(skills, depots, jobs, vehicles, metric);
    }

    private static Model buildModel(Map<String, Skill> skills, Map<String, Depot> depots, Job[] jobs, Vehicle[] vehicles, Metric metric) {
        return new Model(
                vehicles,
                jobs,
                depots.values().toArray(new Depot[0]),
                metric,
                skills.values().toArray(new Skill[0])
        );
    }

    private static Vehicle[] buildVehicles(List<VehicleData> inputVehicles, Map<String, Depot> depots, Map<String, Skill> skills) {
        var idx = new AtomicInteger(0);
        return inputVehicles
                .stream()
                .sorted(Comparator.comparing(VehicleData::getName))
                .map(i -> new Vehicle(
                        idx.getAndIncrement(),
                        i.getName(),
                        depots.get(i.getHomeDepot()),
                        buildDepotsOfVehicle(i.getAvailableDepots(), depots),
                        i.getCapacities(),
                        buildTimeWindowValues(i.getTimeWindows()),
                        i.getFixCost(),
                        i.getVarCost(),
                        buildSkillValue(i.getProvidedSkills(), skills)
                ))
                .toArray(Vehicle[]::new);
    }

    private static Depot[] buildDepotsOfVehicle(List<String> availableDepots, Map<String, Depot> depots) {
        return availableDepots.stream()
                .distinct()
                .map(depots::get)
                .filter(Objects::nonNull)
                .toArray(Depot[]::new);
    }

    private static Job[] buildJobs(List<JobData> inputJobs, Map<String, Skill> skills) {
        var idx = new AtomicInteger(0);
        var jobIdx = inputJobs
                .stream()
                .collect(Collectors.toMap(JobData::getName, j -> idx.getAndIncrement()));

        return inputJobs
                .stream()
                .map(i -> new Job(
                                jobIdx.get(i.getName()),
                                i.getName(),
                                new Location(i.getGeoId(), i.getXlong(), i.getYlat()),
                                i.getAmount(),
                                buildTimeWindowValues(i.getTimeWindows()),
                                i.getLoadType(),
                                jobIdx.getOrDefault(i.getRelatedJob(), -1),
                                i.getServiceTime(),
                                buildSkillValue(i.getExpectedSkills(), skills),
                                0
                        )
                )
                .toArray(Job[]::new);
    }

    private static Map<String, Depot> buildDepots(List<DepotData> inputDepots, Map<String, Skill> skills) {
        var idx = new AtomicInteger(0);
        return inputDepots
                .stream()
                .sorted(Comparator.comparing(DepotData::getName))
                .map(i -> new Depot(
                        idx.getAndIncrement(),
                        i.getName(),
                        new Location(i.getGeoId(), i.getXlong(), i.getYlat()),
                        buildTimeWindowValues(i.getTimeWindows()),
                        i.getServiceTime(),
                        buildSkillValue(i.getProvidedSkills(), skills)
                ))
                .collect(Collectors.toMap(Depot::name, d -> d));
    }

    private static float[][] buildTimeWindowValues(List<float[]> timeWindows) {
        // Bigger time window overrides smaller ones.
        timeWindows.sort((o1, o2) -> (int) ((o1[0] - o2[0]) * 1000f));

        // If no time window is given, insert the default time window
        if(timeWindows.size() == 0)
            timeWindows.add(new float[]{0, Integer.MAX_VALUE});

        return timeWindows.toArray(new float[0][]);
    }

    private static int buildSkillValue(List<String> skillName, Map<String, Skill> skills) {
        return skillName.stream()
                .map(skills::get)
                .filter(Objects::nonNull)
                .mapToInt(Skill::idx)
                .sum();
    }

    private static Map<String, Skill> buildSkills(List<DepotData> inputDepots, List<JobData> inputJobs, List<VehicleData> inputVehicles) {
        var depotSkills = inputDepots.stream().filter(i -> i.getProvidedSkills().size() > 0).flatMap(i -> i.getProvidedSkills().stream());
        var vehicleSkills = inputVehicles.stream().filter(i -> i.getProvidedSkills().size() > 0).flatMap(i -> i.getProvidedSkills().stream());
        var jobSkills = inputJobs.stream().filter(i -> i.getExpectedSkills().size() > 0).flatMap(i -> i.getExpectedSkills().stream());

        var skillNames = Stream.concat(Stream.concat(depotSkills, vehicleSkills), jobSkills)
                .distinct()
                .sorted()
                .toList();

        if(skillNames.size() > 64)
            throw new IllegalStateException("Too many skills used. Max is 64.");

        // Allocate an byte-unique integer to each skill.
        var skills = new HashMap<String, Skill>();
        for (int i = 0; i < skillNames.size(); i++) {
            skills.put(skillNames.get(i), new Skill(
                    1 << i,
                    skillNames.get(i)
            ));
        }

        return skills;
    }
}
