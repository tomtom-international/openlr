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
 * Copyright (C) 2009,2010 TomTom International B.V.
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
 *  Copyright (C) 2009,2010 TomTom International B.V.
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
package openlr.decoder.properties;

import openlr.OpenLRProcessingException;
import openlr.decoder.rating.OpenLRRating.RatingCategory;
import openlr.properties.OpenLRPropertiesReader;
import openlr.properties.OpenLRPropertyAccess;
import org.apache.commons.configuration.Configuration;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

/**
 * Tests handling of the decoder properties.
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public class DecoderPropertiesTest {

    /** The expected value for property 'same line degradation'. */
    private static final float SAME_LINE_DEGRADIATION = (float) 0.1;
    /** The expected value for property 'line factor'. */
    private static final int LINE_FACTOR = 3;
    /** The expected value for property 'node factor'. */
    private static final int NODE_FACTOR = 2;
    /** The expected value for property 'FRC variance'. */
    private static final int FRC_VARIANCE = 2;
    /** The expected value for property 'minimum accepted rating'. */
    private static final int MIN_ACCEPTED_RATING = 800;
    /** The expected value for property 'maximum node distance'. */
    private static final int MAX_NODE_DISTANCE = 100;
    /** The expected value for property 'bearing distance'. */
    private static final int BEARING_DISTANCE = 20;
    /** The expected value of 18 for property 'bearing interval'. */
    private static final int BEARING_INTERVAL_18 = 18;
    /** The expected value of 12 for property 'bearing interval'. */
    private static final int BEARING_INTERVAL_12 = 12;
    /** The expected value of 6 for property 'bearing interval'. */
    private static final int BEARING_INTERVAL_6 = 6;
    /** A rating value of 25. */
    private static final int RATING_25 = 25;
    /** A rating value of 0. */
    private static final int RATING_0 = 0;
    /** A rating value of 50. */
    private static final int RATING_50 = 50;
    /** A rating value of 75. */
    private static final int RATING_75 = 75;
    /** A rating value of 100. */
    private static final int RATING_100 = 100;

    /**
     * Test reading of the decoder properties file.
     */
    @Test
    public final void testDecoderPropertiesRead() {
        File validProperties = new File(
                "src/test/resources/OpenLR-Decoder-Properties.xml");
        if (!validProperties.exists()) {
            fail("missing resources");
        }
        try {
            OpenLRPropertiesReader.loadPropertiesFromStream(
                    new FileInputStream(validProperties), true);
        } catch (OpenLRProcessingException e) {
            fail("failed to load valid properties file", e);
        } catch (FileNotFoundException e) {
            fail("cannot find properties file", e);
        }
    }

    /**
     * Tests the proper instantiation of the properties values.
     */
    @Test
    public final void testDecoderPropertiesAccess() {
        File validProperties = new File(
                "src/test/resources/OpenLR-Decoder-Properties.xml");
        if (!validProperties.exists()) {
            fail("missing resources");
        }
        Configuration prop = null;
        try {
            prop = OpenLRPropertiesReader.loadPropertiesFromStream(
                    new FileInputStream(validProperties), true);
        } catch (OpenLRProcessingException e) {
            fail("failed to load valid properties file", e);
        } catch (FileNotFoundException e) {
            fail("cannot find properties file", e);
        }
        try {
            assertEquals(OpenLRPropertyAccess.getIntegerPropertyValue(prop,
                    OpenLRDecoderProperty.BEAR_DIST), BEARING_DISTANCE);
            assertEquals(OpenLRPropertyAccess.getIntegerPropertyValue(prop,
                    OpenLRDecoderProperty.MAX_NODE_DIST), MAX_NODE_DISTANCE);
            assertEquals(OpenLRPropertyAccess.getIntegerPropertyValue(prop,
                    OpenLRDecoderProperty.MIN_ACC_RATING), MIN_ACCEPTED_RATING);
            assertEquals(OpenLRPropertyAccess.getIntegerPropertyValue(prop,
                    OpenLRDecoderProperty.FRC_VARIANCE), FRC_VARIANCE);
            assertEquals(OpenLRPropertyAccess.getIntegerPropertyValue(prop,
                    OpenLRDecoderProperty.NODE_FACTOR), NODE_FACTOR);
            assertEquals(OpenLRPropertyAccess.getIntegerPropertyValue(prop,
                    OpenLRDecoderProperty.LINE_FACTOR), LINE_FACTOR);
            assertEquals(OpenLRPropertyAccess.getFloatPropertyValue(prop,
                    OpenLRDecoderProperty.SAME_LINE_DEGRAD),
                    SAME_LINE_DEGRADIATION);

            assertEquals(OpenLRPropertyAccess.getIntegerPropertyValueFromMap(
                    prop, OpenLRDecoderProperty.BEAR_INTERVALS,
                    RatingCategory.EXCELLENT.getIdentifier()),
                    BEARING_INTERVAL_6);
            assertEquals(OpenLRPropertyAccess.getIntegerPropertyValueFromMap(
                    prop, OpenLRDecoderProperty.BEAR_INTERVALS,
                    RatingCategory.GOOD.getIdentifier()), BEARING_INTERVAL_12);
            assertEquals(OpenLRPropertyAccess.getIntegerPropertyValueFromMap(
                    prop, OpenLRDecoderProperty.BEAR_INTERVALS,
                    RatingCategory.AVERAGE.getIdentifier()),
                    BEARING_INTERVAL_18);
//			assertEquals(OpenLRPropertyAccess.getIntegerPropertyValueFromMap(
//					prop, OpenLRDecoderProperty.BEAR_INTERVALS,
//					RatingCategory.POOR.getIdentifier()), BEARING_INTERVAL_18);

            assertEquals(OpenLRPropertyAccess.getIntegerPropertyValueFromMap(
                    prop, OpenLRDecoderProperty.FRC_INTERVALS,
                    RatingCategory.EXCELLENT.getIdentifier()), 0);
            assertEquals(OpenLRPropertyAccess.getIntegerPropertyValueFromMap(
                    prop, OpenLRDecoderProperty.FRC_INTERVALS,
                    RatingCategory.GOOD.getIdentifier()), 1);
            assertEquals(OpenLRPropertyAccess.getIntegerPropertyValueFromMap(
                    prop, OpenLRDecoderProperty.FRC_INTERVALS,
                    RatingCategory.AVERAGE.getIdentifier()), 2);
//			assertEquals(OpenLRPropertyAccess.getIntegerPropertyValueFromMap(
//					prop, OpenLRDecoderProperty.FRC_INTERVALS,
//					RatingCategory.POOR.getIdentifier()), 2);

            assertEquals(OpenLRPropertyAccess.getIntegerPropertyValueFromMap(
                    prop, OpenLRDecoderProperty.FRC_RATING,
                    RatingCategory.EXCELLENT.getIdentifier()), RATING_100);
            assertEquals(OpenLRPropertyAccess.getIntegerPropertyValueFromMap(
                    prop, OpenLRDecoderProperty.FRC_RATING,
                    RatingCategory.GOOD.getIdentifier()), RATING_75);
            assertEquals(OpenLRPropertyAccess.getIntegerPropertyValueFromMap(
                    prop, OpenLRDecoderProperty.FRC_RATING,
                    RatingCategory.AVERAGE.getIdentifier()), RATING_50);
            assertEquals(OpenLRPropertyAccess.getIntegerPropertyValueFromMap(
                    prop, OpenLRDecoderProperty.FRC_RATING,
                    RatingCategory.POOR.getIdentifier()), RATING_0);

            assertEquals(OpenLRPropertyAccess.getIntegerPropertyValueFromMap(
                    prop, OpenLRDecoderProperty.FOW_RATING,
                    RatingCategory.EXCELLENT.getIdentifier()), RATING_100);
            assertEquals(OpenLRPropertyAccess.getIntegerPropertyValueFromMap(
                    prop, OpenLRDecoderProperty.FOW_RATING,
                    RatingCategory.GOOD.getIdentifier()), RATING_50);
            assertEquals(OpenLRPropertyAccess.getIntegerPropertyValueFromMap(
                    prop, OpenLRDecoderProperty.FOW_RATING,
                    RatingCategory.AVERAGE.getIdentifier()), RATING_50);
            assertEquals(OpenLRPropertyAccess.getIntegerPropertyValueFromMap(
                    prop, OpenLRDecoderProperty.FOW_RATING,
                    RatingCategory.POOR.getIdentifier()), RATING_25);

            assertEquals(OpenLRPropertyAccess.getIntegerPropertyValueFromMap(
                    prop, OpenLRDecoderProperty.BEAR_RATING,
                    RatingCategory.EXCELLENT.getIdentifier()), RATING_100);
            assertEquals(OpenLRPropertyAccess.getIntegerPropertyValueFromMap(
                    prop, OpenLRDecoderProperty.BEAR_RATING,
                    RatingCategory.GOOD.getIdentifier()), RATING_50);
            assertEquals(OpenLRPropertyAccess.getIntegerPropertyValueFromMap(
                    prop, OpenLRDecoderProperty.BEAR_RATING,
                    RatingCategory.AVERAGE.getIdentifier()), RATING_25);
            assertEquals(OpenLRPropertyAccess.getIntegerPropertyValueFromMap(
                    prop, OpenLRDecoderProperty.BEAR_RATING,
                    RatingCategory.POOR.getIdentifier()), RATING_0);

        } catch (OpenLRProcessingException e) {
            fail("reading properties failed", e);
        }
    }
}
