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
import eu.adlogix.com.google.api.ads.dfp.domain.Placement;
import eu.adlogix.com.google.api.ads.dfp.domain.PlacementPage;
import eu.adlogix.com.google.api.ads.dfp.domain.StatementBuilder;
import eu.adlogix.com.google.api.ads.dfp.service.DfpServiceClassManager;
import eu.adlogix.com.google.api.ads.dfp.service.DfpServiceValidator;
import eu.adlogix.com.google.api.ads.dfp.service.PlacementService;

/**
 * @param <T>- DFP Placement
 * @param <S>- DFP StatementBuilder
 * 
 */
public class PlacementFinder<T, S> {

	private static DfpServiceClassManager serviceClassManager = new DfpServiceClassManager();

	private final PlacementService<?> placementService;

	private final DfpVersion dfpVersion;


	/**
	 * Allows to construct the {@link PlacementFinder} with {@link DfpServices}
	 * and {@link DfpSession} which will extract the necessary services to use
	 * in this class.
	 * 
	 * @param dfpServices
	 * @param session
	 * @param dfpVersion
	 */
	public PlacementFinder(final DfpServices dfpServices, final DfpSession session, DfpVersion dfpVersion) {
		this(dfpServices.get(session, serviceClassManager.getPlacementServiceInterface(dfpVersion)), dfpVersion);
	}

	/**
	 * Constructs the {@link PlacementFinder}
	 * 
	 * @param placementService
	 * @param dfpVersion
	 */
	private <P> PlacementFinder(final P placementService, DfpVersion dfpVersion) {

		this.dfpVersion = dfpVersion;

		checkNotNull(placementService);
		if (DfpServiceValidator.isValidPlacementService(placementService, this.dfpVersion)) {
			this.placementService = new PlacementService<P>(placementService);

		} else {
			throw new RuntimeException("Invalid PlacementService object for version:"
					+ this.dfpVersion.getVersionName());
		}

	}

	public T findById(Long id) {
		return Iterables.getOnlyElement(findByPlacementStatementBuilder(new PlacementStatementBuilderCreator<S>(this.dfpVersion).withId(id, StatementCondition.EQUAL)));
	}

	public List<T> findAll() {
		return findAll(null);
	}

	public List<T> findAll(DateTime lastModifiedDateTime) {
		return findByPlacementStatementBuilder(new PlacementStatementBuilderCreator<S>(this.dfpVersion), lastModifiedDateTime);
	}

	/**
	 * @param dfpStatementBuilder
	 * @return dfpPlacementList
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

		List<Placement<T>> placements = new ArrayList<Placement<T>>();

		do {

			PlacementPage<?, T> page = placementService.getPlacementsByStatement(statementBuilder.toStatement());

			if (page.getResults() != null) {
				totalResultSetSize = page.getTotalResultSetSize();
				placements.addAll(Lists.newArrayList(page.getResults()));
			}

			statementBuilder.increaseOffsetBy(StatementBuilder.getSuggestedPageLimit(this.dfpVersion));
		} while (statementBuilder.getOffset() < totalResultSetSize);

		List<T> dfpPlacements = new ArrayList<T>();
		for (Placement<T> placement : placements) {
			T dfpPlacement = (T) placement.getPlacement();
			dfpPlacements.add(dfpPlacement);
		}


		return dfpPlacements;
	}

	private List<T> findByPlacementStatementBuilder(PlacementStatementBuilderCreator<S> placementStatementBuilder) {
		return findByPlacementStatementBuilder(placementStatementBuilder, null);

	}

	private List<T> findByPlacementStatementBuilder(PlacementStatementBuilderCreator<S> placementStatementBuilder,
			DateTime lastModifiedDateTime) {
		if (null != lastModifiedDateTime) {
			placementStatementBuilder.withLastModifiedDateTime(lastModifiedDateTime, StatementCondition.GREATER_OR_EQUAL);
		}

		return findByStatementBuilder(placementStatementBuilder.toStatementBuilder());
	}
}
