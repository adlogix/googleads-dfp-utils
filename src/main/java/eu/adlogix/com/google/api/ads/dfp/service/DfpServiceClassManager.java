package eu.adlogix.com.google.api.ads.dfp.service;

import static org.joor.Reflect.on;
import eu.adlogix.com.google.api.ads.dfp.domain.DfpVersion;

public class DfpServiceClassManager {

	public final static String DFP_AXIS_PACKAGE_PREFIX = "com.google.api.ads.dfp.axis";
	public final static String DFP_AXIS_UTILS_PACKAGE_PREFIX = "com.google.api.ads.dfp.axis.utils";

	public Class<?> getInventoryServiceInterface(DfpVersion version) {

		return getClass(DFP_AXIS_PACKAGE_PREFIX, version, "InventoryServiceInterface");
	}

	public Class<?> getNetworkServiceInterface(DfpVersion version) {

		return getClass(DFP_AXIS_PACKAGE_PREFIX, version, "NetworkServiceInterface");
	}

	public Class<?> getStatementBuilder(DfpVersion version) {

		return getClass(DFP_AXIS_UTILS_PACKAGE_PREFIX, version, "StatementBuilder");
	}

	public Class<?> getDateTimes(DfpVersion version) {

		return getClass(DFP_AXIS_UTILS_PACKAGE_PREFIX, version, "DateTimes");
	}

	public Class<?> getLineItemServiceInterface(DfpVersion version) {

		return getClass(DFP_AXIS_PACKAGE_PREFIX, version, "LineItemServiceInterface");
	}

	public Class<?> getPlacementServiceInterface(DfpVersion version) {

		return getClass(DFP_AXIS_PACKAGE_PREFIX, version, "PlacementServiceInterface");
	}

	public Class<?> getOrderServiceInterface(DfpVersion version) {

		return getClass(DFP_AXIS_PACKAGE_PREFIX, version, "OrderServiceInterface");
	}

	private String getFullClasseName(String prefix, DfpVersion dfpVersion, String className) {
		return prefix + "." + dfpVersion.getVersionName() + "." + className;
	}

	private Class<?> getClass(String packageName, DfpVersion version, String className) {
		try {
			String fullClassName = getFullClasseName(packageName, version, className);
			return on(fullClassName).get();

		} catch (Exception e) {
			throw new RuntimeException(className + "not found for version:" + version.getVersionName() + " in package:"
					+ packageName, e);

		}
	}

}
