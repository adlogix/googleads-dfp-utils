package eu.adlogix.com.google.api.ads.dfp.domain;

import static org.joor.Reflect.on;
import lombok.Getter;
import eu.adlogix.com.google.api.ads.dfp.util.DfpClassUtil;

/**
 * @param <T>- DFP Network
 * 
 */
public class Network<T> {

	@Getter
	T dfpNetwork;

	public Network(T dfpNetwork) {

		this.dfpNetwork = dfpNetwork;
		try {
			String className = dfpNetwork.getClass().getName();

			if (!(DfpClassUtil.isValidDfpPackage(className) && className.endsWith("Network"))) {
				throw new RuntimeException("The parameter provided is not an object of Network");
			}

		} catch (SecurityException e) {
			throw new RuntimeException(e);
		}
	}

	public String getEffectiveRootAdUnitId() {

		try {
			return on(this.dfpNetwork).call("getEffectiveRootAdUnitId").get();

		} catch (Exception e) {
			throw new RuntimeException("Error in calling method:getEffectiveRootAdUnitId in class:"
					+ this.dfpNetwork.getClass().getName(), e);
		}
	}
}
