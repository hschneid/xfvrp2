package xf.xfvrp.report;

import xf.xfvrp.model.LoadType;

/**
 * Copyright (c) 2012-2022 Holger Schneider
 * All rights reserved.
 *
 * This source code is licensed under the MIT License (MIT) found in the
 * LICENSE file in the root directory of this source tree.
 *
 *
 * The Event is the atomic structure of a report and represents the
 * visit of a truck at a certain node. The values in a event are not
 * cummulative, so the summary objects contain the summed values.
 *
 * Events are built up in the simulation process, when report object is
 * requested in the solution object.
 *
 * @author hschneid
 *
 */
public record ReportEvent(
		String name,
		float distance,
		float travelTime,
		float duration,
		float[] amounts,

		LoadType loadType,
		SiteType siteType,
		float arrival,
		float departure,
		float service,
		float waiting,
		float delay
){}
