package xf.xfvrp.report.build;

import xf.xfvrp.exception.XFVRPException;
import xf.xfvrp.model.*;
import xf.xfvrp.optimize.Solution;
import xf.xfvrp.report.Report;
import xf.xfvrp.report.ReportEvent;
import xf.xfvrp.report.RouteReport;
import xf.xfvrp.report.SiteType;

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
        Report rep = new Report();

        Event[][] schedule = solution.getSchedule();
        for (int i = 0; i < schedule.length; i++) {
            Event[] route = schedule[i];
            if (containsCustomers(route)) {
                rep.add(getRouteReport(route, solution.getModel().vehicles()[i]));
            }
        }

        rep.getUnplannedJobs().addAll(
                solution.getUnassignedJobs().stream()
                        .map(Job::name)
                        .toList()
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
        return true;
    }
}
