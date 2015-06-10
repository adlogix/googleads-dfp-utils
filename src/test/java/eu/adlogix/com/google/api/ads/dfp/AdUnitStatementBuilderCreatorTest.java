package eu.adlogix.com.google.api.ads.dfp;

import java.util.Arrays;

import org.testng.AssertJUnit;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.api.ads.dfp.axis.utils.v201505.StatementBuilder;
import com.google.api.ads.dfp.axis.v201505.Statement;

import eu.adlogix.com.google.api.ads.dfp.domain.DfpVersion;


public class AdUnitStatementBuilderCreatorTest {

	@Test(dataProvider = "statementBuilderCreatorDataProvider")
	public void statementBuilderShouldCreateQueriesCorrectly(
			final AdUnitStatementBuilderCreator<StatementBuilder> creator,
			final String expectedQuery, final Integer expectedValuesLength) {
		Statement statement = (creator.toStatementBuilder()).toStatement();
		AssertJUnit.assertEquals(statement.getQuery(), expectedQuery);
		AssertJUnit.assertEquals(statement.getValues().length, (int) expectedValuesLength);

	}

	@DataProvider(name = "statementBuilderCreatorDataProvider")
	public Object[][] statementBuilderCreatorDataProvider() {

		return new Object[][] { { providerItem_withId_equal(), "WHERE id = :id", 1 },
				{ providerItem_withId_not_equal(), "WHERE id != :id", 1 },
				{ providerItem_withIds(), "WHERE id IN (Hello,World)", 0 } };

	}

	private AdUnitStatementBuilderCreator<StatementBuilder> providerItem_withId_equal() {
		return new AdUnitStatementBuilderCreator<StatementBuilder>(DfpVersion.V_201505).withId("test_id", StatementCondition.EQUAL);
	}

	private AdUnitStatementBuilderCreator<StatementBuilder> providerItem_withId_not_equal() {
		return new AdUnitStatementBuilderCreator<StatementBuilder>(DfpVersion.V_201505).withId("test_id", StatementCondition.NOT_EQUAL);
	}

	private AdUnitStatementBuilderCreator<StatementBuilder> providerItem_withIds() {
		return new AdUnitStatementBuilderCreator<StatementBuilder>(DfpVersion.V_201505).withIds(Arrays.asList(new String[] {
				"Hello", "World" }));
	}
}