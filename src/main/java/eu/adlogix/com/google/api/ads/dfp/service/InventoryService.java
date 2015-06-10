package eu.adlogix.com.google.api.ads.dfp.service;

import static org.joor.Reflect.on;
import lombok.Getter;
import eu.adlogix.com.google.api.ads.dfp.domain.AdUnitPage;
import eu.adlogix.com.google.api.ads.dfp.domain.Statement;

public class InventoryService<T> {

	@Getter
	T inventoryService;

	public InventoryService(T inventoryService) {

		this.inventoryService = inventoryService;
	}

	public <P, U, S> AdUnitPage<P, U> getAdUnitsByStatement(Statement<S> statement) {

		try {
			P returnValue = on(this.inventoryService).call("getAdUnitsByStatement", statement.getDfpStatement()).get();

			return new AdUnitPage<P, U>(returnValue);

		} catch (Exception e) {
			throw new RuntimeException("Error in calling method:getAdUnitsByStatement in class:"
					+ this.inventoryService.getClass().getName(), e);
		}
	}
}
