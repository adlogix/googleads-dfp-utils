package eu.adlogix.com.google.api.ads.dfp;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import com.google.api.ads.dfp.axis.factory.DfpServices;
import com.google.api.ads.dfp.lib.client.DfpSession;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import eu.adlogix.com.google.api.ads.dfp.domain.DfpDomainValidator;
import eu.adlogix.com.google.api.ads.dfp.domain.DfpVersion;
import eu.adlogix.com.google.api.ads.dfp.domain.Order;
import eu.adlogix.com.google.api.ads.dfp.domain.OrderPage;
import eu.adlogix.com.google.api.ads.dfp.domain.StatementBuilder;
import eu.adlogix.com.google.api.ads.dfp.service.DfpServiceClassManager;
import eu.adlogix.com.google.api.ads.dfp.service.DfpServiceValidator;
import eu.adlogix.com.google.api.ads.dfp.service.OrderService;

/**
 * @param <O>- DFP Order
 * @param <S>- DFP StatementBuilder
 * 
 */
public class OrderFinder<O, S> {

	private static DfpServiceClassManager serviceClassManager = new DfpServiceClassManager();

	private final OrderService<?> orderService;
	private final DfpVersion dfpVersion;

	/**
	 * Allows to construct the {@link OrderFinder} with {@link DfpServices} and
	 * {@link DfpSession} which will extract the necessary services to use in
	 * this class.
	 * 
	 * @param dfpServices
	 * @param session
	 * @param dfpVersion
	 */
	public OrderFinder(final DfpServices dfpServices, final DfpSession session, final DfpVersion dfpVersion) {
		this(dfpServices.get(session, serviceClassManager.getOrderServiceInterface(dfpVersion)), dfpVersion);
	}

	/**
	 * Constructs the {@link OrderFinder}
	 * 
	 * @param orderService
	 * @param dfpVersion
	 */
	private <D> OrderFinder(final D orderService, DfpVersion dfpVersion) {

		this.dfpVersion = dfpVersion;

		checkNotNull(orderService);
		if (DfpServiceValidator.isValidOrderService(orderService, this.dfpVersion)) {
			this.orderService = new OrderService<D>(orderService);
		} else {
			throw new RuntimeException("Invalid OrderService object for version:" + this.dfpVersion.getVersionName());
		}

	}

	public O findById(Long id) {
		return Iterables.getOnlyElement(findByOrderStatementBuilder(new OrderStatementBuilderCreator<S>(this.dfpVersion).withId(id, StatementCondition.EQUAL)));
	}

	/**
	 * @param dfpStatementBuilder
	 * @return dfpOrderList
	 */

	public List<O> findByStatementBuilder(S dfpStatementBuilder) {

		StatementBuilder<S> statementBuilder;

		if (DfpDomainValidator.isValidStatementBuilder(dfpStatementBuilder, this.dfpVersion)) {
			statementBuilder = new StatementBuilder<S>(dfpStatementBuilder);
		} else {
			throw new RuntimeException("Invalid StatementBuilder object for version:"
					+ this.dfpVersion.getVersionName());

		}

		statementBuilder.limit(StatementBuilder.getSuggestedPageLimit(this.dfpVersion));

		int totalResultSetSize = 0;

		List<Order<O>> orders = new ArrayList<Order<O>>();

			do {
				OrderPage<?, O> page = orderService.getOrdersByStatement(statementBuilder.toStatement());

				if (page.getResults() != null) {
					totalResultSetSize = page.getTotalResultSetSize();
					orders.addAll(Lists.newArrayList(page.getResults()));
				}

			statementBuilder.increaseOffsetBy(StatementBuilder.getSuggestedPageLimit(this.dfpVersion));
			} while (statementBuilder.getOffset() < totalResultSetSize);

		List<O> dfpOrders = new ArrayList<O>();
		for (Order<O> order : orders) {
			dfpOrders.add(order.getDfpOrder());
		}

		return dfpOrders;
	}

	private List<O> findByOrderStatementBuilder(OrderStatementBuilderCreator<S> orderStatementBuilder) {
		return findByLineItemStatementBuilder(orderStatementBuilder, null);

	}

	private List<O> findByLineItemStatementBuilder(OrderStatementBuilderCreator<S> orderStatementBuilder,
			DateTime lastModifiedDateTime) {
		if (null != lastModifiedDateTime) {
			orderStatementBuilder.withLastModifiedDateTime(lastModifiedDateTime, StatementCondition.GREATER_OR_EQUAL);
		}

		return findByStatementBuilder(orderStatementBuilder.toStatementBuilder());
	}
}
