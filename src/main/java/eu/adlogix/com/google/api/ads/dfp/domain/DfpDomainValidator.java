package eu.adlogix.com.google.api.ads.dfp.domain;



public class DfpDomainValidator {
	
	public final static String DFP_AXIS_UTILS_PACKAGE_PREFIX = "com.google.api.ads.dfp.axis.utils";
	
	public static boolean isValidStatementBuilder(Object statementBuilder, DfpVersion version) {

		String className = statementBuilder.getClass().getName();

		String statementBuilderClassName=DFP_AXIS_UTILS_PACKAGE_PREFIX+"."+version.getVersionName()+".StatementBuilder";
		
		if (className.equals(statementBuilderClassName)) {
			return true;
		}
		
		return false;
	}

}
