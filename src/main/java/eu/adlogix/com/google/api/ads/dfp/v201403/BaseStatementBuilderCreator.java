package eu.adlogix.com.google.api.ads.dfp.v201403;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.google.api.ads.dfp.axis.utils.v201403.StatementBuilder;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public abstract class BaseStatementBuilderCreator {

	private Map<StatementQueryFilter, StatementQueryValue> where = Maps.newHashMap();

	protected void where(final StatementQueryFilter statementQueryFilter, StatementQueryValue statementQueryValue) {
		where.put(statementQueryFilter, statementQueryValue);
	}

	private void buildWhereCondition(final StatementBuilder statementBuilder,
			final Map<StatementQueryFilter, StatementQueryValue> where) {

		List<String> queryConditions = Lists.newArrayList();

		for (Map.Entry<StatementQueryFilter, StatementQueryValue> entry : where.entrySet()) {
			if (entry.getValue().getObject() != null) {
				statementBuilder.withBindVariableValue(entry.getKey().getId(), entry.getValue().getObject());
			}
			queryConditions.add(buildWhereConditionEntry(entry));
		}

		statementBuilder.where(StringUtils.join(queryConditions, " AND "));
	}

	private String buildWhereConditionEntry(Map.Entry<StatementQueryFilter, StatementQueryValue> entry) {

		String filterId = entry.getKey().getId();
		Object filterObject = entry.getValue().getObject();
		String filterConditionOperator = entry.getValue().getCondition().getOperator();

		if (filterObject == null) {
			return filterId + " IS NULL";
		}

		return filterId + " " + filterConditionOperator + " :" + filterId;
	}

	public final StatementBuilder toStatementBuilder() {
		return toStatementBuilder(new StatementBuilder());
	}

	public final StatementBuilder toStatementBuilder(StatementBuilder statementBuilder) {
		checkNotNull(statementBuilder);
		buildWhereCondition(statementBuilder, where);
		return statementBuilder;
	}
}