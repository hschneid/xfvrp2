package xf.xfvrp.optimize.construction;

import xf.xfvrp.model.Event;
import xf.xfvrp.model.Job;
import xf.xfvrp.model.Model;
import xf.xfvrp.model.Vehicle;
import xf.xfvrp.optimize.Solution;
import xf.xfvrp.optimize.SolutionOperation;
import xf.xfvrp.optimize.evaluation.Evaluator;
import xf.xfvrp.optimize.evaluation.RouteEvaluator;
import xf.xfvrp.optimize.evaluation.SolutionPurifier;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RandomizedSolutionBuilder {

    private final Random rand = new Random(1234);

    public Solution build(Model model) {
        var solution = createSolution(model);

        addJobs(solution);

        Evaluator.evaluate(solution);

        return solution;
    }

    private Solution createSolution(Model model) {
        var solution = new Solution(model);

        // Create schedule
        solution.setSchedule(new Event[model.vehicles().length][2]);

        // Set home depots
        for (int i = 0; i < solution.getSchedule().length; i++) {
            var homeDepot = solution.getModel().vehicles()[i].homeDepot();
            solution.getSchedule()[i][0] = homeDepot;
            solution.getSchedule()[i][1] = homeDepot;
        }

        // Set all jobs to unassigned vehicle
        SolutionOperation.appendToVehicle(solution, model.unassignedVehicleIdx(), model.jobs());

        return solution;
    }

    private void addJobs(Solution solution) {
        var jobs = Arrays.stream(solution.getSchedule()[solution.getModel().unassignedVehicleIdx()])
                .map(j -> (j instanceof Job job) ? job : null)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        // Bring customers into randomized order
        Collections.shuffle(jobs, rand);
        SolutionPurifier.purify(solution);

        SolutionOperation.clearVehicle(solution, solution.getModel().unassignedVehicleIdx());

        JOBS:
        for (Job job : jobs) {
            // Create random order of routes
            var routes = IntStream.range(0, solution.getSchedule().length)
                    .boxed()
                    .filter(f -> f != solution.getModel().unassignedVehicleIdx())
                    .collect(Collectors.toList());
            Collections.shuffle(routes, rand);

            // For every route
            for (Integer routeIdx : routes) {
                // Check if customer can be added at the end of this route
                var oldRoute = solution.getSchedule()[routeIdx];
                SolutionOperation.appendToVehicle(solution, routeIdx, job);
                var quality = RouteEvaluator.eval(routeIdx, solution);
                if (quality > 0) {
                    SolutionPurifier.purify(solution);
                    continue JOBS;
                }

                solution.getSchedule()[routeIdx] = oldRoute;
            }

            // Job was not assigned
            SolutionOperation.appendToVehicle(solution, solution.getModel().unassignedVehicleIdx(), job);
        }
    }
}
