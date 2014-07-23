package eu.adlogix.com.google.api.ads.dfp;


public enum PlacementStatementQueryFilter implements StatementQueryFilter {

	ID("id"), LAST_MODIFIED_DATE_TIME("lastModifiedDateTime");

	private final String id;

	private PlacementStatementQueryFilter(String id) {
		this.id = id;
	}

	@Override
	public String getId() {
		return id;
	}
}
