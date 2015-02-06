package eu.adlogix.com.google.api.ads.dfp.domain;

import lombok.Getter;
import eu.adlogix.com.google.api.ads.dfp.util.DfpClassUtil;

/**
 * @param <T>- DFP Placement
 * 
 */
public class Placement<T> {

	@Getter
	T Placement;

	public Placement(T Placement) {

		this.Placement = Placement;

		try {
			String className = Placement.getClass().getName();

			if (!(DfpClassUtil.isValidDfpPackage(className) && className.endsWith("Placement"))) {
				throw new RuntimeException("The parameter provided is not an object of Placement");
			}

		} catch (SecurityException e) {
			throw new RuntimeException(e);
		}
	}

}
