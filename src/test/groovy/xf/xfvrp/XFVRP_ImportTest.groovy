/*
 *  * Copyright (c) 2012-present Holger Schneider
 *  * All rights reserved.
 *  *
 *  * This source code is licensed under the MIT License (MIT) found in the
 *  * LICENSE file in the root directory of this source tree.
 */

package xf.xfvrp

import spock.lang.Specification
import xf.xfvrp.model.EucledianMetric
import xf.xfvrp.model.LoadType
import xf.xfvrp.report.Report
import xf.xfvrp.report.ReportEvent
import xf.xfvrp.report.RouteReport

import java.util.stream.Collectors

class XFVRP_ImportTest extends Specification {

    def "Create initial and valid solution from given data"() {
        def vrp = new XFVRP()
        vrp.setMetric(new EucledianMetric())

        addDepot(vrp,"DEP1",10, 10,11, [[10,40],[50,80]] as List<float[]>,['A', 'B'])
        addDepot(vrp,"DEP2",20, 20,12, [[10,40],[50,80]] as List<float[]>,['A', 'C'])
        addJob  (vrp, "J1", 5, 5, [3,1,1] as float[], LoadType.DELIVERY, 5, [[5,60],[70,100]] as List<float[]>, ['B', 'D'])
        addJob  (vrp, "J2", 15, 15, [3,1,1] as float[], LoadType.DELIVERY, 6, [[5,60],[70,100]] as List<float[]>, ['C', 'E'])
        addJob  (vrp, "J3", 15, 15, [3,1,1] as float[], LoadType.DELIVERY, 6, [[5,60],[70,100]] as List<float[]>, ['F'])
        addVehicle(vrp, 'LKW1', 'DEP1', ['DEP1', 'DEP2'], [5,5,5] as float[], 100, 0.1, [[0,100],[200,300]] as List<float[]>,['D'])
        addVehicle(vrp, 'LKW2', 'DEP2', ['DEP1', 'DEP2'], [5,5,5] as float[], 100, 0.1, [[0,100],[200,300]] as List<float[]>,['E'])

        when:
        vrp.execute()
        def report = vrp.getReport()

        then:
        toString(report, 0) == 'DEP1,J1,DEP1'
        toString(report, 1) == 'DEP2,J2,DEP2'
        report.unplannedJobs[0] == 'J3'
    }

    void addDepot(XFVRP vrp, String name, float xlong, float ylat, float serviceTime, List<float[]> timeWindows, List<String> skills) {
        def d = vrp.buildDepot()
                .setName(name)
                .setXlong(xlong)
                .setYlat(ylat)
                .setServiceTime(serviceTime)

        for (float[] f : timeWindows) {
            d.setTimeWindow(f)
        }

        for (String s : skills) {
            d.setProvidedSkill(s)
        }
    }

    void addJob(XFVRP vrp, String name, float xlong, float ylat, float[] demand, LoadType loadType, float serviceTime, List<float[]> timeWindows, List<String> skills) {
        def j = vrp.buildJob()
                .setName(name)
                .setXlong(xlong)
                .setYlat(ylat)
                .setServiceTime(serviceTime)
                .setAmount(demand)
                .setLoadType(loadType)

        for (float[] f : timeWindows) {
            j.setTimeWindow(f)
        }

        for (String s : skills) {
            j.setExpectedSkill(s)
        }
    }

    void addVehicle(XFVRP vrp, String name, String homeDepot, List<String> depots, float[] capacities, float fixCost, float varCost, List<float[]> timeWindows, List<String> skills) {
        def v = vrp.buildVehicle()
                .setName(name)
                .setHomeDepot(homeDepot)
                .setCapacities(capacities)
                .setFixCost(fixCost)
                .setVariableCost(varCost)

        for (String d : depots) {
            v.setAvailableDepot(d)
        }

        for (float[] f : timeWindows) {
            v.setTimeWindow(f)
        }

        for (String s : skills) {
            v.setProvidedSkill(s)
        }
    }

    String toString(Report rep, int routeIdx) {
        return rep.routes[routeIdx].events.stream()
                .map(e -> e.name())
                .collect(Collectors.joining(','))
    }
}
