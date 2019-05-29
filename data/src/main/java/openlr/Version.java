package openlr;

public class Version {

	/** the major version */
	private final String majorVersion;

	/** the minor version */
	private final String minorVersion;

	/** the patch version */
	private final String patchVersion;

	/**
	 * Creates a new version object.
	 * 
	 * @param majVersion
	 *            major version
	 * @param minVersion
	 *            minor version
	 * @param patVersion
	 *            patch version
	 */
	Version(final String majVersion, final String minVersion,
			final String patVersion) {
		majorVersion = majVersion;
		minorVersion = minVersion;
		patchVersion = patVersion;
	}

	/**
	 * Creates a version object with unknown version information.
	 */
	Version() {
		majorVersion = VersionHelper.UNKNOWN_VERSION;
		minorVersion = VersionHelper.UNKNOWN_VERSION;
		patchVersion = VersionHelper.UNKNOWN_VERSION;
	}

	/**
	 * Gets the major version.
	 * 
	 * @return major version
	 */
	public final String getMajorVersion() {
		return majorVersion;
	}

	/**
	 * Gets the minor version.
	 * 
	 * @return minor version
	 */
	public final String getMinorVersion() {
		return minorVersion;
	}

	/**
	 * Gets the patch version.
	 * 
	 * @return patch version
	 */
	public final String getPatchVersion() {
		return patchVersion;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final String toString() {
		if (majorVersion == VersionHelper.UNKNOWN_VERSION
				&& minorVersion == VersionHelper.UNKNOWN_VERSION
				&& patchVersion == VersionHelper.UNKNOWN_VERSION) {
			return VersionHelper.UNKNOWN_VERSION;
		}
		StringBuilder sb = new StringBuilder();
		sb.append(majorVersion).append(".");
		sb.append(minorVersion).append(".");
		sb.append(patchVersion);
		return sb.toString();
	}

}
