package eu.adlogix.com.google.api.ads.dfp;

import org.joda.time.DateTime;

import eu.adlogix.com.google.api.ads.dfp.domain.DateTimes;
import eu.adlogix.com.google.api.ads.dfp.domain.DfpVersion;

public class PlacementStatementBuilderCreator<S> extends BaseStatementBuilderCreator<S> {

	public PlacementStatementBuilderCreator(DfpVersion dfpVersion) {
		super(dfpVersion);
	}

	public PlacementStatementBuilderCreator<S> withId(Long id, StatementCondition condition) {
		where(PlacementStatementQueryFilter.ID, new StatementQueryValue(id, condition));
		return this;
	}
	
	public PlacementStatementBuilderCreator<S> withLastModifiedDateTime(DateTime lastModifiedDateTime,
			StatementCondition condition) {
		where(PlacementStatementQueryFilter.LAST_MODIFIED_DATE_TIME, new StatementQueryValue(DateTimes.toDateTime(dfpVersion, lastModifiedDateTime), condition));
		return this;
	}
}
