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
package openlr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public final class VersionHelper {

    static final String UNKNOWN_VERSION = "unknown";
    private static final String VERSION_FILE = "VERSION";

    public static Version getVersion(final String coderType) {
        InputStream is = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(coderType + "/" + VERSION_FILE);
        if (is == null) {
            return new Version();
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String version;
        try {
            version = br.readLine();
            if (version == null) {
                return new Version();
            }
            return new Version(resolveMajorVersion(version),
                    resolveMinorVersion(version), resolvePatchVersion(version));
        } catch (IOException e) {
            return new Version();
        }
    }

    private static String resolveMajorVersion(final String versionString) {
        if (versionString == null) {
            return UNKNOWN_VERSION;
        }
        String[] splitted = versionString.split("\\.");
        return splitted[0];
    }

    private static String resolveMinorVersion(final String versionString) {
        if (versionString == null) {
            return UNKNOWN_VERSION;
        }
        String[] splitted = versionString.split("\\.");
        if (splitted.length > 1) {
            return splitted[1];
        }
        return UNKNOWN_VERSION;
    }

    private static String resolvePatchVersion(final String versionString) {
        if (versionString == null) {
            return UNKNOWN_VERSION;
        }
        String[] splitted = versionString.split("\\.");
        if (splitted.length > 2) {
            return splitted[2];
        }
        return UNKNOWN_VERSION;
    }

}
