package eu.adlogix.com.google.api.ads.dfp.domain;

import lombok.Getter;
import eu.adlogix.com.google.api.ads.dfp.util.DfpClassUtil;

/**
 * @param <T>- DFP LineItem
 * 
 */
public class LineItem<T> {

	@Getter
	T dfpLineItem;

	public LineItem(T dfpLineItem) {

		this.dfpLineItem = dfpLineItem;

		try {
			String className = dfpLineItem.getClass().getName();

			if (!(DfpClassUtil.isValidDfpPackage(className) && className.endsWith("LineItem"))) {
				throw new RuntimeException("The parameter provided is not an object of LineItem");
			}

		} catch (SecurityException e) {
			throw new RuntimeException(e);
		}
	}

}
