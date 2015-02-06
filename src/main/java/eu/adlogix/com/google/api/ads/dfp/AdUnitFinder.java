package eu.adlogix.com.google.api.ads.dfp;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import com.google.api.ads.dfp.axis.factory.DfpServices;
import com.google.api.ads.dfp.lib.client.DfpSession;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import eu.adlogix.com.google.api.ads.dfp.domain.AdUnit;
import eu.adlogix.com.google.api.ads.dfp.domain.AdUnitPage;
import eu.adlogix.com.google.api.ads.dfp.domain.DfpDomainValidator;
import eu.adlogix.com.google.api.ads.dfp.domain.DfpVersion;
import eu.adlogix.com.google.api.ads.dfp.domain.StatementBuilder;
import eu.adlogix.com.google.api.ads.dfp.service.DfpServiceClassManager;
import eu.adlogix.com.google.api.ads.dfp.service.DfpServiceValidator;
import eu.adlogix.com.google.api.ads.dfp.service.InventoryService;
import eu.adlogix.com.google.api.ads.dfp.service.NetworkService;

/**
 * @param <T>- DFP AdUnit
 * @param <S>- DFP StatementBuilder
 * 
 */
public class AdUnitFinder<T, S> {

	private final InventoryService<?> inventoryService;

	private final NetworkService<?> networkServiceInterface;

	private final int statementPageLimit;

	private final DfpVersion dfpVersion;

	private static DfpServiceClassManager serviceClassManager = new DfpServiceClassManager();


