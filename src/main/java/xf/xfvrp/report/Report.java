package xf.xfvrp.report;

import xf.xfvrp.model.Vehicle;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
public class Report {

	private final ReportSummary summary = new ReportSummary();
	private final List<RouteReport> reportList = new ArrayList<>();

	private final Set<Vehicle> vehicleSet = new HashSet<>();
	
	/**
	 * A Report is the structral representation of a route planning solution.
	 *
	 */
	public Report() {
	}

	public Set<Vehicle> getVehicles() {
		return vehicleSet;
	}
	
	/* Add-Functions */

	public void add(RouteReport route) {
		if(route.getSummary().getNbrOfEvents() > 0) {
			summary.add(route);
			reportList.add(route);
			vehicleSet.add(route.getVehicle());
		}
	}

	/* GetFunctions-Functions */

	public List<RouteReport> getRoutes() {
		return reportList;
	}

	public ReportSummary getSummary() {
		return summary;
	}

	/**
	 * Import the route reports of another report object into this report.
	 *
	 * @param rep Another report object
	 */
	public void importReport(Report rep) {
		for (RouteReport tRep : rep.getRoutes())
			add(tRep);
	}
}
