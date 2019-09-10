/**
 * Licensed to the TomTom International B.V. under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  TomTom International B.V.
 * licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * <p>
 * Copyright (C) 2009-2012 TomTom International B.V.
 * <p>
 * TomTom (Legal Department)
 * Email: legal@tomtom.com
 * <p>
 * TomTom (Technical contact)
 * Email: openlr@tomtom.com
 * <p>
 * Address: TomTom International B.V., Oosterdoksstraat 114, 1011DK Amsterdam,
 * the Netherlands
 */
/**
 *  Copyright (C) 2009-2012 TomTom International B.V.
 *
 *   TomTom (Legal Department)
 *   Email: legal@tomtom.com
 *
 *   TomTom (Technical contact)
 *   Email: openlr@tomtom.com
 *
 *   Address: TomTom International B.V., Oosterdoksstraat 114, 1011DK Amsterdam,
 *   the Netherlands
 */
package openlr.utils.locref.boundary;

/**
 * The Class Envelope defines a rectangle which can grow.
 */
class Envelope {

    /** the minimum x-coordinate. */
    private double minx = 0;

    /** the maximum x-coordinate. */
    private double maxx = -1;

    /** the minimum y-coordinate. */
    private double miny = 0;

    /** the maximum y-coordinate. */
    private double maxy = -1;

    /**
     * Include.
     *
     * @param x
     *            the x
     * @param y
     *            the y
     */
    void include(final double x, final double y) {
        if (maxx < minx) {
            minx = x;
            maxx = x;
            miny = y;
            maxy = y;
        } else {
            if (x < minx) {
                minx = x;
            }
            if (x > maxx) {
                maxx = x;
            }
            if (y < miny) {
                miny = y;
            }
            if (y > maxy) {
                maxy = y;
            }
        }
    }

    /**
     * Gets the min x.
     *
     * @return the min x
     */
    double getMinX() {
        return minx;
    }

    /**
     * Gets the max x.
     *
     * @return the max x
     */
    double getMaxX() {
        return maxx;
    }

    /**
     * Gets the min y.
     *
     * @return the min y
     */
    double getMinY() {
        return miny;
    }

    /**
     * Gets the max y.
     *
     * @return the max y
     */
    double getMaxY() {
        return maxy;
    }
}
