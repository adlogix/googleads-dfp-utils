package eu.adlogix.com.google.api.ads.dfp.v201802;

import com.google.api.ads.dfp.axis.utils.v201802.StatementBuilder;

import eu.adlogix.com.google.api.ads.dfp.BaseStatementBuilderCreatorFactory;
import eu.adlogix.com.google.api.ads.dfp.domain.DfpVersion;

public class DfpStatementBuilderCreatorFactory extends BaseStatementBuilderCreatorFactory<StatementBuilder> {

	public DfpStatementBuilderCreatorFactory() {
		super(DfpVersion.V_201802);
	}

}
