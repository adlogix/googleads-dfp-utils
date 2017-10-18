package eu.adlogix.com.google.api.ads.dfp.domain;

public enum DfpVersion {

	V_201702("v201702"), V_201705("v201705"), V_201708("v201708");

	String versionName;

	DfpVersion(String versionName) {
		this.versionName = versionName;
	}

	public String getVersionName() {
		return versionName;
	}

}
