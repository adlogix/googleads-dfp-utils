package eu.adlogix.com.google.api.ads.dfp.v201505;

import static org.testng.Assert.assertEquals;

import java.util.Arrays;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.api.ads.dfp.axis.v201505.Statement;

import eu.adlogix.com.google.api.ads.dfp.StatementCondition;

public class AdUnitStatementBuilderCreatorTest {

	@Test(dataProvider = "statementBuilderCreatorDataProvider")
	public void statementBuilderShouldCreateQueriesCorrectly(final AdUnitStatementBuilderCreator creator,
			final String expectedQuery, final Integer expectedValuesLength) {
		Statement statement = creator.toStatementBuilder().toStatement();
		assertEquals(statement.getQuery(), expectedQuery);
		assertEquals(statement.getValues().length, (int) expectedValuesLength);

	}

	@DataProvider(name = "statementBuilderCreatorDataProvider")
	public Object[][] statementBuilderCreatorDataProvider() {

		return new Object[][] { { providerItem_withId_equal(), "WHERE id = :id", 1 },
				{ providerItem_withId_not_equal(), "WHERE id != :id", 1 },
				{ providerItem_withIds(), "WHERE id IN (Hello,World)", 0 } };

	}

	private AdUnitStatementBuilderCreator providerItem_withId_equal() {
		return new AdUnitStatementBuilderCreator().withId("test_id", StatementCondition.EQUAL);
	}

	private AdUnitStatementBuilderCreator providerItem_withId_not_equal() {
		return new AdUnitStatementBuilderCreator().withId("test_id", StatementCondition.NOT_EQUAL);
	}

	private AdUnitStatementBuilderCreator providerItem_withIds() {
		return new AdUnitStatementBuilderCreator().withIds(Arrays.asList(new String[] { "Hello", "World" }));
	}
}