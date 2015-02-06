package eu.adlogix.com.google.api.ads.dfp.service;

import static org.joor.Reflect.on;
import lombok.Getter;
import eu.adlogix.com.google.api.ads.dfp.domain.PlacementPage;
import eu.adlogix.com.google.api.ads.dfp.domain.Statement;

public class PlacementService<I> {

	@Getter
	I placementService;

	public PlacementService(I placementService) {

		this.placementService = placementService;
	}

	public <P, U, S> PlacementPage<P, U> getPlacementsByStatement(Statement<S> statement) {

		try {
			P returnValue = on(this.placementService).call("getPlacementsByStatement", statement.getDfpStatement())
					.get();

			return new PlacementPage<P, U>(returnValue);

		} catch (Exception e) {
			throw new RuntimeException("Error in calling method:getPlacementsByStatement in class:"
					+ this.placementService.getClass().getName(), e);
		}
	}
}
