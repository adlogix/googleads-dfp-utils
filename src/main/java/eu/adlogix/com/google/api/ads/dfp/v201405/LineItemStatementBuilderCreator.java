package eu.adlogix.com.google.api.ads.dfp.v201405;

import org.joda.time.DateTime;

import com.google.api.ads.dfp.axis.utils.v201405.DateTimes;

import eu.adlogix.com.google.api.ads.dfp.LineItemStatementQueryFilter;
import eu.adlogix.com.google.api.ads.dfp.StatementCondition;
import eu.adlogix.com.google.api.ads.dfp.StatementQueryValue;

public class LineItemStatementBuilderCreator extends BaseStatementBuilderCreator {

	public LineItemStatementBuilderCreator withId(Long id, StatementCondition condition) {
		where(LineItemStatementQueryFilter.ID, new StatementQueryValue(id, condition));
		return this;
	}
	
	public LineItemStatementBuilderCreator withLastModifiedDateTime(DateTime lastModifiedDateTime, StatementCondition condition) {
		where(LineItemStatementQueryFilter.LAST_MODIFIED_DATE_TIME, new StatementQueryValue(DateTimes.toDateTime(lastModifiedDateTime), condition));
		return this;
	}
}
