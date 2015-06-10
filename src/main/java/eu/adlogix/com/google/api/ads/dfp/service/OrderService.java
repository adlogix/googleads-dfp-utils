package eu.adlogix.com.google.api.ads.dfp.service;

import static org.joor.Reflect.on;
import lombok.Getter;
import eu.adlogix.com.google.api.ads.dfp.domain.OrderPage;
import eu.adlogix.com.google.api.ads.dfp.domain.Statement;

public class OrderService<T> {

	@Getter
	T orderService;

	public OrderService(T orderService) {

		this.orderService = orderService;
	}

	public <P, O, S> OrderPage<P, O> getOrdersByStatement(Statement<S> statement) {

		try {
			P returnValue = on(this.orderService).call("getOrdersByStatement", statement.getDfpStatement()).get();

			return new OrderPage<P, O>(returnValue);

		} catch (Exception e) {
			throw new RuntimeException("Error in calling method:getOrdersByStatement in class:"
					+ this.orderService.getClass().getName(), e);
		}
	}
}
