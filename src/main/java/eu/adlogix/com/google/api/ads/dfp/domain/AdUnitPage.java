package eu.adlogix.com.google.api.ads.dfp.domain;

import static org.joor.Reflect.on;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import eu.adlogix.com.google.api.ads.dfp.util.DfpClassUtil;

/**
 * @param <T>- DFP AdUnitPage
 * @param <U>- DFP AdUnit
 * 
 */
public class AdUnitPage<T, U> {

	@Getter
	T dfpAdUnitPage;

	public AdUnitPage(T dfpAdUnitPage) {

		this.dfpAdUnitPage = dfpAdUnitPage;

		try {
			String className = dfpAdUnitPage.getClass().getName();

			if (!(DfpClassUtil.isValidDfpPackage(className) && className.endsWith("AdUnitPage"))) {
				throw new RuntimeException("The parameter provided is not an object of AdUnitPage");
			}

		} catch (SecurityException e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public AdUnit<U>[] getResults() {

		List<AdUnit<U>> adUnits = new ArrayList<AdUnit<U>>();

		try {

			U[] dfpAdUnits = on(this.dfpAdUnitPage).call("getResults").get();

			if (dfpAdUnits != null) {
				for (U dfpAdunit : dfpAdUnits) {
					adUnits.add(new AdUnit<U>(dfpAdunit));
				}
			}

		} catch (Exception e) {
			throw new RuntimeException("Error in calling method:getResults in class:"
					+ this.dfpAdUnitPage.getClass().getName(), e);
		}
		return adUnits.toArray(new AdUnit[adUnits.size()]);
	}


	public Integer getTotalResultSetSize() {

		try {
			return on(this.dfpAdUnitPage).call("getTotalResultSetSize").get();

		} catch (Exception e) {
			throw new RuntimeException("Error in calling method:getTotalResultSetSize in class:"
					+ this.dfpAdUnitPage.getClass().getName(), e);

		}

	}

}
