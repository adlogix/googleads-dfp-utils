package eu.adlogix.com.google.api.ads.dfp.service;

import static org.joor.Reflect.on;
import lombok.Getter;
import eu.adlogix.com.google.api.ads.dfp.domain.LineItemPage;
import eu.adlogix.com.google.api.ads.dfp.domain.Statement;

public class LineItemService<T> {

	@Getter
	T lineItemService;

	public LineItemService(T lineItemService) {
		this.lineItemService = lineItemService;
	}

	public <P, I, S> LineItemPage<P, I> getLineItemsByStatement(Statement<S> filterStatement) {

		try {

			P returnValue = on(this.lineItemService)
					.call("getLineItemsByStatement", filterStatement.getDfpStatement())
					.get();

			return new LineItemPage<P, I>(returnValue);

		} catch (Exception e) {
			throw new RuntimeException("Error in calling method:getLineItemsByStatement in class:"
					+ this.lineItemService.getClass().getName(), e);
		}

	}


}
