/**
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
package openlr.map;

import org.testng.annotations.Test;

import static org.testng.Assert.assertSame;

/**
 * Tests the enums code below openlr.map
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public class EnumsTest {

    /**
     * Tests {@link FunctionalRoadClass}.
     */
    @Test
    public final void testFunctionalRoadClass() {

        FunctionalRoadClass[] enumValues = FunctionalRoadClass.values();
        int i = 0;
        for (FunctionalRoadClass frc : FunctionalRoadClass.getFRCs()) {
            assertSame(frc, enumValues[i]); // check the list sequence
            // check if FRC ID is incremented from zero upwards
            assertSame(frc.getID(), i);
            i++;
        }
    }

    /**
     * Tests {@link FormOfWay}.
     */
    @Test
    public final void testFormOfWay() {

        FormOfWay[] enumValues = FormOfWay.values();
        int i = 0;
        for (FormOfWay fow : FormOfWay.getFOWs()) {
            assertSame(fow, enumValues[i]); // check the list sequence
            // check if FRC ID is incremented from zero upwards
            assertSame(fow.getID(), i);
            i++;
        }
    }

}
