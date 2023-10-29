package xf.xfvrp.optimize;

import xf.xfvrp.model.Event;
import xf.xfvrp.model.Model;

public class Solution {

    private final Model model;
    // Schedule of vehicles -> list of events (Jobs or Depots)
    private Event[][] schedule;

    private float quality;

    public Solution(Model model) {
        this.model = model;
    }

    public Model getModel() {
        return model;
    }

    public Event[][] getSchedule() {
        return schedule;
    }

    public void setSchedule(Event[][] schedule) {
        this.schedule = schedule;
    }

    public float getQuality() {
        return quality;
    }

    public void setQuality(float quality) {
        this.quality = quality;
    }
}
