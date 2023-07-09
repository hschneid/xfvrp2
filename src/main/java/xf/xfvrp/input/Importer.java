package xf.xfvrp.input;

import xf.xfvrp.model.Metric;

import java.util.ArrayList;
import java.util.List;

public class Importer {

    private final List<DepotData> depots = new ArrayList<>();
    private final List<JobData> jobs = new ArrayList<>();
    private final List<VehicleData> vehicles = new ArrayList<>();

    private VehicleData lastVehicleData = null;
    private DepotData lastDepotData = null;
    private JobData lastJobData = null;

    private Metric metric;

    public VehicleData createVehicleData() {
        if(lastVehicleData != null)
            vehicles.add(lastVehicleData);

        lastVehicleData = new VehicleData();

        return lastVehicleData;
    }

    public DepotData createDepotData() {
        if(lastDepotData != null)
            depots.add(lastDepotData);

        lastDepotData = new DepotData();

        return lastDepotData;
    }

    public JobData createJobData() {
        if(lastJobData != null)
            jobs.add(lastJobData);

        lastJobData = new JobData();

        return lastJobData;
    }

    public void finishImport() {
        if(lastDepotData != null)
            depots.add(lastDepotData);
        if(lastJobData != null)
            jobs.add(lastJobData);
        if(lastVehicleData != null)
            vehicles.add(lastVehicleData);

        lastDepotData = null;
        lastJobData = null;
        lastVehicleData = null;
    }

    public List<DepotData> getDepots() {
        return depots;
    }

    public List<JobData> getJobs() {
        return jobs;
    }

    public List<VehicleData> getVehicles() {
        return vehicles;
    }

    public Metric getMetric() {
        return metric;
    }

    public void setMetric(Metric metric) {
        this.metric = metric;
    }
}
