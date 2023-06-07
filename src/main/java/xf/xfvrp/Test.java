package xf.xfvrp;

import xf.xfvrp.model.*;
import xf.xfvrp.optimize.construction.RandomizedSolutionBuilder;
import xf.xfvrp.optimize.evaluation.Evaluator;

public class Test {

    public Test() {
        var model = createModel();
        var solution = new RandomizedSolutionBuilder().execute(model);
        Evaluator.evaluate(solution);

        System.out.println(solution.getQuality());
    }

    private Model createModel() {
        var depot1 = new Depot(
                0,
                "DEP1",
                new Location(0, 10, 10),
                new float[][]{{10,40},{50,80}},
                10f,
                29
        );
        var depot2 = new Depot(
                1,
                "DEP2",
                new Location(1, 20, 20),
                new float[][]{{10,40},{50,80}},
                10f,
                31
        );

        return new Model(
                new Vehicle[]{
                        new Vehicle(
                                0,
                                "LKW",
                                depot1,
                                new Depot[]{depot1, depot2},
                                new float[]{10,5,3},
                                new float[][]{{0,100},{200,300}},
                                100f,
                                0.1f,
                                20
                        )
                },
                new Job[]{
                        new Job(
                                0,
                                "J_123",
                                new Location(2, 5, 5),
                                new float[]{3,1,1},
                                new float[][]{{5,60},{70,100}},
                                LoadType.DELIVERY,
                                null,
                                3,
                                4,
                                100
                        )
                },
                new Depot[] {depot1, depot2},
                new EucledianMetric(),
                new Skill[]{new Skill(4, "Beer")}
        );
    }

    public static void main(String[] args) {
        new Test();
    }
}
