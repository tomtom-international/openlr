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
package openlr.map.utils;

import java.util.Iterator;


/**
 * The IteratorHelper provides helper methods for iterators.
 *
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 *
 * @author TomTom International B.V.
 */
public final class IteratorHelper {

    /**
     * Helper classes cannot be instantiated.
     */
    private IteratorHelper() {
        throw new UnsupportedOperationException();
    }

    /**
     * Checks if an element item is contained in the iterator sequence.
     *
     * The iterator cannot be re-used after calling this function!!
     *
     * @param <E> the element type
     * @param iter the iterator
     * @param item the item
     * @return true, if the element is in the iterator sequence
     */
    public static <E> boolean contains(final Iterator<? extends E> iter, final E item) {
        if (iter == null || item == null) {
            throw new NullPointerException();
        }
        while (iter.hasNext()) {
            if (item.equals(iter.next())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the number of elements in the iterator sequence.
     *
     * The iterator cannot be re-used after calling this function!!
     *
     * @param <E> the element type
     * @param iter the iterator
     * @return the number of elements
     */
    public static <E> int size(final Iterator<E> iter) {
        if (iter == null) {
            throw new NullPointerException();
        }
        int count = 0;
        while (iter.hasNext()) {
            iter.next();
            count++;
        }
        return count;
    }
}
