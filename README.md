Google Ads Dfp Utils
====================

Google Ads Dfp Utils is a set of utility classes which allows on top of the googleads-java-lib to retrieve very easily information from DFP

## Installing

### With Maven

You can use Maven by including the library:

```xml
<dependency>
    <groupId>eu.adlogix.com.google.api.ads.dfp</groupId>
    <artifactId>googleads-dfp-utils</artifactId>
    <version>0.0.1</version>
</dependency>
```

## Examples

### Find AdUnits

```java
// Get the InventoryService.
InventoryServiceInterface inventoryService =
    dfpServices.get(session, InventoryServiceInterface.class);

AdUnitFinder adUnitFinder = new AdUnitFinder(inventoryService);

// Find the Root AdUnit
AdUnit rootAdUnit = adUnitFinder.findRoot();

// Find one AdUnit
AdUnit adUnit = adUnitFinder.findById("123456");

// Find all AdUnits by parentId
List<AdUnit> adUnits = adUnitFinder.findAllByParentId("123456");

// Fine all AdUnits
List<AdUnit> adUnits = adUnitFinder.findAll();

// Custom
StatementBuilder statementBuilder = new AdUnitStatementBuilderCreator()
                .withStatus("ACTIVE", StatementCondition.EQUAL)
                .withLastModifiedDateTime(new DateTime(), StatementCondition.GREATER_OR_EQUAL)
                .toStatementBuilder();

List<AdUnit> adUnits = adUnitFinder.findByStatementBuilder(statementBuilder);
```






