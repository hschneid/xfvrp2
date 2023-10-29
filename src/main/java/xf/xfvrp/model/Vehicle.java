package xf.xfvrp.model;

public record Vehicle(
        int idx,
        String name,
        Depot homeDepot,
        Depot[] availableDepots,
        float[] capacities,
        float[][] timeWindows,
        float fixCost,
        float varCost,
        int providedSkills
) {

    public boolean isUnassignedVehicle() {
        return name.equals("ZZZ_UNASSIGNED");
    }
}
