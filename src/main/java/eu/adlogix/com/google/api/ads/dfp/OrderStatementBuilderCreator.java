package eu.adlogix.com.google.api.ads.dfp;

import org.joda.time.DateTime;

import eu.adlogix.com.google.api.ads.dfp.domain.DateTimes;
import eu.adlogix.com.google.api.ads.dfp.domain.DfpVersion;


public class OrderStatementBuilderCreator<S> extends BaseStatementBuilderCreator<S> {

	public OrderStatementBuilderCreator(DfpVersion dfpVersion) {
		super(dfpVersion);
	}

	public OrderStatementBuilderCreator<S> withId(Long id, StatementCondition condition) {
		where(OrderStatementQueryFilter.ID, new StatementQueryValue(id, condition));
		return this;
	}

	public OrderStatementBuilderCreator<S> withLastModifiedDateTime(DateTime lastModifiedDateTime,
			StatementCondition condition) {
		where(OrderStatementQueryFilter.LAST_MODIFIED_DATE_TIME, new StatementQueryValue(DateTimes.toDateTime(dfpVersion, lastModifiedDateTime), condition));
		return this;
	}

}
