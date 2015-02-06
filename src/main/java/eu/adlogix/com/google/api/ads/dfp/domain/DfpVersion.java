package eu.adlogix.com.google.api.ads.dfp.domain;

public enum DfpVersion {

	V_201403("v201403"), V_201405("v201405"), V_201408("v201408"), V_201411("v201411"), V_201502("v201502"), V_201505(
			"v201505");

	String versionName;

	DfpVersion(String versionName) {
		this.versionName = versionName;
	}

	public String getVersionName() {
		return versionName;
	}

}
