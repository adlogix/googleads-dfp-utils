package eu.adlogix.com.google.api.ads.dfp.domain;

import static org.joor.Reflect.on;
import lombok.Getter;
import eu.adlogix.com.google.api.ads.dfp.service.DfpServiceClassManager;
import eu.adlogix.com.google.api.ads.dfp.util.DfpClassUtil;

/**
 * @param <T>- DFP StatementBuilder
 * 
 */
public class StatementBuilder<T> {

	@Getter
	T dfpStatementBuilder;

	public StatementBuilder(DfpVersion version) {

		Class<?> statementBuilderClass = null;

		try {
			statementBuilderClass = new DfpServiceClassManager().getStatementBuilder(version);
			this.dfpStatementBuilder = on(statementBuilderClass).create().get();

		} catch (Exception e) {
			throw new RuntimeException("Error in getting object of class:" + statementBuilderClass.getName(), e);
		}

	}

	public StatementBuilder(T dfpStatementBuilder) {

		this.dfpStatementBuilder = dfpStatementBuilder;

		try {
			String className = dfpStatementBuilder.getClass().getName();

			if (!(DfpClassUtil.isValidDfpPackage(className) && className.endsWith("StatementBuilder"))) {
				throw new RuntimeException("The parameter provided is not an object of StatementBuilder");
			}

		} catch (SecurityException e) {
			throw new RuntimeException(e);
		}
	}

	public StatementBuilder<T> limit(int statementPageLimit) {

		try {

			T returnValue = on(this.dfpStatementBuilder).call("limit", statementPageLimit).get();
			return new StatementBuilder<T>(returnValue);

		} catch (Exception e) {
			throw new RuntimeException("Error in calling method:limit in class:"
					+ this.dfpStatementBuilder.getClass().getName(), e);
		}

	}

	public StatementBuilder<T> increaseOffsetBy(int statementPageLimit) {
		try {

			T returnValue = on(this.dfpStatementBuilder).call("increaseOffsetBy", statementPageLimit).get();
			return new StatementBuilder<T>(returnValue);

		} catch (Exception e) {
			throw new RuntimeException("Error in calling method:increaseOffsetBy in class:"
					+ this.dfpStatementBuilder.getClass().getName(), e);
		}
	}

	public Integer getOffset() {
		try {

			return on(this.dfpStatementBuilder).call("getOffset").get();
		} catch (Exception e) {
			throw new RuntimeException("Error in calling method:getOffset in class:"
					+ this.dfpStatementBuilder.getClass().getName(), e);
		}

	}

	public Integer getSuggestedPageLimit() {
		try {
			return on(this.dfpStatementBuilder).field("SUGGESTED_PAGE_LIMIT").get();

		} catch (Exception e) {
			throw new RuntimeException("Error in getting field:SUGGESTED_PAGE_LIMIT in class:"
					+ this.dfpStatementBuilder.getClass().getName(), e);
		}

	}

	public <S> Statement<S> toStatement() {
		try {
			S dfpStatement = on(this.dfpStatementBuilder).call("toStatement").get();
			return new Statement<S>(dfpStatement);

		} catch (Exception e) {
			throw new RuntimeException("Error in calling method:toStatement in class:"
					+ this.dfpStatementBuilder.getClass().getName(), e);
		}

	}

	public static Integer getSuggestedPageLimit(DfpVersion version) {
		Class<?> statementBuilderClass = null;

		try {
			statementBuilderClass = new DfpServiceClassManager().getStatementBuilder(version);
			return on(statementBuilderClass).field("SUGGESTED_PAGE_LIMIT").get();

		} catch (Exception e) {
			throw new RuntimeException("Error in getting field:SUGGESTED_PAGE_LIMIT in class:"
					+ statementBuilderClass.getName(), e);
		}

	}

	public StatementBuilder<T> withBindVariableValue(String key, Object value) {

		try {
			T dfpStatementBuilder = on(this.dfpStatementBuilder).call("withBindVariableValue", key, value).get();

			return new StatementBuilder<T>(dfpStatementBuilder);

		} catch (Exception e) {
			throw new RuntimeException("Error in calling method:withBindVariableValue in class:"
					+ this.dfpStatementBuilder.getClass().getName(), e);
		}
	}

	public StatementBuilder<T> where(String conditions) {
		try {
			T dfpStatementBuilder = on(this.dfpStatementBuilder).call("where", conditions).get();
			return new StatementBuilder<T>(dfpStatementBuilder);

		} catch (Exception e) {
			throw new RuntimeException("Error in calling method:where in class:"
					+ this.dfpStatementBuilder.getClass().getName(), e);
		}
	}

}
