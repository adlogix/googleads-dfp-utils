Google Ads Dfp Utils
====================

[![Build Status](https://travis-ci.org/adlogix/googleads-dfp-utils.png?branch=master)](https://travis-ci.org/adlogix/googleads-dfp-utils)

Google Ads Dfp Utils is a set of utility classes which allows on top of the googleads-java-lib to retrieve very easily information from DFP

## Installing

### With Maven

You can use Maven by including the library:

```xml
<dependency>
    <groupId>eu.adlogix.com.google.api.ads.dfp</groupId>
    <artifactId>googleads-dfp-utils</artifactId>
    <version>0.0.9</version>
</dependency>
```

## Examples

### Find AdUnits

```java

// Initialization of the DfpFinderFactory class with the utility service class of DFP
DfpFinderFactory dfpFinderFactory = new DfpFinderFactory(dfpServices, dfpSession);

//Get the AdUnitFinder from the dfpFinderFactory
AdUnitFinder<AdUnit,StatementBuilder> adUnitFinder = dfpFinderFactory.getAdUnitFinder();

// Find the Effective Root AdUnit
AdUnit rootAdUnit = adUnitFinder.findRoot();

// Find one AdUnit
AdUnit adUnit = adUnitFinder.findById("123456");

// Find AdUnits for a given list
List<AdUnit> adUnits = adUnitFinder.findByIds(Arrays.asList(new String[] { "111", "222" }));

// Find all AdUnits by parentId
List<AdUnit> adUnits = adUnitFinder.findAllByParentId("123456");

// Fine all AdUnits
List<AdUnit> adUnits = adUnitFinder.findAll();

// Custom
// Initialization of the DfpStatementBuilderCreatorFactory (This can be initialized once and reused)
DfpStatementBuilderCreatorFactory statementBuilderCreatorFactory=new DfpStatementBuilderCreatorFactory();

AdUnitStatementBuilderCreator<StatementBuilder> statementBuilderCreator = statementBuilderCreatorFactory.getAdUnitStatementBuilderCreator();

StatementBuilder statementBuilder = statementBuilderCreator
        .withStatus("ACTIVE", StatementCondition.EQUAL)
        .withLastModifiedDateTime(new DateTime(), StatementCondition.GREATER_OR_EQUAL)
        .toStatementBuilder();

List<AdUnit> adUnits = adUnitFinder.findByStatementBuilder(statementBuilder);
```

### Find LineItems

```java

// Initialization of the DfpFinderFactory class with the utility service class of DFP
DfpFinderFactory dfpFinderFactory = new DfpFinderFactory(dfpServices, dfpSession);

//Get the LineItemFinder from the dfpFinderFactory
LineItemFinder<LineItem, StatementBuilder> lineItemFinder = dfpFinderFactory.getLineItemFinder();
		
// Find one LineItem
LineItem lineItem = lineItemFinder.findById(123456l);

// Custom
// Initialization of the DfpStatementBuilderCreatorFactory (This can be initialized once and reused)
DfpStatementBuilderCreatorFactory statementBuilderCreatorFactory=new DfpStatementBuilderCreatorFactory();

LineItemStatementBuilderCreator<StatementBuilder> statementBuilderCreator= statementBuilderCreatorFactory.getLineItemStatementBuilderCreator();

StatementBuilder statementBuilder = statementBuilderCreator
                .withId(123456l, StatementCondition.EQUAL)
                .withLastModifiedDateTime(new DateTime(), StatementCondition.GREATER_OR_EQUAL)
                .toStatementBuilder();

List<LineItem> lineItems = lineItemFinder.findByStatementBuilder(statementBuilder);
```

### Find Orders

```java

// Initialization of the DfpFinderFactory class with the utility service class of DFP
DfpFinderFactory dfpFinderFactory = new DfpFinderFactory(dfpServices, dfpSession);

//Get the OrderFinder from the dfpFinderFactory
OrderFinder<Order,StatementBuilder> orderFinder = dfpFinderFactory.getOrderFinder();

// Find one Order
Order order = orderFinder.findById(123456l);

// Custom
// Initialization of the DfpStatementBuilderCreatorFactory (This can be initialized once and reused)
DfpStatementBuilderCreatorFactory statementBuilderCreatorFactory=new DfpStatementBuilderCreatorFactory();

OrderStatementBuilderCreator<StatementBuilder> statementBuilderCreator= statementBuilderCreatorFactory.getOrderStatementBuilderCreator();

StatementBuilder statementBuilder = statementBuilderCreator
                .withId(123456l, StatementCondition.EQUAL)
                .withLastModifiedDateTime(new DateTime(), StatementCondition.GREATER_OR_EQUAL)
                .toStatementBuilder();

List<Order> orders = orderFinder.findByStatementBuilder(statementBuilder);
```

### Find Placements

```java

// Initialization of the DfpFinderFactory class with the utility service class of DFP
DfpFinderFactory dfpFinderFactory = new DfpFinderFactory(dfpServices, dfpSession);

//Get the PlacementFinder from the dfpFinderFactory
PlacementFinder<Placement, StatementBuilder> placementFinder= dfpFinderFactory.getPlacementFinder();

// Find one Placement
Placement placement = placementFinder.findById(123456l);

// Custom

// Initialization of the DfpStatementBuilderCreatorFactory (This can be initialized once and reused)
DfpStatementBuilderCreatorFactory statementBuilderCreatorFactory=new DfpStatementBuilderCreatorFactory();

PlacementStatementBuilderCreator<StatementBuilder> statementBuilderCreator= statementBuilderCreatorFactory.getPlacementStatementBuilderCreator();

StatementBuilder statementBuilder = statementBuilderCreator
                .withId(123456l, StatementCondition.EQUAL)
                .withLastModifiedDateTime(new DateTime(), StatementCondition.GREATER_OR_EQUAL)
                .toStatementBuilder();

List<Placement> placements = placementFinder.findByStatementBuilder(statementBuilder);
```
