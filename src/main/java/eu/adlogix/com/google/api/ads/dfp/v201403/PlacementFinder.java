package eu.adlogix.com.google.api.ads.dfp.v201403;

import static com.google.common.base.Preconditions.checkNotNull;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import com.google.api.ads.dfp.axis.factory.DfpServices;
import com.google.api.ads.dfp.axis.utils.v201403.StatementBuilder;
import com.google.api.ads.dfp.axis.v201403.ApiException;
import com.google.api.ads.dfp.axis.v201403.Placement;
import com.google.api.ads.dfp.axis.v201403.PlacementPage;
import com.google.api.ads.dfp.axis.v201403.PlacementServiceInterface;
import com.google.api.ads.dfp.lib.client.DfpSession;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

public class PlacementFinder {

	private final PlacementServiceInterface placementService;

	/**
	 * Allows to construct the {@link PlacementFinder} with {@link DfpServices}
	 * and {@link DfpSession} which will extract the necessary services to use
	 * in this class.
	 * 
	 * @param dfpServices
	 * @param session
	 */
	public PlacementFinder(final DfpServices dfpServices, final DfpSession session) {
		this(dfpServices.get(session, PlacementServiceInterface.class));
	}

	/**
	 * Constructs the {@link PlacementFinder}
	 * 
	 * @param placementService
	 */
	public PlacementFinder(final PlacementServiceInterface placementService) {
		checkNotNull(placementService);
		this.placementService = placementService;
	}

	public Placement findById(Long id) {
		return Iterables.getOnlyElement(findByPlacementStatementBuilder(new PlacementStatementBuilderCreator().withId(id, StatementCondition.EQUAL)));
	}

	public List<Placement> findAll() {
		return findAll(null);
	}

	public List<Placement> findAll(DateTime lastModifiedDateTime) {
		return findByPlacementStatementBuilder(new PlacementStatementBuilderCreator(), lastModifiedDateTime);
	}

	public List<Placement> findByStatementBuilder(StatementBuilder statementBuilder) {

		statementBuilder.limit(StatementBuilder.SUGGESTED_PAGE_LIMIT);

		int totalResultSetSize = 0;

		List<Placement> placements = new ArrayList<Placement>();

		try {
			do {
				PlacementPage page = placementService.getPlacementsByStatement(statementBuilder.toStatement());

				if (page.getResults() != null) {
					totalResultSetSize = page.getTotalResultSetSize();
					placements.addAll(Lists.newArrayList(page.getResults()));
				}

				statementBuilder.increaseOffsetBy(StatementBuilder.SUGGESTED_PAGE_LIMIT);
			} while (statementBuilder.getOffset() < totalResultSetSize);

		} catch (ApiException e) {
			throw new RuntimeException(e);
		} catch (RemoteException e) {
			throw new RuntimeException(e);
		}

		return placements;
	}

	private List<Placement> findByPlacementStatementBuilder(PlacementStatementBuilderCreator placementStatementBuilder) {
		return findByPlacementStatementBuilder(placementStatementBuilder, null);

	}

	private List<Placement> findByPlacementStatementBuilder(PlacementStatementBuilderCreator placementStatementBuilder,
			DateTime lastModifiedDateTime) {
		if (null != lastModifiedDateTime) {
			placementStatementBuilder.withLastModifiedDateTime(lastModifiedDateTime, StatementCondition.GREATER_OR_EQUAL);
		}

		return findByStatementBuilder(placementStatementBuilder.toStatementBuilder());
	}
}
