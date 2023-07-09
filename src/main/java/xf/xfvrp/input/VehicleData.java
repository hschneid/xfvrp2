package xf.xfvrp.input;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (c) 2012-2023 Holger Schneider
 * All rights reserved.
 *
 * This source code is licensed under the MIT License (MIT) found in the
 * LICENSE file in the root directory of this source tree.
 *
 *
 * @author hschneid
 *
 */
public class VehicleData {

	private String name;
	private String homeDepot;
	private List<String> availableDepots = new ArrayList<>();
	private float[] capacities;
	private List<float[]> timeWindows = new ArrayList<>();
	private float fixCost;
	private float varCost;
	private List<String> providedSkills = new ArrayList<>();


	/**
	 * @param name the name to set
	 */
	public VehicleData setName(String name) {
		this.name = name;
		return this;
	}

	public VehicleData setHomeDepot(String homeDepot) {
		this.homeDepot = homeDepot;
		return this;
	}

	public VehicleData setAvailableDepot(String availableDepot) {
		this.availableDepots.add(availableDepot);
		return this;
	}

	public VehicleData setCapacities(float[] capacities) {
		this.capacities = capacities;
		return this;
	}

	public VehicleData setTimeWindow(float[] timeWindow) {
		this.timeWindows.add(timeWindow);
		return this;
	}

	public VehicleData setFixCost(float fixCost) {
		this.fixCost = fixCost;
		return this;
	}

	public VehicleData setVariableCost(float varCost) {
		this.varCost = varCost;
		return this;
	}

	public VehicleData setProvidedSkill(String providedSkill) {
		this.providedSkills.add(providedSkill);
		return this;
	}

	String getName() {
		return name;
	}

	String getHomeDepot() {
		return homeDepot;
	}

	List<String> getAvailableDepots() {
		return availableDepots;
	}

	float[] getCapacities() {
		return capacities;
	}

	List<float[]> getTimeWindows() {
		return timeWindows;
	}

	float getFixCost() {
		return fixCost;
	}

	float getVarCost() {
		return varCost;
	}

	List<String> getProvidedSkills() {
		return providedSkills;
	}
}