package eu.adlogix.com.google.api.ads.dfp;


public enum AdUnitStatementQueryFilter implements StatementQueryFilter {

	ID("id"), PARENT_ID("parentId"), STATUS("status"), LAST_MODIFIED_DATE_TIME("lastModifiedDateTime");

	private final String id;

	private AdUnitStatementQueryFilter(String id) {
		this.id = id;
	}

	@Override
	public String getId() {
		return id;
	}

}
