package eu.adlogix.com.google.api.ads.dfp.v201608;

import com.google.api.ads.dfp.axis.factory.DfpServices;
import com.google.api.ads.dfp.axis.utils.v201608.StatementBuilder;
import com.google.api.ads.dfp.axis.v201608.AdUnit;
import com.google.api.ads.dfp.axis.v201608.LineItem;
import com.google.api.ads.dfp.axis.v201608.Order;
import com.google.api.ads.dfp.axis.v201608.Placement;
import com.google.api.ads.dfp.lib.client.DfpSession;

import eu.adlogix.com.google.api.ads.dfp.AdUnitFinder;
import eu.adlogix.com.google.api.ads.dfp.LineItemFinder;
import eu.adlogix.com.google.api.ads.dfp.OrderFinder;
import eu.adlogix.com.google.api.ads.dfp.PlacementFinder;
import eu.adlogix.com.google.api.ads.dfp.domain.DfpVersion;

public class DfpFinderFactory {

	private final DfpVersion VERSION = DfpVersion.V_201608;

	private AdUnitFinder<AdUnit, StatementBuilder> adUnitFinder;

	private OrderFinder<Order, StatementBuilder> orderFinder;

	private LineItemFinder<LineItem, StatementBuilder> lineItemFinder;

	private PlacementFinder<Placement, StatementBuilder> placementFinder;

	private final DfpServices dfpServices;

	private final DfpSession dfpSession;

	public DfpFinderFactory(DfpServices dfpServices, DfpSession dfpSession) {
		super();
		this.dfpServices = dfpServices;
		this.dfpSession = dfpSession;
	}

	public AdUnitFinder<AdUnit, StatementBuilder> getAdUnitFinder() {
		if (adUnitFinder == null) {
			adUnitFinder = new AdUnitFinder<AdUnit, StatementBuilder>(dfpServices, dfpSession, VERSION);
		}
		return (AdUnitFinder<AdUnit, StatementBuilder>) adUnitFinder;
	}

	public OrderFinder<Order, StatementBuilder> getOrderFinder() {
		if (orderFinder == null) {
			orderFinder = new OrderFinder<Order, StatementBuilder>(dfpServices, dfpSession, VERSION);
		}
		return (OrderFinder<Order, StatementBuilder>) orderFinder;
	}

	public LineItemFinder<LineItem, StatementBuilder> getLineItemFinder() {
		if (lineItemFinder == null) {
			lineItemFinder = new LineItemFinder<LineItem, StatementBuilder>(dfpServices, dfpSession, VERSION);
		}
		return (LineItemFinder<LineItem, StatementBuilder>) lineItemFinder;
	}

	public PlacementFinder<Placement, StatementBuilder> getPlacementFinder() {
		if (placementFinder == null) {
			placementFinder = new PlacementFinder<Placement, StatementBuilder>(dfpServices, dfpSession, VERSION);
		}
		return (PlacementFinder<Placement, StatementBuilder>) placementFinder;
	}

}
