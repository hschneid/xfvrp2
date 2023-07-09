package xf.xfvrp.optimize.evaluation;

import xf.xfvrp.model.Depot;
import xf.xfvrp.model.Event;
import xf.xfvrp.optimize.Solution;

public class SolutionPurifier {

    /**
     * Purifying a solution
     * _
     * Remove obsolete routes
     * Each vehicle has a schedule, which can contain multiple routes with different intermediate depots.
     * Helping the optimization process by removing obsolete routes is one goal of the purifying. Hereby
     * obsolete depot visits are removed.
     * Example: [D1 - J1 - D1 - D2 - D3 - J2 - D1]
     * Here the visit of depot D2 is obsolete, as no action happens one the routes to and from D2.
     * Result:  [D1 - J1 - D1 -       D3 - J2 - D1]
     * _
     * Remove duplicate depot visits
     * If same depot is listed on same place in schedule, then this is obsolete.
     * Example: [D1 - J1 - D2 - D2 - J2 - D1]
     * Result:  [D1 - J1 - D2 -    - J2 - D1]
     * _
     * Add empty routes
     * As the optimization process can only move jobs, each possible route scenario must already exist in the schedule.
     * For this, empty routes must be created at the end of schedule, which must not change the quality of the solution.
     * Example: [D1 - J1 - D2 - J2 - D3 - J3 - D1]
     * Result:  [D1 - J1 - D2 - J2 - D3 - J3 - D1 - D1 - D2 - D2 - D3 - D3 - D2 - D1 - D3 - D1]
     *
     */
    public static void purify(Solution solution) {
        for (int routeIdx = 0; routeIdx < solution.getSchedule().length; routeIdx++) {
            removeObsoleteDepotVisits(routeIdx, solution);
            //addEmptyRoutes(routeIdx, solution);
            removeDuplicateDepotVisits(routeIdx, solution);
        }
    }


    private static void removeObsoleteDepotVisits(int routeIdx, Solution solution) {
        var route = solution.getSchedule()[routeIdx];
        if(route.length <= 2)
            return;

        var inactiveEvents = new boolean[route.length];
        var foundIncidents = 0;

        // Check
        var lastEvent = route[0];
        var nextEvent = route[2];
        for (int i = 1; i < route.length - 1; i++) {
            if(route[i] instanceof Depot) {
                if(lastEvent instanceof Depot && nextEvent instanceof Depot) {
                    inactiveEvents[i] = true;
                    foundIncidents++;
                }
            }

            lastEvent = route[i];
            nextEvent = route[i + 1];
        }

        if(foundIncidents == 0)
            return;

        solution.getSchedule()[routeIdx] = reduceRoute(route, inactiveEvents, foundIncidents);
    }

    private static void removeDuplicateDepotVisits(int routeIdx, Solution solution) {
        var route = solution.getSchedule()[routeIdx];
        if(route.length <= 2)
            return;

        var inactiveEvents = new boolean[route.length];
        var foundIncidents = 0;

        // Check
        var lastEvent = route[0];
        for (int i = 1; i < route.length; i++) {
            if(route[i] instanceof Depot && lastEvent instanceof Depot) {
                if(route[i].location().idx() == lastEvent.location().idx()) {
                    inactiveEvents[i] = true;
                    foundIncidents++;
                }
            }

            lastEvent = route[i];
        }

        if(foundIncidents == 0)
            return;

        solution.getSchedule()[routeIdx] = reduceRoute(route, inactiveEvents, foundIncidents);
    }

    private static Event[] reduceRoute(Event[] route, boolean[] inactiveEvents, int foundIncidents) {
        var newRoute = new Event[route.length - foundIncidents];
        var j = 0;
        for (int i = 0; i < route.length; i++) {
            if(!inactiveEvents[i])
                newRoute[j++] = route[i];
        }

        return newRoute;
    }

}
