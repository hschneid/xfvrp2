package xf.xfvrp.optimize.evaluation;

import xf.xfvrp.optimize.Solution;

public class Evaluator {

    public static void evaluate(Solution solution) {
        float quality = 0;
        for (int i = 0; i < solution.getSchedule().length; i++) {
            var routeQuality = RouteEvaluator.eval(i, solution);
            if(routeQuality == -1) {
                solution.setQuality(-1);
                return;
            }

            quality += routeQuality;
        }

        solution.setQuality(quality);
    }
}
