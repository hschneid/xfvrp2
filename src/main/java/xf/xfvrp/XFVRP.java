package xf.xfvrp;

import xf.xfvrp.input.DepotData;
import xf.xfvrp.input.Importer;
import xf.xfvrp.input.JobData;
import xf.xfvrp.input.VehicleData;
import xf.xfvrp.input.ModelBuilder;
import xf.xfvrp.model.Metric;
import xf.xfvrp.optimize.construction.RandomizedSolutionBuilder;

public class XFVRP {

    private final Importer importer = new Importer();

    public void execute() {
        importer.finishImport();
        var model = ModelBuilder.build(
                importer.getDepots(),
                importer.getJobs(),
                importer.getVehicles(),
                importer.getMetric()
        );
        var solution = new RandomizedSolutionBuilder().build(model);
        System.out.println();
    }

    public DepotData buildDepot() {
        return importer.createDepotData();
    }

    public JobData buildJob() {
        return importer.createJobData();
    }

    public VehicleData buildVehicle() {
        return importer.createVehicleData();
    }

    public void setMetric(Metric metric) {
        importer.setMetric(metric);
    }

}
