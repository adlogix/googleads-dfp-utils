package eu.adlogix.com.google.api.ads.dfp.v201403;

import org.joda.time.DateTime;

import com.google.api.ads.dfp.axis.utils.v201403.DateTimes;

public class PlacementStatementBuilderCreator extends BaseStatementBuilderCreator {

	public PlacementStatementBuilderCreator withId(Long id, StatementCondition condition) {
		where(PlacementStatementQueryFilter.ID, new StatementQueryValue(id, condition));
		return this;
	}
	
	public PlacementStatementBuilderCreator withLastModifiedDateTime(DateTime lastModifiedDateTime, StatementCondition condition) {
		where(PlacementStatementQueryFilter.LAST_MODIFIED_DATE_TIME, new StatementQueryValue(DateTimes.toDateTime(lastModifiedDateTime), condition));
		return this;
	}
}
