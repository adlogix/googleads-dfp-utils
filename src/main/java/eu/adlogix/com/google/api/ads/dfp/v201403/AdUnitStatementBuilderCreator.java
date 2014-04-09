package eu.adlogix.com.google.api.ads.dfp.v201403;

import org.joda.time.DateTime;

import com.google.api.ads.dfp.axis.utils.v201403.DateTimes;

public class AdUnitStatementBuilderCreator extends BaseStatementBuilderCreator {

	public AdUnitStatementBuilderCreator withParentId(String parentId, StatementCondition condition) {
		where(AdUnitStatementQueryFilter.PARENT_ID, new StatementQueryValue(parentId, condition));
		return this;
	}

	public AdUnitStatementBuilderCreator withStatusActive() {
		return withStatus("ACTIVE", StatementCondition.EQUAL);
	}

	public AdUnitStatementBuilderCreator withStatus(String status, StatementCondition condition) {
		where(AdUnitStatementQueryFilter.STATUS, new StatementQueryValue(status, condition));
		return this;
	}

	public AdUnitStatementBuilderCreator withLastModifiedDateTime(DateTime lastModifiedDateTime, StatementCondition condition) {
		where(AdUnitStatementQueryFilter.LAST_MODIFIED_DATE_TIME, new StatementQueryValue(DateTimes.toDateTime(lastModifiedDateTime), condition));
		return this;
	}
}