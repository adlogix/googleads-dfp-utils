package eu.adlogix.com.google.api.ads.dfp;

import java.util.List;

import org.joda.time.DateTime;

import eu.adlogix.com.google.api.ads.dfp.domain.DateTimes;
import eu.adlogix.com.google.api.ads.dfp.domain.DfpVersion;


public class AdUnitStatementBuilderCreator<S> extends BaseStatementBuilderCreator<S> {

	public AdUnitStatementBuilderCreator(DfpVersion dfpVersion) {
		super(dfpVersion);
	}

	public AdUnitStatementBuilderCreator<S> withId(String id, StatementCondition condition) {
		where(AdUnitStatementQueryFilter.ID, new StatementQueryValue(id, condition));
		return this;
	}

	public AdUnitStatementBuilderCreator<S> withIds(List<String> ids) {
		where(AdUnitStatementQueryFilter.ID, new StatementQueryValue(ids, StatementCondition.IN));
		return this;
	}

	public AdUnitStatementBuilderCreator<S> withParentId(String parentId, StatementCondition condition) {
		where(AdUnitStatementQueryFilter.PARENT_ID, new StatementQueryValue(parentId, condition));
		return this;
	}

	public AdUnitStatementBuilderCreator<S> withStatusActive() {
		return withStatus("ACTIVE", StatementCondition.EQUAL);
	}

	public AdUnitStatementBuilderCreator<S> withStatus(String status, StatementCondition condition) {
		where(AdUnitStatementQueryFilter.STATUS, new StatementQueryValue(status, condition));
		return this;
	}

	public AdUnitStatementBuilderCreator<S> withLastModifiedDateTime(DateTime lastModifiedDateTime,
			StatementCondition condition) {
		where(AdUnitStatementQueryFilter.LAST_MODIFIED_DATE_TIME, new StatementQueryValue(DateTimes.toDateTime(dfpVersion, lastModifiedDateTime), condition));
		return this;
	}
}