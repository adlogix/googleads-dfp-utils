package eu.adlogix.com.google.api.ads.dfp.domain;

import static org.joor.Reflect.on;

import java.util.ArrayList;
import java.util.List;

import eu.adlogix.com.google.api.ads.dfp.util.DfpClassUtil;

/**
 * @param <T>- DFP LineItemPage
 * @param <I>- DFP LineItem
 * 
 */
public class LineItemPage<T, I> {

	T dfpLineItemPage;

	public LineItemPage(T dfpLineItemPage) {

		this.dfpLineItemPage = dfpLineItemPage;

		try {
			String className = dfpLineItemPage.getClass().getName();

			if (!(DfpClassUtil.isValidDfpPackage(className) && className.endsWith("LineItemPage"))) {
				throw new RuntimeException("The parameter provided is not an object of LineItemPage");
			}

		} catch (SecurityException e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public LineItem<I>[] getResults() {

		List<LineItem<I>> lineItems = new ArrayList<LineItem<I>>();

		try {

			I[] dfpLineItems = on(this.dfpLineItemPage).call("getResults").get();

			if (dfpLineItems != null) {
				for (I dfpLineItem : dfpLineItems) {
					lineItems.add(new LineItem<I>(dfpLineItem));
				}
			}

		} catch (Exception e) {
			throw new RuntimeException("Error in calling method:getResults in class:"
					+ this.dfpLineItemPage.getClass().getName(), e);
		}
		return lineItems.toArray(new LineItem[lineItems.size()]);
	}

	public Integer getTotalResultSetSize() {

		try {
			return on(this.dfpLineItemPage).call("getTotalResultSetSize").get();

		} catch (Exception e) {
			throw new RuntimeException("Error in calling method:getTotalResultSetSize in class:"
					+ this.dfpLineItemPage.getClass().getName(), e);

		}

	}
}
