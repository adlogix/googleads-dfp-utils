package eu.adlogix.com.google.api.ads.dfp.v201411;

import org.joda.time.DateTime;

import com.google.api.ads.dfp.axis.utils.v201411.DateTimes;

import eu.adlogix.com.google.api.ads.dfp.OrderStatementQueryFilter;
import eu.adlogix.com.google.api.ads.dfp.StatementCondition;
import eu.adlogix.com.google.api.ads.dfp.StatementQueryValue;

public class OrderStatementBuilderCreator extends BaseStatementBuilderCreator {

	public OrderStatementBuilderCreator withId(Long id, StatementCondition condition) {
		where(OrderStatementQueryFilter.ID, new StatementQueryValue(id, condition));
		return this;
	}

	public OrderStatementBuilderCreator withLastModifiedDateTime(DateTime lastModifiedDateTime,
			StatementCondition condition) {
		where(OrderStatementQueryFilter.LAST_MODIFIED_DATE_TIME, new StatementQueryValue(DateTimes.toDateTime(lastModifiedDateTime), condition));
		return this;
	}

}
