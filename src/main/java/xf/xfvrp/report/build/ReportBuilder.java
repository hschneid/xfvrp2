package xf.xfvrp.report.build;

import xf.xfvrp.exception.XFVRPException;
import xf.xfvrp.model.*;
import xf.xfvrp.optimize.Solution;
import xf.xfvrp.report.Report;
import xf.xfvrp.report.ReportEvent;
import xf.xfvrp.report.RouteReport;
import xf.xfvrp.report.SiteType;

import java.util.Arrays;

/**
 * Copyright (c) 2012-2022 Holger Schneider
 * All rights reserved.
 *
 * This source code is licensed under the MIT License (MIT) found in the
 * LICENSE file in the root directory of this source tree.
 *
 *
 * @author hschneid
 *
 */
public class ReportBuilder {

    public Report getReport(Solution solution) throws XFVRPException {
        var rep = new Report();

        var schedule = solution.getSchedule();
        for (int i = 0; i < schedule.length; i++) {
            var route = schedule[i];
            var vehicle = solution.getModel().vehicles()[i];

            if (!vehicle.isUnassignedVehicle() && containsCustomers(route)) {
                rep.add(getRouteReport(route, vehicle));
            }
        }

        // If there is a vehicle for unassinged jobs, find it and
        // list all job names to report
        Arrays.stream(solution.getModel().vehicles())
                .filter(Vehicle::isUnassignedVehicle)
                .mapToInt(Vehicle::idx)
                .findAny()
                .ifPresent(vehIdx ->
                        rep.getUnplannedJobs().addAll(
                                Arrays.stream(solution.getSchedule()[vehIdx])
                                        .filter(e -> e instanceof Job)
                                        .map(Event::name)
                                        .toList()
                        )
                );

        return rep;
    }

    private RouteReport getRouteReport(Event[] route, Vehicle vehicle) throws XFVRPException {
        RouteReport routeReport = new RouteReport(vehicle);

        for (Event event : route) {
            LoadType loadType = null;
            SiteType siteType = null;
            if (event instanceof Depot) {
                siteType = SiteType.DEPOT;
            }
            if (event instanceof Job job) {
                loadType = job.loadType();
                siteType = SiteType.JOB;
            }

            ReportEvent reportEvent = new ReportEvent(
                    event.name(),
                    0,
                    0,
                    0,
                    new float[0],

                    loadType,
                    siteType,
                    0,
                    0,
                    0,
                    0,
                    0
            );
            routeReport.add(reportEvent);
        }

        return routeReport;
    }

    private boolean containsCustomers(Event[] route) {
        for (int i = route.length - 1; i >= 0; i--) {
            if(route[i] instanceof Job)
                return true;
        }
        return false;
    }
}
