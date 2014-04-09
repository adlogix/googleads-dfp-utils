package eu.adlogix.com.google.api.ads.dfp.v201403;

public enum AdUnitStatementQueryFilter implements StatementQueryFilter {

	PARENT_ID("parentId"), STATUS("status"), LAST_MODIFIED_DATE_TIME("lastModifiedDateTime");

	private final String id;

	private AdUnitStatementQueryFilter(String id) {
		this.id = id;
	}

	@Override
	public String getId() {
		return id;
	}

}
