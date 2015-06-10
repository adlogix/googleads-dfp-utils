package eu.adlogix.com.google.api.ads.dfp.domain;

import lombok.Getter;
import eu.adlogix.com.google.api.ads.dfp.util.DfpClassUtil;

/**
 * @param <T>- DFP Statement
 * 
 */
public class Statement<T> {

	@Getter
	T dfpStatement;

	public Statement(T dfpStatement) {

		this.dfpStatement = dfpStatement;

		try {
			String className = dfpStatement.getClass().getName();

			if (!(DfpClassUtil.isValidDfpPackage(className) && className.endsWith("Statement"))) {
				throw new RuntimeException("The parameter provided is not an object of Statement");
			}

		} catch (SecurityException e) {
			throw new RuntimeException(e);
		}
	}
}
