package eu.adlogix.com.google.api.ads.dfp;
import org.joda.time.DateTime;

import eu.adlogix.com.google.api.ads.dfp.domain.DateTimes;
import eu.adlogix.com.google.api.ads.dfp.domain.DfpVersion;


public class LineItemStatementBuilderCreator<S> extends BaseStatementBuilderCreator<S> {

	public LineItemStatementBuilderCreator(DfpVersion dfpVersion) {
		super(dfpVersion);
	}

	public LineItemStatementBuilderCreator<S> withId(Long id, StatementCondition condition) {
		where(LineItemStatementQueryFilter.ID, new StatementQueryValue(id, condition));
		return this;
	}
	
	public LineItemStatementBuilderCreator<S> withLastModifiedDateTime(DateTime lastModifiedDateTime,
			StatementCondition condition) {
		where(LineItemStatementQueryFilter.LAST_MODIFIED_DATE_TIME, new StatementQueryValue(DateTimes.toDateTime(dfpVersion, lastModifiedDateTime), condition));
		return this;
	}
}
