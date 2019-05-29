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
package openlr.decoder;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;

import openlr.map.MapDatabase;
import openlr.map.loader.MapLoadParameter;
import openlr.map.loader.OpenLRMapLoaderException;
import openlr.map.sqlite.loader.DBFileNameParameter;
import openlr.map.sqlite.loader.SQLiteMapLoader;

import org.testng.Assert;

/**
 * Provides access to the test map 
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 * 
 * @author TomTom International B.V.
 */
public final class TestMap {
    
    /**
     * disabled constructor
     */
    private TestMap() {
    }
    

	/** The Constant MAP_DB. */
	private static final String MAP_DB = "decoder-test-db.sqlite";

	/**
	 * Gets the test map database.
	 * 
	 * @return the test map database
	 */
	public static MapDatabase getTestMapDatabase() {
		SQLiteMapLoader sqliteLoader = new SQLiteMapLoader();
		String path = null;
		try {
			Enumeration<URL> files = TestMap.class.getClassLoader()
					.getResources(MAP_DB);

			while (files.hasMoreElements()) {
				URL url = files.nextElement();
				path = url.getPath();
				break;
			}
		} catch (IOException e) {
			Assert.fail("Unexpected exception", e);
		}
		if (path == null) {
			Assert.fail("Cannot find test map database");
		}
		DBFileNameParameter param = new DBFileNameParameter();
		param.setValue(path);
		Collection<MapLoadParameter> params = new ArrayList<MapLoadParameter>();
		params.add(param);
		MapDatabase mdb = null;
		try {
			mdb = sqliteLoader.load(params);
		} catch (OpenLRMapLoaderException e) {
			Assert.fail("Cannot load test map database", e);
		}
		return mdb;
	}

}
