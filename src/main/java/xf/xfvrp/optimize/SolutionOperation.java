package xf.xfvrp.optimize;/*
 *  * Copyright (c) 2012-present Holger Schneider
 *  * All rights reserved.
 *  *
 *  * This source code is licensed under the MIT License (MIT) found in the
 *  * LICENSE file in the root directory of this source tree.
 */

import xf.xfvrp.model.Event;

public class SolutionOperation {

    public static void appendToVehicle(Solution solution, int vehicleIdx, Event... e) {
        var route = solution.getSchedule()[vehicleIdx];

        var newRoute = new Event[route.length + e.length];
        System.arraycopy(route, 0, newRoute, 0, route.length - 1);
        System.arraycopy(e, 0, newRoute, route.length - 1, e.length);
        newRoute[newRoute.length - 1] = route[route.length - 1];

        solution.getSchedule()[vehicleIdx] = newRoute;
    }

    public static void setToVehicle(Solution solution, int vehicleIdx, int position, Event... e) {
        var route = solution.getSchedule()[vehicleIdx];

        var newRoute = new Event[route.length + 1];
        System.arraycopy(route, 0, newRoute, 0, position);
        System.arraycopy(e, 0, newRoute, position, e.length);
        System.arraycopy(route, position + 1, newRoute, position + e.length, route.length - position - e.length);

        solution.getSchedule()[vehicleIdx] = newRoute;
    }

    public static void clearVehicle(Solution solution, int vehicleIdx) {
        var route = solution.getSchedule()[vehicleIdx];

        var newRoute = new Event[2];
        newRoute[0] = route[0];
        newRoute[1] = route[route.length - 1];

        solution.getSchedule()[vehicleIdx] = newRoute;
    }

}
