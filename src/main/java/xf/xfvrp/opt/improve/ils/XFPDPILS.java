package xf.xfvrp.opt.improve.ils;

import xf.xfvrp.base.exception.XFVRPException;
import xf.xfvrp.opt.Solution;
import xf.xfvrp.opt.XFVRPOptBase;
import xf.xfvrp.opt.improve.routebased.move.XFPDPSingleMove;

/**
 * Copyright (c) 2012-2023 Holger Schneider
 * All rights reserved.
 * <p>
 * This source code is licensed under the MIT License (MIT) found in the
 * LICENSE file in the root directory of this source tree.
 * <p>
 * <p>
 * Optimization procedure for iterative local search
 * <p>
 * Three local search procedures with adaptive randomized variable neighborhood selection.
 *
 * @author hschneid
 */
public class XFPDPILS extends XFILS {

    /*
     * (non-Javadoc)
     * @see xf.xfvrp.opt.improve.ils.XFILS#execute(xf.xfvrp.opt.Solution)
     */
    @Override
    public Solution execute(Solution solution) throws XFVRPException {
        optArr = new XFVRPOptBase[]{
                new XFPDPSingleMove()
        };
        optPropArr = new double[]{
                1
        };
        randomChangeService = new XFPDPRandomChangeService();

        return super.execute(solution);
    }
}