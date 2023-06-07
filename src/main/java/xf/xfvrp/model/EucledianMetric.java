package xf.xfvrp.model;

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
public class EucledianMetric implements Metric {

	@Override
	public float[] getDistanceAndTime(Location src, Location dst, Vehicle veh) {
		var f =  calc(src.xlong(), src.ylat(), dst.xlong(), dst.ylat());
		return new float[]{f, f};
	}

	private float calc(float x1, float y1, float x2, float y2) {
		return (float)Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
	}
}
