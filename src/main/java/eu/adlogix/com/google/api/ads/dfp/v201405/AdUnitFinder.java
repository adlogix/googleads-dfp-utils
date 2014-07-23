package eu.adlogix.com.google.api.ads.dfp.v201405;

import static com.google.common.base.Preconditions.checkNotNull;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import com.google.api.ads.dfp.axis.factory.DfpServices;
import com.google.api.ads.dfp.axis.utils.v201405.StatementBuilder;
import com.google.api.ads.dfp.axis.v201405.AdUnit;
import com.google.api.ads.dfp.axis.v201405.AdUnitPage;
import com.google.api.ads.dfp.axis.v201405.ApiException;
import com.google.api.ads.dfp.axis.v201405.InventoryServiceInterface;
import com.google.api.ads.dfp.axis.v201405.NetworkServiceInterface;
import com.google.api.ads.dfp.lib.client.DfpSession;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import eu.adlogix.com.google.api.ads.dfp.StatementCondition;

public class AdUnitFinder {

	private final InventoryServiceInterface inventoryService;

	private final NetworkServiceInterface networkServiceInterface;

	/**
	 * Allows to construct the {@link AdUnitFinder} with {@link DfpServices} and
	 * {@link DfpSession} which will extract the necessary services to use in
	 * this class.
	 * 
	 * @param dfpServices
	 * @param session
	 */
	public AdUnitFinder(final DfpServices dfpServices, final DfpSession session) {
		this(dfpServices.get(session, InventoryServiceInterface.class), dfpServices.get(session, NetworkServiceInterface.class));
	}

	/**
	 * Constructs the {@link AdUnitFinder} with an instance of
	 * {@link InventoryServiceInterface} and {@link NetworkServiceInterface}.
	 * 
	 * The {@link NetworkServiceInterface} is necessary for example when we need
	 * to find the effective root AdUnit.
	 * 
	 * @param inventoryService
	 * @param networkServiceInterface
	 */
	public AdUnitFinder(final InventoryServiceInterface inventoryService,
			final NetworkServiceInterface networkServiceInterface) {
		checkNotNull(inventoryService, "inventoryService should not be null");
		this.inventoryService = inventoryService;
		this.networkServiceInterface = networkServiceInterface;
	}

	/**
	 * @return The effective root {@link AdUnit} of the network
	 */
	public AdUnit findRoot() {
		try {
			return findById(networkServiceInterface.getCurrentNetwork().getEffectiveRootAdUnitId());
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	public AdUnit findById(String id) {
		return Iterables.getOnlyElement(findByAdUnitStatementBuilder(new AdUnitStatementBuilderCreator().withId(id, StatementCondition.EQUAL)));
	}

	public List<AdUnit> findByIds(List<String> ids) {
		return findByAdUnitStatementBuilder(new AdUnitStatementBuilderCreator().withIds(ids));
	}

	public List<AdUnit> findAllByParentId(String parentId) {
		return findAllByParentId(parentId, null);
	}

	public List<AdUnit> findAllByParentId(String parentId, DateTime lastModifiedDateTime) {
		return findByAdUnitStatementBuilder(new AdUnitStatementBuilderCreator().withParentId(parentId, StatementCondition.EQUAL), lastModifiedDateTime);
	}

	public List<AdUnit> findAllActiveByParentId(String parentId) {
		return findAllActiveByParentId(parentId, null);
	}

	public List<AdUnit> findAllActiveByParentId(String parentId, DateTime lastModifiedDateTime) {
		return findByAdUnitStatementBuilder(new AdUnitStatementBuilderCreator().withParentId(parentId, StatementCondition.EQUAL)
				.withStatusActive(), lastModifiedDateTime);
	}

	public List<AdUnit> findAll() {
		return findAll(null);
	}

	public List<AdUnit> findAll(DateTime lastModifiedDateTime) {
		return findByAdUnitStatementBuilder(new AdUnitStatementBuilderCreator(), lastModifiedDateTime);
	}

	public List<AdUnit> findAllActive() {
		return findAllActive(null);
	}

	public List<AdUnit> findAllActive(DateTime lastModifiedDateTime) {
		return findByAdUnitStatementBuilder(new AdUnitStatementBuilderCreator().withStatusActive(), lastModifiedDateTime);
	}

	public List<AdUnit> findByStatementBuilder(StatementBuilder statementBuilder) {

		statementBuilder.limit(StatementBuilder.SUGGESTED_PAGE_LIMIT);

		int totalResultSetSize = 0;

		List<AdUnit> adUnits = new ArrayList<AdUnit>();

		try {
			do {
				AdUnitPage page = inventoryService.getAdUnitsByStatement(statementBuilder.toStatement());

				if (page.getResults() != null) {
					totalResultSetSize = page.getTotalResultSetSize();
					adUnits.addAll(Lists.newArrayList(page.getResults()));
				}

				statementBuilder.increaseOffsetBy(StatementBuilder.SUGGESTED_PAGE_LIMIT);
			} while (statementBuilder.getOffset() < totalResultSetSize);

		} catch (ApiException e) {
			throw new RuntimeException(e);
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}

		return adUnits;
	}

	private List<AdUnit> findByAdUnitStatementBuilder(AdUnitStatementBuilderCreator adUnitStatementBuilder) {
		return findByAdUnitStatementBuilder(adUnitStatementBuilder, null);
	}

	private List<AdUnit> findByAdUnitStatementBuilder(AdUnitStatementBuilderCreator adUnitStatementBuilder,
			DateTime lastModifiedDateTime) {

		if (null != lastModifiedDateTime) {
			adUnitStatementBuilder.withLastModifiedDateTime(lastModifiedDateTime, StatementCondition.GREATER_OR_EQUAL);
		}

		return findByStatementBuilder(adUnitStatementBuilder.toStatementBuilder());
	}
}