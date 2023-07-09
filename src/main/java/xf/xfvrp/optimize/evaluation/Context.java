package xf.xfvrp.optimize.evaluation;

import xf.xfvrp.model.*;

import java.util.Arrays;

public class Context {

    private float distance;
    private float duration;
    private float time;

    private boolean hasDelay = false;

    private float[] loadedAmount;

    private int providedSkillsOfDepot;
    private int providedSkillsOfVehicle;

    private final Model model;
    private final Vehicle vehicle;

    public Context(Model model, Vehicle vehicle) {
        this.model = model;
        this.vehicle = vehicle;
        this.providedSkillsOfVehicle = vehicle.providedSkills();
    }

    private int currentRouteIdx;
    private Event lastEvent;
    private Event currentEvent;

    public Event nextEvent(Event[] route) {
        int nextIdx = -1;
        Event nextEvent = null;

        var needCheck = true;
        while(needCheck) {
            nextIdx = currentRouteIdx + 1;
            if(nextIdx >= route.length)
                return null;

            nextEvent = route[nextIdx];

            // Depots and Same location
            if(currentEvent instanceof Depot && nextEvent instanceof Depot &&
                    currentEvent.location().equals(nextEvent.location())) {
                continue;
            }

            // Empty depot -> D1 -> D2 -> D3 => D1 -> D3
            if(lastEvent instanceof Depot &&
                    currentEvent instanceof Depot &&
                    nextEvent instanceof Depot) {
                continue;
            }

            needCheck = false;
        }

        currentRouteIdx = nextIdx;
        lastEvent = currentEvent;
        currentEvent = nextEvent;

        addMetric();

        return currentEvent;
    }

    private void addMetric() {
        float[] distanceAndTime;
        if(lastEvent == null) {
            distanceAndTime = new float[]{0,0};
            var timeWindow = getTimeWindow(time, currentEvent.timeWindows());
            time = timeWindow[0];
        } else {
            distanceAndTime = model.metric().getDistanceAndTime(
                    lastEvent.location(),
                    currentEvent.location(),
                    vehicle
            );
        }

        distance += distanceAndTime[0];
        duration += distanceAndTime[1];
        time += distanceAndTime[1];

        var timeWindow = getTimeWindow(time, currentEvent.timeWindows());
        var waitingTime = Math.max(time, timeWindow[0]) - time;
        var serviceTime = currentEvent.serviceTime();

        duration += waitingTime + serviceTime;
        time += waitingTime + serviceTime;

        hasDelay = time > timeWindow[1];
    }

    public void addDepot() {
        // Unload the amounts
        Arrays.fill(loadedAmount, 0);

        // Reset the provided skills of depot
        this.providedSkillsOfDepot = ((Depot)currentEvent).providedSkills();
    }

    public float[] getTimeWindow(float time, float[][] timeWindows) {
        if(timeWindows.length == 1)
            return timeWindows[0];

        // Choose first time window, that can hold the given time
        for (int i = 0, len = timeWindows.length; i < len; i++)
            if(time < timeWindows[i][1])
                return timeWindows[i];

        // If no time window can hold the given time, then return the last time window
        return timeWindows[timeWindows.length - 1];
    }

    public void addAmounts() {
        var job = (Job)currentEvent;
        for (int i = 0; i < loadedAmount.length; i++) {
            loadedAmount[i] += job.amount()[i];
        }
    }

    public float[] getLoadedAmount() {
        return loadedAmount;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public boolean hasDelay() {
        return hasDelay;
    }

    public float getTime() {
        return time;
    }

    public Event getCurrentEvent() {
        return currentEvent;
    }

    public int getProvidedSkillsOfVehicle() {
        return providedSkillsOfVehicle;
    }

    public int getProvidedSkillsOfDepot() {
        return providedSkillsOfDepot;
    }

    public float getDistance() {
        return distance;
    }
}
