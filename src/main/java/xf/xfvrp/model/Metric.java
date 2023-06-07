package xf.xfvrp.model;


/**
 * Copyright (c) 2012-2023 Holger Schneider
 * All rights reserved.
 * <p>
 * This source code is licensed under the MIT License (MIT) found in the
 * LICENSE file in the root directory of this source tree.
 * <p>
 * <p>
 *
 * @author hschneid
 */
public interface Metric {

    float[] getDistanceAndTime(Location src, Location dst, Vehicle veh);
}
