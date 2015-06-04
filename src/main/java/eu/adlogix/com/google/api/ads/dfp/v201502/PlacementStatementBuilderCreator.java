package eu.adlogix.com.google.api.ads.dfp.v201502;

import org.joda.time.DateTime;

import com.google.api.ads.dfp.axis.utils.v201502.DateTimes;

import eu.adlogix.com.google.api.ads.dfp.PlacementStatementQueryFilter;
import eu.adlogix.com.google.api.ads.dfp.StatementCondition;
import eu.adlogix.com.google.api.ads.dfp.StatementQueryValue;

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
