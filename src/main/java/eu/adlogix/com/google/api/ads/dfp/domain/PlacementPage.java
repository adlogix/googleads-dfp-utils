package eu.adlogix.com.google.api.ads.dfp.domain;

import static org.joor.Reflect.on;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import eu.adlogix.com.google.api.ads.dfp.util.DfpClassUtil;

/**
 * @param <T>- DFP PlacementPage
 * @param <U>- DFP Placement
 * 
 */
public class PlacementPage<T, P> {

	@Getter
	T dfpPlacementPage;

	public PlacementPage(T dfpPlacementPage) {

		this.dfpPlacementPage = dfpPlacementPage;

		try {
			String className = dfpPlacementPage.getClass().getName();

			if (!(DfpClassUtil.isValidDfpPackage(className) && className.endsWith("PlacementPage"))) {
				throw new RuntimeException("The parameter provided is not an object of PlacementPage");
			}

		} catch (SecurityException e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public Placement<P>[] getResults() {

		List<Placement<P>> placements = new ArrayList<Placement<P>>();

		try {

			P[] dfpPlacements = on(this.dfpPlacementPage).call("getResults").get();

			if (dfpPlacements != null) {
				for (P dfpPlacement : dfpPlacements) {
					placements.add(new Placement<P>(dfpPlacement));
				}
			}

		} catch (Exception e) {

			throw new RuntimeException("Error in calling method:getResults in class:"
					+ this.dfpPlacementPage.getClass().getName(), e);
		}
		return placements.toArray(new Placement[placements.size()]);
	}

	public Integer getTotalResultSetSize() {

		try {

			return on(this.dfpPlacementPage).call("getTotalResultSetSize").get();

		} catch (Exception e) {

			throw new RuntimeException("Error in calling method:getTotalResultSetSize in class:"
					+ this.dfpPlacementPage.getClass().getName(), e);

		}

	}

}
