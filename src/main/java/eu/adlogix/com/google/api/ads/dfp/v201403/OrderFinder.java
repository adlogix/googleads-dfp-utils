package eu.adlogix.com.google.api.ads.dfp.v201403;

import static com.google.common.base.Preconditions.checkNotNull;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import com.google.api.ads.dfp.axis.utils.v201403.StatementBuilder;
import com.google.api.ads.dfp.axis.v201403.ApiException;
import com.google.api.ads.dfp.axis.v201403.Order;
import com.google.api.ads.dfp.axis.v201403.OrderPage;
import com.google.api.ads.dfp.axis.v201403.OrderServiceInterface;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

public class OrderFinder {

	private final OrderServiceInterface orderService;

	public OrderFinder(final OrderServiceInterface orderService) {
		checkNotNull(orderService);
		this.orderService = orderService;
	}

	public Order findById(Long id) {
		return Iterables.getOnlyElement(findByOrderStatementBuilder(new OrderStatementBuilderCreator().withId(id, StatementCondition.EQUAL)));
	}

	public List<Order> findByStatementBuilder(StatementBuilder statementBuilder) {

		statementBuilder.limit(StatementBuilder.SUGGESTED_PAGE_LIMIT);

		int totalResultSetSize = 0;

		List<Order> orders = new ArrayList<Order>();

		try {
			do {
				OrderPage page = orderService.getOrdersByStatement(statementBuilder.toStatement());

				if (page.getResults() != null) {
					totalResultSetSize = page.getTotalResultSetSize();
					orders.addAll(Lists.newArrayList(page.getResults()));
				}

				statementBuilder.increaseOffsetBy(StatementBuilder.SUGGESTED_PAGE_LIMIT);
			} while (statementBuilder.getOffset() < totalResultSetSize);

		} catch (ApiException e) {
			throw new RuntimeException(e);
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}

		return orders;
	}

	private List<Order> findByOrderStatementBuilder(OrderStatementBuilderCreator orderStatementBuilder) {
		return findByLineItemStatementBuilder(orderStatementBuilder, null);

	}

	private List<Order> findByLineItemStatementBuilder(OrderStatementBuilderCreator orderStatementBuilder,
			DateTime lastModifiedDateTime) {
		if (null != lastModifiedDateTime) {
			orderStatementBuilder.withLastModifiedDateTime(lastModifiedDateTime, StatementCondition.GREATER_OR_EQUAL);
		}

		return findByStatementBuilder(orderStatementBuilder.toStatementBuilder());
	}
}
