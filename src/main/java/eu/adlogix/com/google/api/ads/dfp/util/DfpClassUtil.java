package eu.adlogix.com.google.api.ads.dfp.util;

import eu.adlogix.com.google.api.ads.dfp.domain.DfpVersion;


public class DfpClassUtil {
	
	public final static String DFP_PACKAGE_PREFIX = "com.google.api.ads.dfp";

	public static boolean isValidDfpPackage(String packageName) {

		for (DfpVersion version : DfpVersion.values()) {
			if (packageName.startsWith(DFP_PACKAGE_PREFIX) && packageName.contains(version.getVersionName())) {
				return true;
			}
		}
		return false;

	}
}
