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

class XFVRP_ImportTest extends Specification {

    def "First test"() {
        def vrp = new XFVRP()
        vrp.setMetric(new EucledianMetric())

        var depot1 = addDepot(vrp,"DEP1",10, 10,11, [[10,40],[50,80]] as List<float[]>,['A', 'B'])
        var depot2 = addDepot(vrp,"DEP2",20, 20,12, [[10,40],[50,80]] as List<float[]>,['A', 'C'])
        var job1 = addJob(vrp, "J1", 5, 5, [3,1,1] as float[], LoadType.DELIVERY, 5, [[5,60],[70,100]] as List<float[]>, ['B', 'D'])
        var job2 = addJob(vrp, "J2", 15, 15, [3,1,1] as float[], LoadType.DELIVERY, 6, [[5,60],[70,100]] as List<float[]>, ['C', 'E'])
        var veh1 = addVehicle(vrp, 'LKW1', 'DEP1', ['DEP1', 'DEP2'], [5,5,5] as float[], 100, 0.1, [[0,100],[200,300]] as List<float[]>,['D'])
        var veh2 = addVehicle(vrp, 'LKW2', 'DEP2', ['DEP1', 'DEP2'], [5,5,5] as float[], 100, 0.1, [[0,100],[200,300]] as List<float[]>,['E'])

        when:
        vrp.execute()

        then:
        1 == 1
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
}
