package eu.adlogix.com.google.api.ads.dfp.v201408;

import static com.google.common.base.Preconditions.checkNotNull;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import com.google.api.ads.dfp.axis.factory.DfpServices;
import com.google.api.ads.dfp.axis.utils.v201408.StatementBuilder;
import com.google.api.ads.dfp.axis.v201408.ApiException;
import com.google.api.ads.dfp.axis.v201408.Order;
import com.google.api.ads.dfp.axis.v201408.OrderPage;
import com.google.api.ads.dfp.axis.v201408.OrderServiceInterface;
import com.google.api.ads.dfp.lib.client.DfpSession;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import eu.adlogix.com.google.api.ads.dfp.StatementCondition;

public class OrderFinder {

	private final OrderServiceInterface orderService;

	/**
	 * Allows to construct the {@link OrderFinder} with {@link DfpServices} and
	 * {@link DfpSession} which will extract the necessary services to use in
	 * this class.
	 * 
	 * @param dfpServices
	 * @param session
	 */
	public OrderFinder(final DfpServices dfpServices, final DfpSession session) {
		this(dfpServices.get(session, OrderServiceInterface.class));
	}

	/**
	 * Constructs the {@link OrderFinder}
	 * 
	 * @param orderService
	 */
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
