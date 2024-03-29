package xf.xfvrp.report;

import xf.xfvrp.model.Vehicle;

import java.util.ArrayList;
import java.util.List;

/** 
 * Copyright (c) 2012-2022 Holger Schneider
 * All rights reserved.
 *
 * This source code is licensed under the MIT License (MIT) found in the
 * LICENSE file in the root directory of this source tree.
 *
 *
 * LPReport of a route by a list of events
 * 
 * Each route report has a allocated vehicle, which contains
 * the parameters.
 * 
 * @author hschneid
 *
 */
public class RouteReport {

	private final RouteReportSummary summary;
	private final Vehicle vehicle;
	private final List<ReportEvent> reportEventList = new ArrayList<>();

	
	public RouteReport(Vehicle vehicle) {
		this.vehicle = vehicle;
		this.summary = new RouteReportSummary(vehicle);
	}

	public void add(ReportEvent e) {
		summary.add(e);
		reportEventList.add(e);
	}
		
	public RouteReportSummary getSummary() {
		return summary;
	}
	
	/**
	 * 
	 * @return
	 */
	public Vehicle getVehicle() {
		return vehicle;
	}
	
	/**
	 * Returns a list of all event objects of all sub routes of this route.
	 * 
	 * @return list of event objects
	 */
	public List<ReportEvent> getEvents() {
		return reportEventList;
	}
	
	
}
