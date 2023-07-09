package xf.xfvrp.input;

import xf.xfvrp.model.LoadType;

import java.util.ArrayList;
import java.util.List;

public class JobData {

    private String name;
    private int geoId;
    private float xlong;
    private float ylat;
    private float[] amount;
    private List<float[]> timeWindows = new ArrayList<>();
    private LoadType loadType;
    private String relatedJob;
    private float serviceTime;
    private List<String> expectedSkills = new ArrayList<>();

    public JobData setName(String name) {
        this.name = name;
        return this;
    }

    public JobData setGeoId(int geoId) {
        this.geoId = geoId;
        return this;
    }

    public JobData setXlong(float xlong) {
        this.xlong = xlong;
        return this;
    }

    public JobData setYlat(float ylat) {
        this.ylat = ylat;
        return this;
    }

    public JobData setAmount(float[] amount) {
        this.amount = amount;
        return this;
    }

    public JobData setTimeWindow(float[] timeWindow) {
        this.timeWindows.add(timeWindow);
        return this;
    }

    public JobData setLoadType(LoadType loadType) {
        this.loadType = loadType;
        return this;
    }

    public JobData setRelatedJob(String relatedJob) {
        this.relatedJob = relatedJob;
        return this;
    }

    public JobData setServiceTime(float serviceTime) {
        this.serviceTime = serviceTime;
        return this;
    }

    public JobData setExpectedSkill(String expectedSkill) {
        this.expectedSkills.add(expectedSkill);
        return this;
    }

    String getName() {
        return name;
    }

    int getGeoId() {
        return geoId;
    }

    float getXlong() {
        return xlong;
    }

    float getYlat() {
        return ylat;
    }

    float[] getAmount() {
        return amount;
    }

    List<float[]> getTimeWindows() {
        return timeWindows;
    }

    LoadType getLoadType() {
        return loadType;
    }

    String getRelatedJob() {
        return relatedJob;
    }

    float getServiceTime() {
        return serviceTime;
    }

    List<String> getExpectedSkills() {
        return expectedSkills;
    }
}
