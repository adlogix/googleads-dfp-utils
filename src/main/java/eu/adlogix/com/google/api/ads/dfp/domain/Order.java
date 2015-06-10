package eu.adlogix.com.google.api.ads.dfp.domain;

import lombok.Getter;
import eu.adlogix.com.google.api.ads.dfp.util.DfpClassUtil;

/**
 * @param <T>- DFP Order
 * 
 */
public class Order<T> {

	@Getter
	T dfpOrder;

	public Order(T dfpOrder) {

		this.dfpOrder = dfpOrder;

		try {
			String className = dfpOrder.getClass().getName();

			if (!(DfpClassUtil.isValidDfpPackage(className) && className.endsWith("Order"))) {
				throw new RuntimeException("The parameter provided is not an object of Order");
			}

		} catch (SecurityException e) {
			throw new RuntimeException(e);
		}
	}

}
