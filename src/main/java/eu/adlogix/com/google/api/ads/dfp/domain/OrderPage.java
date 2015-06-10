package eu.adlogix.com.google.api.ads.dfp.domain;

import static org.joor.Reflect.on;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import eu.adlogix.com.google.api.ads.dfp.util.DfpClassUtil;

/**
 * @param <T>- DFP OrderPage
 * @param <U>- DFP Order
 * 
 */
public class OrderPage<T, O> {

	@Getter
	T dfpOrderPage;

	public OrderPage(T dfpOrderPage) {

		this.dfpOrderPage = dfpOrderPage;

		try {
			String className = dfpOrderPage.getClass().getName();

			if (!(DfpClassUtil.isValidDfpPackage(className) && className.endsWith("OrderPage"))) {
				throw new RuntimeException("The parameter provided is not an object of OrderPage");
			}

		} catch (SecurityException e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public Order<O>[] getResults() {

		List<Order<O>> orders = new ArrayList<Order<O>>();

		try {

			O[] dfpOrders = on(this.dfpOrderPage).call("getResults").get();

			if (dfpOrders != null) {
				for (O dfpOrder : dfpOrders) {
					orders.add(new Order<O>(dfpOrder));
				}
			}

		} catch (Exception e) {
			throw new RuntimeException("Error in calling method:getResults in class:"
					+ this.dfpOrderPage.getClass().getName(), e);
		}
		return orders.toArray(new Order[orders.size()]);
	}

	public Integer getTotalResultSetSize() {

		try {
			return on(this.dfpOrderPage).call("getTotalResultSetSize").get();

		} catch (Exception e) {
			throw new RuntimeException("Error in calling method:getTotalResultSetSize in class:"
					+ this.dfpOrderPage.getClass().getName(), e);

		}

	}

}
