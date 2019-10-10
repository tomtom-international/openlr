package openlr.proto;

import openlr.LocationReference;
import openlr.PhysicalDecoder;
import openlr.PhysicalFormatException;
import openlr.rawLocRef.RawLocationReference;

public class OpenLRProtoDecoder implements PhysicalDecoder {
	@Override
	public Class<?> getDataClass() {
		return null;
	}

	@Override
	public RawLocationReference decodeData(LocationReference data) throws PhysicalFormatException {
		return null;
	}

	@Override
	public String getDataFormatIdentifier() {
		return null;
	}
}
