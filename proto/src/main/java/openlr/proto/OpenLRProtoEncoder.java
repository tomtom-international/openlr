package openlr.proto;

import openlr.LocationReference;
import openlr.PhysicalEncoder;
import openlr.rawLocRef.RawLocationReference;

public class OpenLRProtoEncoder implements PhysicalEncoder {
	@Override
	public LocationReference encodeData(RawLocationReference rawLocRef) {
		return null;
	}

	@Override
	public LocationReference encodeData(RawLocationReference rawLocRef, int version) {
		return null;
	}

	@Override
	public int[] getSupportedVersions() {
		return new int[0];
	}

	@Override
	public String getDataFormatIdentifier() {
		return null;
	}

	@Override
	public Class<?> getDataClass() {
		return null;
	}
}
