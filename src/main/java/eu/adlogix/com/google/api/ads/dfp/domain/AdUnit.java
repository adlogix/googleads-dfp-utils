package eu.adlogix.com.google.api.ads.dfp.domain;

import lombok.Getter;
import eu.adlogix.com.google.api.ads.dfp.util.DfpClassUtil;

/**
 * @param <T>- DFP AdUnit
 * 
 */
public class AdUnit<T> {

	@Getter
	T dfpAdunit;

	public AdUnit(T dfpAdunit) {

		this.dfpAdunit = dfpAdunit;

		try {
			String className = dfpAdunit.getClass().getName();

			if (!(DfpClassUtil.isValidDfpPackage(className) && className.endsWith("AdUnit"))) {
				throw new RuntimeException("The parameter provided is not an object of AdUnit");
			}

		} catch (SecurityException e) {
			throw new RuntimeException(e);
		}
	}

}