	/**
	 * Allows to construct the {@link AdUnitFinder} with {@link DfpServices} and
	 * {@link DfpSession} which will extract the necessary services to use in
	 * this class.
	 * 
	 * @param dfpServices
	 * @param session
	 * @param dfpVersion
	 */
	public AdUnitFinder(final DfpServices dfpServices, final DfpSession session, DfpVersion dfpVersion) {

		this(dfpServices.get(session, serviceClassManager.getInventoryServiceInterface(dfpVersion)), dfpServices.get(session, serviceClassManager.getNetworkServiceInterface(dfpVersion)), dfpVersion);

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
	 * @param dfpVersion
	 * 
	 */

	private <I, N> AdUnitFinder(final I inventoryService, final N networkService, DfpVersion dfpVersion) {

		this(inventoryService, networkService, StatementBuilder.getSuggestedPageLimit(dfpVersion), dfpVersion);
	}


	/**
	 * Constructs the {@link AdUnitFinder}
	 * 
	 * @param inventoryService
	 * @param networkServiceInterface
	 * @param statementPageLimit
	 * @param dfpVersion
	 * 
	 */

	private <I, N> AdUnitFinder(final I inventoryService, final N networkService, final int statementPageLimit,
			DfpVersion dfpVersion) {
		checkNotNull(inventoryService, "inventoryService should not be null");

		this.dfpVersion = dfpVersion;

		if (DfpServiceValidator.isValidInventoryService(inventoryService, this.dfpVersion)) {
			this.inventoryService = new InventoryService<I>(inventoryService);
		} else {
			throw new RuntimeException("Invalid InventoryService object for version:"
					+ this.dfpVersion.getVersionName());
		}

		if (DfpServiceValidator.isValidNetworkService(networkService, this.dfpVersion)) {
			this.networkServiceInterface = new NetworkService<N>(networkService);

		} else {
			throw new RuntimeException("Invalid NetworkService object for version:" + this.dfpVersion.getVersionName());
		}
		this.statementPageLimit = statementPageLimit;
	}

	/**
	 * Changes the {@link StatementBuilder} page limit
	 * 
	 * @param statementPageLimit
	 *            The {@link StatementBuilder} page limit
	 * @return
	 */
	public AdUnitFinder<T, S> setStatementPageLimit(final int statementPageLimit) {
		return new AdUnitFinder<T, S>(inventoryService.getInventoryService(), networkServiceInterface.getNetworkServiceInterface(), statementPageLimit, this.dfpVersion);
	}

	/**
	 * @return The effective root {@link AdUnit} of the network
	 */
	public T findRoot() {
		try {
			return findById(networkServiceInterface.getCurrentNetwork().getEffectiveRootAdUnitId());
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}
	
	public T findById(String id) {
		return Iterables.getOnlyElement(findByAdUnitStatementBuilder(new AdUnitStatementBuilderCreator<S>(this.dfpVersion).withId(id, StatementCondition.EQUAL)));
	}

	public List<T> findByIds(List<String> ids) {
		return findByAdUnitStatementBuilder(new AdUnitStatementBuilderCreator<S>(this.dfpVersion).withIds(ids));
	}

	public List<T> findAllByParentId(String parentId) {
		return findAllByParentId(parentId, null);
	}

	public List<T> findAllByParentId(String parentId, DateTime lastModifiedDateTime) {
		return findByAdUnitStatementBuilder(new AdUnitStatementBuilderCreator<S>(this.dfpVersion).withParentId(parentId, StatementCondition.EQUAL), lastModifiedDateTime);
	}

	public List<T> findAllActiveByParentId(String parentId) {
		return findAllActiveByParentId(parentId, null);
	}

	public List<T> findAllActiveByParentId(String parentId, DateTime lastModifiedDateTime) {
		return findByAdUnitStatementBuilder(new AdUnitStatementBuilderCreator<S>(this.dfpVersion).withParentId(parentId, StatementCondition.EQUAL)
				.withStatusActive(), lastModifiedDateTime);
	}

	public List<T> findAll() {
		return findAll(null);
	}

	public List<T> findAll(DateTime lastModifiedDateTime) {
		return findByAdUnitStatementBuilder(new AdUnitStatementBuilderCreator<S>(this.dfpVersion), lastModifiedDateTime);
	}

	public List<T> findAllActive() {
		return findAllActive(null);
	}

	public List<T> findAllActive(DateTime lastModifiedDateTime) {
		return findByAdUnitStatementBuilder(new AdUnitStatementBuilderCreator<S>(this.dfpVersion).withStatusActive(), lastModifiedDateTime);
	}

	/**
	 * @param dfpStatementBuilder
	 * @return dfpAdUnitList
	 */
	public List<T> findByStatementBuilder(S dfpStatementBuilder) {

		StatementBuilder<S> statementBuilder;

		if (DfpDomainValidator.isValidStatementBuilder(dfpStatementBuilder, this.dfpVersion)) {
			statementBuilder = new StatementBuilder<S>(dfpStatementBuilder);
		} else {
			throw new RuntimeException("Invalid StatementBuilder object for version:" + this.dfpVersion.getVersionName());

		}

		int totalResultSetSize = 0;

		List<AdUnit<T>> adUnits = new ArrayList<AdUnit<T>>();

		statementBuilder.limit(statementPageLimit);

		do {
			AdUnitPage<?, T> page = inventoryService.getAdUnitsByStatement(statementBuilder.toStatement());

			if (page.getResults() != null) {
				totalResultSetSize = page.getTotalResultSetSize();
				adUnits.addAll(Lists.newArrayList(page.getResults()));
			}

			statementBuilder.increaseOffsetBy(statementPageLimit);
		} while (statementBuilder.getOffset() < totalResultSetSize);

		List<T> dfpAdUnits = new ArrayList<T>();
		for (AdUnit<T> adUnit : adUnits) {
			T dfpAdunit = (T) adUnit.getDfpAdunit();
			dfpAdUnits.add(dfpAdunit);
		}

		return dfpAdUnits;
	}

	private List<T> findByAdUnitStatementBuilder(AdUnitStatementBuilderCreator<S> adUnitStatementBuilder) {
		return findByAdUnitStatementBuilder(adUnitStatementBuilder, null);
	}

	private List<T> findByAdUnitStatementBuilder(AdUnitStatementBuilderCreator<S> adUnitStatementBuilder,
			DateTime lastModifiedDateTime) {

		if (null != lastModifiedDateTime) {
			adUnitStatementBuilder.withLastModifiedDateTime(lastModifiedDateTime, StatementCondition.GREATER_OR_EQUAL);
		}

		return findByStatementBuilder(adUnitStatementBuilder.toStatementBuilder());
	}
}