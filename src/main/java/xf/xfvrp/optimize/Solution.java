package xf.xfvrp.optimize;

import xf.xfvrp.model.Event;
import xf.xfvrp.model.Job;
import xf.xfvrp.model.Model;

import java.util.List;

public class Solution {

    private final Model model;
    // Schedule of vehicles -> list of events (Jobs or Depots)
    private Event[][] schedule;

    private float quality;

    private List<Job> unassignedJobs;

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

    public List<Job> getUnassignedJobs() {
        return unassignedJobs;
    }

    public void setUnassignedJobs(List<Job> unassignedJobs) {
        this.unassignedJobs = unassignedJobs;
    }
}
