package xf.xfvrp.optimize.construction;

import xf.xfvrp.model.Event;
import xf.xfvrp.model.Job;
import xf.xfvrp.model.Model;
import xf.xfvrp.optimize.Solution;
import xf.xfvrp.optimize.evaluation.Evaluator;
import xf.xfvrp.optimize.evaluation.RouteEvaluator;
import xf.xfvrp.optimize.evaluation.SolutionPurifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
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
        // Set unassigned jobs
        solution.setUnassignedJobs(Arrays.asList(model.jobs()));

        return solution;
    }

    private void addJobs(Solution solution) {
        var jobs = new ArrayList<>(solution.getUnassignedJobs());

        // Bring customers into randomized order
        Collections.shuffle(jobs, rand);
        SolutionPurifier.purify(solution);

        solution.setUnassignedJobs(new ArrayList<>());

        // For every customer block ...
        JOBS:
        for (Job job : jobs) {
            var routes = IntStream.range(0, solution.getSchedule().length)
                    .boxed()
                    .collect(Collectors.toList());
            Collections.shuffle(routes, rand);

            // For every route
            for (Integer j : routes) {
                // Check if customer can be added at the end of this route
                var newRoute = createRoute(job, solution.getSchedule()[j]);
                var isValid = check(solution, newRoute);
                if (isValid) {
                    solution.getSchedule()[j] = newRoute;
                    SolutionPurifier.purify(solution);
                    continue JOBS;
                }
            }

            // Job was not assinged
            solution.getUnassignedJobs().add(job);
        }
    }

    private boolean check(Solution sol, Event[] newRoute) {
        var newSol = new Solution(sol.getModel());

        var schedule = new Event[1][];
        schedule[0] = newRoute;
        newSol.setSchedule(schedule);
        var result = RouteEvaluator.eval(0, newSol);

        // Invalid is result == -1
        return result > 0;
    }

    private Event[] createRoute(Job job, Event[] route) {
        var newRoute = new Event[route.length + 1];
        System.arraycopy(route, 0, newRoute, 0, route.length - 1);
        newRoute[route.length - 1 + 1] = job;
        newRoute[newRoute.length - 1] = route[route.length - 1];

        return newRoute;
    }
}
