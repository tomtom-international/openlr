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
package openlr.encoder.properties;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import openlr.OpenLRProcessingException;
import openlr.properties.OpenLRPropertiesReader;
import openlr.properties.OpenLRPropertyAccess;
import openlr.properties.OpenLRPropertyException.PropertyError;

import org.apache.commons.configuration.Configuration;
import org.testng.annotations.Test;

public class EncoderPropertiesTest {
	
	@Test
	public void testEncoderPropertiesRead(){
		File validProperties = new File("src/test/resources/OpenLR-Encoder-Properties.xml");
		File invalidProperties = new File("src/test/resources/OpenLR-Encoder-Properties-invalid.xml");
		if (!validProperties.exists() || !invalidProperties.exists()){
			fail("missing resources");
		}
		try {
			OpenLRPropertiesReader.loadPropertiesFromStream(new FileInputStream(validProperties), true);
		} catch (OpenLRProcessingException e) {
			fail("failed to load valid properties file", e);
		} catch (FileNotFoundException e) {
			fail("cannot find properties", e);
		}
		try {
			OpenLRPropertiesReader.loadPropertiesFromStream(new FileInputStream(invalidProperties), true);
		} catch (OpenLRProcessingException e) {
			assertEquals(e.getErrorCode(), PropertyError.READING_PROPERTIES_ERROR);			
		}catch (FileNotFoundException e) {
			fail("cannot find properties", e);
		}
		
	}

	@Test
	public void testEncoderPropertiesAccess(){
		File validProperties = new File("src/test/resources/OpenLR-Encoder-Properties.xml");
		if (!validProperties.exists()){
			fail("missing resources");
		}
		Configuration prop = null;
		try {
			prop = OpenLRPropertiesReader.loadPropertiesFromStream(new FileInputStream(validProperties), true);
		} catch (OpenLRProcessingException e) {
			fail("failed to load valid properties file", e);
		}catch (FileNotFoundException e) {
			fail("cannot find properties", e);
		}
		try{
			assertEquals(OpenLRPropertyAccess.getIntegerPropertyValue(prop, OpenLREncoderProperty.BEAR_DIST), 20);
			assertEquals(OpenLRPropertyAccess.getIntegerPropertyValue(prop, OpenLREncoderProperty.MAX_DIST_LRP), 15000);
			assertEquals(OpenLRPropertyAccess.getBooleanPropertyValue(prop, OpenLREncoderProperty.TURN_RESTRICTION_CHECK), false);
			assertEquals(OpenLRPropertyAccess.getIntegerPropertyValueById(prop, OpenLREncoderProperty.PHYSICAL_FORMAT_VERSION, "xml"), 2);
			assertEquals(OpenLRPropertyAccess.getIntegerPropertyValueById(prop, OpenLREncoderProperty.PHYSICAL_FORMAT_VERSION, "binary"), 3);
			assertEquals(OpenLRPropertyAccess.getIntegerPropertyValueById(prop, OpenLREncoderProperty.PHYSICAL_FORMAT_VERSION, "fklsfl"), -1);
		}
		catch(OpenLRProcessingException e){
			fail("reading properties failed", e);
		}
	}
}
