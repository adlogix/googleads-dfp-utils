package eu.adlogix.com.google.api.ads.dfp.v201408;

import java.util.List;

import org.joda.time.DateTime;

import com.google.api.ads.dfp.axis.utils.v201408.DateTimes;

import eu.adlogix.com.google.api.ads.dfp.AdUnitStatementQueryFilter;
import eu.adlogix.com.google.api.ads.dfp.StatementCondition;
import eu.adlogix.com.google.api.ads.dfp.StatementQueryValue;

public class AdUnitStatementBuilderCreator extends BaseStatementBuilderCreator {

	public AdUnitStatementBuilderCreator withId(String id, StatementCondition condition) {
		where(AdUnitStatementQueryFilter.ID, new StatementQueryValue(id, condition));
		return this;
	}

	public AdUnitStatementBuilderCreator withIds(List<String> ids) {
		where(AdUnitStatementQueryFilter.ID, new StatementQueryValue(ids, StatementCondition.IN));
		return this;
	}

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