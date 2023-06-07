package xf.xfvrp.optimize.evaluation;

import xf.xfvrp.model.Depot;
import xf.xfvrp.model.Event;
import xf.xfvrp.model.Job;
import xf.xfvrp.optimize.Solution;

public class RouteEvaluator {

    public static float eval(int routeIdx, Solution solution) {
        var vehicle = solution.getModel().vehicles()[routeIdx];
        var context = new Context(solution.getModel(), vehicle);

        Event currentEvent;
        while(
                (currentEvent = context.nextEvent(solution.getSchedule()[routeIdx]))
                        != null) {

            if(currentEvent instanceof Depot) {
                context.addDepot();
            } else if(currentEvent instanceof Job) {
                context.addAmounts();
            }

            if(!isContextValid(context))
                return -1;
        }

        return calculateCost(context);
    }

    private static float calculateCost(Context context) {
        return context.getVehicle().fixCost() +
                context.getDistance() * context.getVehicle().varCost();
    }

    private static boolean isContextValid(Context context) {
        // check capacity
        for (int i = 0; i < context.getVehicle().capacities().length; i++) {
            if(context.getLoadedAmount()[i] > context.getVehicle().capacities()[i])
                return false;
        }

        // check time windows
        if(context.hasDelay())
            return false;
        if(context.getTime() >
                context.getTimeWindow(context.getTime(), context.getVehicle().timeWindows())[1])
            return false;

        // check skills - Does the current context provide enough skills to process this job
        if(context.getCurrentEvent() instanceof Job job) {
            var providedSkills = context.getProvidedSkillsOfDepot() & context.getProvidedSkillsOfVehicle();
            var diffSkills = (providedSkills & job.expectedSkills()) ^ job.expectedSkills();

            if(diffSkills > 0)
                return false;
        }

        return true;
    }


}
