package xf.xfvrp.input;

import java.util.ArrayList;
import java.util.List;

public class DepotData {

    private String name;
    private int geoId;
    private float xlong;
    private float ylat;
    private List<float[]> timeWindows = new ArrayList<>();
    private float serviceTime;
    private List<String> providedSkills = new ArrayList<>();

    public DepotData setName(String name) {
        this.name = name;
        return this;
    }

    public DepotData setGeoId(int geoId) {
        this.geoId = geoId;
        return this;
    }

    public DepotData setXlong(float xlong) {
        this.xlong = xlong;
        return this;
    }

    public DepotData setYlat(float ylat) {
        this.ylat = ylat;
        return this;
    }

    public DepotData setTimeWindow(float[] timeWindow) {
        this.timeWindows.add(timeWindow);
        return this;
    }

    public DepotData setServiceTime(float serviceTime) {
        this.serviceTime = serviceTime;
        return this;
    }

    public DepotData setProvidedSkill(String providedSkill) {
        this.providedSkills.add(providedSkill);
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

    List<float[]> getTimeWindows() {
        return timeWindows;
    }

    float getServiceTime() {
        return serviceTime;
    }

    List<String> getProvidedSkills() {
        return providedSkills;
    }
}
