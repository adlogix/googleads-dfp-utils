package eu.adlogix.com.google.api.ads.dfp.v201403;

import static com.google.common.base.Preconditions.checkNotNull;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import com.google.api.ads.dfp.axis.utils.v201403.StatementBuilder;
import com.google.api.ads.dfp.axis.v201403.ApiException;
import com.google.api.ads.dfp.axis.v201403.LineItem;
import com.google.api.ads.dfp.axis.v201403.LineItemPage;
import com.google.api.ads.dfp.axis.v201403.LineItemServiceInterface;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

public class LineItemFinder {

	private final LineItemServiceInterface lineItemService;

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
