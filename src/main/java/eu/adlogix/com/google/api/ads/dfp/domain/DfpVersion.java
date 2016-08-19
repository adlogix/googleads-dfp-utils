package eu.adlogix.com.google.api.ads.dfp.domain;

public enum DfpVersion {

	V_201505("v201505"), V_201511("v201511"), V_201605("v201605");

	String versionName;

	DfpVersion(String versionName) {
		this.versionName = versionName;
	}

	public String getVersionName() {
		return versionName;
	}

}
