package eu.adlogix.com.google.api.ads.dfp.service;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import com.google.api.ads.dfp.lib.client.DfpServiceClient;

import eu.adlogix.com.google.api.ads.dfp.domain.DfpVersion;

public class DfpServiceValidator {

	public final static String DFP_AXIS_PACKAGE_PREFIX = "com.google.api.ads.dfp.axis";

	public static boolean isValidInventoryService(Object inventoryService, DfpVersion version) {
		return isValidService(inventoryService, "InventoryService", version);
	}

	public static boolean isValidNetworkService(Object networkService, DfpVersion version) {
		return isValidService(networkService, "NetworkService", version);
	}

	public static boolean isValidLineItemService(Object lineItemService, DfpVersion version) {
		return isValidService(lineItemService, "LineItemService", version);
	}

	public static boolean isValidOrderService(Object orderService, DfpVersion version) {
		return isValidService(orderService, "OrderService", version);
	}

	public static boolean isValidPlacementService(Object placementService, DfpVersion version) {
		return isValidService(placementService, "PlacementService", version);
	}

	private static boolean isValidService(Object serviceObject, String serviceName, DfpVersion version) {

		boolean isProxy = Proxy.isProxyClass(serviceObject.getClass());

		if (isProxy) {
			InvocationHandler invocationHandler = Proxy.getInvocationHandler(serviceObject);
			Class<?> invocationHandlerClass = invocationHandler.getClass();

			if (invocationHandlerClass.getName().equals(DfpServiceClient.class.getName())) {

				DfpServiceClient client = (DfpServiceClient) invocationHandler;
				String soapClientClassName = client.getSoapClient().getClass().getName();
				String serviceClassPrefix = DFP_AXIS_PACKAGE_PREFIX + "." + version.getVersionName() + "."
						+ serviceName;
				return soapClientClassName.startsWith(serviceClassPrefix);
			}
		}

		return false;

	}

}
