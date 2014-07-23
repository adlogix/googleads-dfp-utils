package eu.adlogix.com.google.api.ads.dfp.v201405;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.google.api.ads.dfp.axis.utils.v201405.StatementBuilder;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import eu.adlogix.com.google.api.ads.dfp.StatementQueryFilter;
import eu.adlogix.com.google.api.ads.dfp.StatementQueryValue;

public abstract class BaseStatementBuilderCreator {

	private Map<StatementQueryFilter, StatementQueryValue> where = Maps.newHashMap();

	protected void where(final StatementQueryFilter statementQueryFilter, StatementQueryValue statementQueryValue) {
		where.put(statementQueryFilter, statementQueryValue);
	}

	private void buildWhereCondition(final StatementBuilder statementBuilder,
			final Map<StatementQueryFilter, StatementQueryValue> where) {

		List<String> queryConditions = Lists.newArrayList();

		for (Map.Entry<StatementQueryFilter, StatementQueryValue> entry : where.entrySet()) {
			if (entry.getValue().getObject() != null && !(entry.getValue().getObject() instanceof Collection<?>)) {
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

		if (filterObject instanceof List<?>) {
			return filterId + " IN (" + listToQuery((List<?>) filterObject) + ")";
		}

		return filterId + " " + filterConditionOperator + " :" + filterId;
	}

	private String listToQuery(List<?> filterObjects) {

		StringBuilder queryBuilder = new StringBuilder();

		Iterator<?> iterator = filterObjects.iterator();

		while (iterator.hasNext()) {

			Object filterObject = iterator.next();

			queryBuilder.append(filterObject);

			if (iterator.hasNext()) {
				queryBuilder.append(",");
			}

		}

		return queryBuilder.toString();
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