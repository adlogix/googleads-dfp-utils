package eu.adlogix.com.google.api.ads.dfp.domain;

public enum DfpVersion {

	V_201708("v201708"), V_201711("v201711"), V_201802("v201802"), V_201805("v201805");

	String versionName;

	DfpVersion(String versionName) {
		this.versionName = versionName;
	}

	public String getVersionName() {
		return versionName;
	}

}
