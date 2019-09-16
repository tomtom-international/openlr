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
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; version 2 of the License and the extra
 * conditions for OpenLR. (see openlr-license.txt)
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 * <p>
 * Copyright (C) 2009-2019 TomTom International B.V.
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
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; version 2 of the License and the extra
 *  conditions for OpenLR. (see openlr-license.txt)
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along
 *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

/**
 *  Copyright (C) 2009-2019 TomTom International B.V.
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
package openlr.binary;

import openlr.map.Line;
import openlr.map.Node;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class LocationCheckTest {

    @Test
    public final void testLocationCheck() {
        Mockery context = new Mockery();
        final Line l1 = context.mock(Line.class, "l1");
        final Node sl1 = context.mock(Node.class, "sl1)");
        final Node el1 = context.mock(Node.class, "el1)");

        final Line l2 = context.mock(Line.class, "l2");
        final Node sl2 = context.mock(Node.class, "sl2)");
        final Node el2 = context.mock(Node.class, "el2)");

        final Line l3 = context.mock(Line.class, "l3");
        final Node sl3 = context.mock(Node.class, "sl3)");
        final Node el3 = context.mock(Node.class, "el3)");


        context.checking(new Expectations() {
            {
                allowing(l1).getStartNode();
                will(returnValue(sl1));
            }

            {
                allowing(l1).getEndNode();
                will(returnValue(el1));
            }

            {
                allowing(sl1).getLatitudeDeg();
                will(returnValue(65.69));
            }

            {
                allowing(el1).getLatitudeDeg();
                will(returnValue(64.69));
            }
        });

        context.checking(new Expectations() {
            {
                allowing(l2).getStartNode();
                will(returnValue(sl2));
            }

            {
                allowing(l2).getEndNode();
                will(returnValue(el2));
            }

            {
                allowing(sl2).getLatitudeDeg();
                will(returnValue(66.00));
            }

            {
                allowing(el2).getLatitudeDeg();
                will(returnValue(64.69));
            }
        });

        context.checking(new Expectations() {
            {
                allowing(l3).getStartNode();
                will(returnValue(sl3));
            }

            {
                allowing(l3).getEndNode();
                will(returnValue(el3));
            }

            {
                allowing(sl3).getLatitudeDeg();
                will(returnValue(66.00));
            }

            {
                allowing(el3).getLatitudeDeg();
                will(returnValue(70.0));
            }
        });

        List<Line> lines1 = new ArrayList<Line>();
        lines1.add(l1);
        int value1 = LocationCheck.calculateMaxDistanceFromPath(lines1);
        assertEquals(value1, 15000);

        List<Line> lines2 = new ArrayList<Line>();
        lines2.add(l2);
        int value2 = LocationCheck.calculateMaxDistanceFromPath(lines2);
        assertEquals(value2, 14820);

        List<Line> lines3 = new ArrayList<Line>();
        lines3.add(l3);
        int value3 = LocationCheck.calculateMaxDistanceFromPath(lines3);
        assertEquals(value3, 12462);

        List<Line> lines4 = new ArrayList<Line>();
        lines4.add(l2);
        lines4.add(l3);
        int value4 = LocationCheck.calculateMaxDistanceFromPath(lines4);
        assertEquals(value4, 12462);
    }

}
