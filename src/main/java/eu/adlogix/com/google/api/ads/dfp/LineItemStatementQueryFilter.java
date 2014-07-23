package eu.adlogix.com.google.api.ads.dfp;


public enum LineItemStatementQueryFilter implements StatementQueryFilter {

	ID("id"), LAST_MODIFIED_DATE_TIME("lastModifiedDateTime");

	private final String id;

	private LineItemStatementQueryFilter(String id) {
		this.id = id;
	}

	@Override
	public String getId() {
		return id;
	}
}
