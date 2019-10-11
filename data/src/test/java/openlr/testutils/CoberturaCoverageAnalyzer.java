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
package openlr.testutils;

import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This utility class processes Cobertura test coverage reports of the OpenLR
 * packages for insufficient coverage of specific methods. At this point in time
 * these are equals, hashCode and toString. <br>
 * The usage process is the following:<br>
 * <ul>
 * <li>
 * Run "mvn -P development clean cobertura:cobertura" in the parent package or a 
 * desired sub-package.</li>
 * <li>
 * Run this tool. It will print out line numbers inside the coverage XMLs that
 * refer to a reported insufficient test coverage of the specific method.</li>
 * <li>
 * Go to the reported line of a insufficient coverage detection an scroll up to
 * the enclosing class to find out what class is affected.</li>
 * <li>
 * Consult the Cobertura output by visiting openlr/&lt;OpenLR
 * package&gt;/target/site/cobertura/index.html in your browser to find out
 * what's wrong if the coverage is not entirely missing (0.0)</li>
 * <li>
 * Write a test that covers this class and method!</li>
 * </ul>
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
final class CoberturaCoverageAnalyzer {

    /**
     * The analyzed OpenLR packages.
     */
    private static final String[] ANALYZED_PACKAGES = new String[]{"data",
            "map", "encoder", "decoder", "xml", "binary", "datex2"};

    /**
     * The base regular expressing pattern that matches line-rate attributes of
     * a Cobertura coverage report that contain a ratio starting with zero (i.e.
     * values below 1.0). It it defines a regex group (via brackets) that can be
     * accessed by the user of the pattern to retrieve the matched string.
     */
    private static final String PATTERN_BASE_LINE_RATE = "(line-rate=\"0\\.[0-9]+\")";

    /**
     * The relative path to the Cobertura coverage report.
     */
    private static final String PATH_TO_COVERAGE_REPORT = "target/site/cobertura/coverage.xml";

    /**
     * Disabled constructor.
     */
    private CoberturaCoverageAnalyzer() {
        throw new UnsupportedOperationException();
    }

    /**
     * Runs a check over all OpenLR packages for insufficient test coverage of
     * methods toString, hashCode and equals. <br>
     *
     * @param args
     *            Is ignored.
     */
    public static void main(final String[] args) {

        int result = 0;

        for (String pack : ANALYZED_PACKAGES) {
            if (!checkCoverageForPackage(pack)) {
                result = -1;
            }
        }

        System.out.println("Done, result: " + result);
    }

    /**
     * Checks the test coverage of the relevant methods for the given OpenLR
     * package.
     *
     * @param pack
     *            The package to check.
     * @return <code>true</code> if no insufficient coverage was found,
     *         otherwise <code>false</code>.
     */
    private static boolean checkCoverageForPackage(final String pack) {

        boolean coverageOk = true;
        File coverageReport = new File("../" + pack + "/"
                + PATH_TO_COVERAGE_REPORT);

        System.out.println("===============\nChecking package \"" + pack
                + "\" (file: " + coverageReport.getAbsolutePath()
                + ")\n===============");
        if (coverageReport.exists()) {

            try {
                BufferedReader reader = new BufferedReader(new FileReader(
                        coverageReport));
                String line;
                int lineCount = 1;
                Matcher matcher;
                while ((line = reader.readLine()) != null) {
                    for (AnalyzedMethods method : AnalyzedMethods.getMethods()) {
                        matcher = method.pattern.matcher(line);
                        if (matcher.find()) {
                            coverageOk = false;
                            System.out.println("line " + lineCount + " "
                                    + method.methodName + ": "
                                    + matcher.group(1));
                        }

                    }
                    lineCount++;
                }

            } catch (FileNotFoundException e) {
                System.out.println("ERROR: " + e.getMessage());
            } catch (IOException e) {
                System.out.println("ERROR: " + e.getMessage());
            }

            if (coverageOk) {
                System.out.println("Coverage OK!");
            }
        } else {
            System.out.println("No cobertura coverage report found! ("
                    + coverageReport + ")");
            System.out
                    .println("You should run mvn cobertura:cobertura inside this package!");
        }

        return coverageOk;
    }

    /**
     * Defines setup data for methods that should be checked for insufficient
     * test coverage.
     */
    private enum AnalyzedMethods {

        /**
         * The pattern that searches insufficient coverage of equals methods in
         * Cobertura reports
         */
        EQUALS(Pattern.compile("\"equals\".*" + PATTERN_BASE_LINE_RATE),
                "equals"),

        /**
         * The pattern that searches insufficient coverage of hashCode methods
         * in Cobertura reports
         */
        HASH_CODE(Pattern.compile("\"hashCode\".*" + PATTERN_BASE_LINE_RATE),
                "hashCode"),

        /**
         * The pattern that searches insufficient coverage of toString methods
         * in Cobertura reports
         */
        TO_STRING(Pattern.compile("\"toString\".*" + PATTERN_BASE_LINE_RATE),
                "toString");

        /** The Constant VALUES. */
        private static final List<AnalyzedMethods> VALUES = Collections
                .unmodifiableList(Arrays.asList(values()));
        /** The regex pattern. */
        private Pattern pattern;
        /**
         * The method name.
         */
        private String methodName;

        /**
         * Creates a new instance.
         *
         * @param pat
         *            The regex pattern.
         * @param name
         *            The method name.
         */
        private AnalyzedMethods(final Pattern pat, final String name) {
            pattern = pat;
            methodName = name;
        }

        /**
         * Delivers all configured method types.
         *
         * @return all configured method types.
         */
        public static List<AnalyzedMethods> getMethods() {
            return VALUES;
        }
    }
}
