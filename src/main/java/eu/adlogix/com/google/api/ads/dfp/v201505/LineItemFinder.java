package eu.adlogix.com.google.api.ads.dfp.v201505;

import static com.google.common.base.Preconditions.checkNotNull;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import com.google.api.ads.dfp.axis.factory.DfpServices;
import com.google.api.ads.dfp.axis.utils.v201505.StatementBuilder;
import com.google.api.ads.dfp.axis.v201505.ApiException;
import com.google.api.ads.dfp.axis.v201505.LineItem;
import com.google.api.ads.dfp.axis.v201505.LineItemPage;
import com.google.api.ads.dfp.axis.v201505.LineItemServiceInterface;
import com.google.api.ads.dfp.lib.client.DfpSession;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import eu.adlogix.com.google.api.ads.dfp.StatementCondition;

public class LineItemFinder {

	private final LineItemServiceInterface lineItemService;

	/**
	 * Allows to construct the {@link LineItemFinder} with {@link DfpServices}
	 * and {@link DfpSession} which will extract the necessary services to use
	 * in this class.
	 * 
	 * @param dfpServices
	 * @param session
	 */
	public LineItemFinder(final DfpServices dfpServices, final DfpSession session) {
		this(dfpServices.get(session, LineItemServiceInterface.class));
	}

	/**
	 * Constructs the {@link LineItemFinder}
	 * 
	 * @param lineItemService
	 */
	public LineItemFinder(final LineItemServiceInterface lineItemService) {
		checkNotNull(lineItemService);
		this.lineItemService = lineItemService;
	}

	public LineItem findById(Long id) {
		return Iterables.getOnlyElement(findByLineItemStatementBuilder(new LineItemStatementBuilderCreator().withId(id, StatementCondition.EQUAL)));
	}

	public List<LineItem> findByStatementBuilder(StatementBuilder statementBuilder) {

		statementBuilder.limit(StatementBuilder.SUGGESTED_PAGE_LIMIT);

		int totalResultSetSize = 0;

		List<LineItem> lineItems = new ArrayList<LineItem>();

		try {
			do {
				LineItemPage page = lineItemService.getLineItemsByStatement(statementBuilder.toStatement());

				if (page.getResults() != null) {
					totalResultSetSize = page.getTotalResultSetSize();
					lineItems.addAll(Lists.newArrayList(page.getResults()));
				}

				statementBuilder.increaseOffsetBy(StatementBuilder.SUGGESTED_PAGE_LIMIT);
			} while (statementBuilder.getOffset() < totalResultSetSize);

		} catch (ApiException e) {
			throw new RuntimeException(e);
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}

		return lineItems;
	}

	private List<LineItem> findByLineItemStatementBuilder(LineItemStatementBuilderCreator lineItemStatementBuilder) {
		return findByLineItemStatementBuilder(lineItemStatementBuilder, null);

	}

	private List<LineItem> findByLineItemStatementBuilder(LineItemStatementBuilderCreator lineItemStatementBuilder,
			DateTime lastModifiedDateTime) {
		if (null != lastModifiedDateTime) {
			lineItemStatementBuilder.withLastModifiedDateTime(lastModifiedDateTime, StatementCondition.GREATER_OR_EQUAL);
		}

		return findByStatementBuilder(lineItemStatementBuilder.toStatementBuilder());
	}
}
