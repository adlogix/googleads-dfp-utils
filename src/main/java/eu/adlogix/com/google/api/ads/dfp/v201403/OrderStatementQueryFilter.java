package eu.adlogix.com.google.api.ads.dfp.v201403;

public enum OrderStatementQueryFilter implements StatementQueryFilter {

	ID("id"), LAST_MODIFIED_DATE_TIME("lastModifiedDateTime");

	private final String id;

	private OrderStatementQueryFilter(String id) {
		this.id = id;
	}

	@Override
	public String getId() {
		return id;
	}

}
