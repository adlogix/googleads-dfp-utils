package eu.adlogix.com.google.api.ads.dfp;

import lombok.Data;

@Data
public class StatementQueryValue {

	private final Object object;

	private final StatementCondition condition;
}
