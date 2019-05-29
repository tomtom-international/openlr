/**
 * Licensed to the TomTom International B.V. under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  TomTom International B.V.
 * licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
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
package openlr.binary;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * The class ByteArray represents an array of bytes.
 * 
 * <p>
 * OpenLR is a trade mark of TomTom International B.V.
 * <p>
 * email: software@openlr.org
 * 
 * @author TomTom International B.V.
 */
public class ByteArray {

	/** The Constant MAX_BYTE_VALUE. */
	private static final int MAX_BYTE_VALUE = 256;

	/** The bytes. */
	private byte[] bytes;

	/**
	 * Instantiates a new byte array from the input stream.
	 *
	 * @param stream the input stream
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public ByteArray(final InputStream stream) throws IOException {
		List<Byte> byteList = new ArrayList<Byte>();
		int b = stream.read();
		while (b >= 0) {
			byteList.add(Byte.valueOf((byte) b));
			b = stream.read();
		}
		bytes = new byte[byteList.size()];
		for (int i = 0; i < byteList.size(); i++) {
			bytes[i] = byteList.get(i).byteValue();
		}
	}

	/**
	 * Instantiates a new byte array with b.
	 * 
	 * @param b
	 *            the bytes
	 */
	public ByteArray(final byte[] b) {
		bytes = b.clone();
	}

	/**
	 * Instantiates a new byte array from a base64-encoded string.
	 * 
	 * @param b64String
	 *            the b64 string
	 */
	public ByteArray(final String b64String) {
		bytes = Base64.decodeBase64(b64String);
	}
	
	/**
	 * Gets the byte at position pos.
	 * 
	 * @param pos
	 *            the position
	 * 
	 * @return the byte at position pos
	 */
	public final byte get(final int pos) {
		if (pos < 0 || pos > bytes.length - 1) {
			throw new ArrayIndexOutOfBoundsException();
		}
		return bytes[pos];
	}

	/**
	 * Gets the data.
	 * 
	 * @return the data
	 */
	public final byte[] getData() {
		return bytes.clone();
	}
	
	/**
	 * Gets the base64 data (RFC 2045).
	 *
	 * @return the base64 data
	 */
	public final String getBase64Data() {
		return new String(Base64.encodeBase64(bytes), Charset.forName("UTF-8"));
	}
	
	/**
	 * Gets the base64 data.
	 *
	 * @return the base64 data
	 */
	public final String getBase64DataUrlSafe() {
		return new String(Base64.encodeBase64URLSafe(bytes), Charset.forName("UTF-8"));
	}

	/**
	 * Gets the size of the byte array.
	 * 
	 * @return the size of the byte array
	 */
	public final int size() {
		return bytes.length;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final String toString() {
		StringBuilder sb = new StringBuilder();
		int nrBytes = bytes.length;
		sb.append(nrBytes).append(" bytes [");
		for (int i = 0; i < nrBytes; i++) {
			sb.append(hexValue(bytes[i]));
			if (i != nrBytes - 1) {
				sb.append(",");
			}
		}
		sb.append("]");
		return sb.toString();
	}

	/**
	 * Returns a hex value string representing the byte b.
	 * 
	 * @param b
	 *            the byte value
	 * @return the hex value string
	 */
	private String hexValue(final byte b) {
		int tempData = b;
		if (tempData < 0) {
			tempData += MAX_BYTE_VALUE;
		}
		String hexval = Integer.toHexString(tempData);
		StringBuilder sb = new StringBuilder();
		sb.append("0x");
		if (hexval.length() == 1) {
			sb.append("0");
		}
		sb.append(hexval);
		return sb.toString();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder();
		builder.append(bytes);
		return builder.toHashCode();
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public final boolean equals(final Object obj) {
		if (!(obj instanceof ByteArray)) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		ByteArray other = (ByteArray) obj;
		EqualsBuilder builder = new EqualsBuilder();
		builder.append(bytes, other.bytes);
		return builder.isEquals();
	}

}
