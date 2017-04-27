package eu.adlogix.com.google.api.ads.dfp.domain;

public enum DfpVersion {

	V_201608("v201608"), V_201611("v201611"), V_201702("v201702");

	String versionName;

	DfpVersion(String versionName) {
		this.versionName = versionName;
	}

	public String getVersionName() {
		return versionName;
	}

}
