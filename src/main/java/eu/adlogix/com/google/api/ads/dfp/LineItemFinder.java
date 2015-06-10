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
import eu.adlogix.com.google.api.ads.dfp.domain.LineItem;
import eu.adlogix.com.google.api.ads.dfp.domain.LineItemPage;
import eu.adlogix.com.google.api.ads.dfp.domain.StatementBuilder;
import eu.adlogix.com.google.api.ads.dfp.service.DfpServiceClassManager;
import eu.adlogix.com.google.api.ads.dfp.service.DfpServiceValidator;
import eu.adlogix.com.google.api.ads.dfp.service.LineItemService;

/**
 * @param <T>- DFP LineItem
 * @param <S>- DFP StatementBuilder
 * 
 */
public class LineItemFinder<T, S> {

	private static DfpServiceClassManager serviceClassManager = new DfpServiceClassManager();

	private final LineItemService<?> lineItemService;

	private final DfpVersion dfpVersion;

	/**
	 * Allows to construct the {@link LineItemFinder} with {@link DfpServices}
	 * and {@link DfpSession} which will extract the necessary services to use
	 * in this class.
	 * 
	 * @param dfpServices
	 * @param session
	 * @param dfpVersion
	 */
	public LineItemFinder(final DfpServices dfpServices, final DfpSession session, DfpVersion dfpVersion) {
		this(dfpServices.get(session, serviceClassManager.getLineItemServiceInterface(dfpVersion)), dfpVersion);
	}


	/**
	 * Constructs the {@link LineItemFinder}
	 * 
	 * @param lineItemService
	 * @param dfpVersion
	 * 
	 */
	private <L> LineItemFinder(final L lineItemService, DfpVersion dfpVersion) {

		this.dfpVersion = dfpVersion;

		checkNotNull(lineItemService);
		if (DfpServiceValidator.isValidLineItemService(lineItemService, this.dfpVersion)) {
			this.lineItemService = new LineItemService<L>(lineItemService);
		} else {
			throw new RuntimeException("Invalid LineItemService object for version:" + this.dfpVersion.getVersionName());
		}

	}

	public T findById(Long id) {
		return Iterables.getOnlyElement(findByLineItemStatementBuilder(new LineItemStatementBuilderCreator<S>(this.dfpVersion).withId(id, StatementCondition.EQUAL)));
	}

	/**
	 * @param dfpStatementBuilder
	 * @return dfpLineItemList
	 */
	public List<T> findByStatementBuilder(S dfpStatementBuilder) {

		StatementBuilder<S> statementBuilder;

		if (DfpDomainValidator.isValidStatementBuilder(dfpStatementBuilder, this.dfpVersion)) {
			statementBuilder = new StatementBuilder<S>(dfpStatementBuilder);
		} else {
			throw new RuntimeException("Invalid StatementBuilder object for version:"
					+ this.dfpVersion.getVersionName());

		}

		statementBuilder.limit(StatementBuilder.getSuggestedPageLimit(this.dfpVersion));

		int totalResultSetSize = 0;

		List<LineItem<T>> lineItems = new ArrayList<LineItem<T>>();

		do {
			LineItemPage<?, T> page = lineItemService.getLineItemsByStatement(statementBuilder.toStatement());

			if (page.getResults() != null) {
				totalResultSetSize = page.getTotalResultSetSize();
				lineItems.addAll(Lists.newArrayList(page.getResults()));
			}

			statementBuilder.increaseOffsetBy(StatementBuilder.getSuggestedPageLimit(this.dfpVersion));
		} while (statementBuilder.getOffset() < totalResultSetSize);

		List<T> dfpLineItems = new ArrayList<T>();
		for (LineItem<T> lineItem : lineItems) {
			T dfpLineItem = (T) lineItem.getDfpLineItem();
			dfpLineItems.add(dfpLineItem);
		}

		return dfpLineItems;
	}

	private List<T> findByLineItemStatementBuilder(LineItemStatementBuilderCreator<S> lineItemStatementBuilder) {
		return findByLineItemStatementBuilder(lineItemStatementBuilder, null);

	}

	private List<T> findByLineItemStatementBuilder(LineItemStatementBuilderCreator<S> lineItemStatementBuilder,
			DateTime lastModifiedDateTime) {
		if (null != lastModifiedDateTime) {
			lineItemStatementBuilder.withLastModifiedDateTime(lastModifiedDateTime, StatementCondition.GREATER_OR_EQUAL);
		}

		return findByStatementBuilder(lineItemStatementBuilder.toStatementBuilder());
	}
}
