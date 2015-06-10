package eu.adlogix.com.google.api.ads.dfp;

import eu.adlogix.com.google.api.ads.dfp.domain.DfpVersion;

public class BaseStatementBuilderCreatorFactory<S> {

	private final DfpVersion version;

	public BaseStatementBuilderCreatorFactory(DfpVersion dfpVersion) {
		super();
		this.version = dfpVersion;
	}

	public AdUnitStatementBuilderCreator<S> getAdUnitStatementBuilderCreator() {
		return new AdUnitStatementBuilderCreator<S>(version);
	}

	public LineItemStatementBuilderCreator<S> getLineItemStatementBuilderCreator() {
		return new LineItemStatementBuilderCreator<S>(version);
	}

	public OrderStatementBuilderCreator<S> getOrderStatementBuilderCreator() {
		return new OrderStatementBuilderCreator<S>(version);
	}

	public PlacementStatementBuilderCreator<S> getPlacementStatementBuilderCreator() {
		return new PlacementStatementBuilderCreator<S>(version);
	}

}
