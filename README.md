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
    <version>0.0.5</version>
</dependency>
```

## Examples

### Find AdUnits

```java

// Initialization of the Finder class if you cached the services
InventoryServiceInterface inventoryService =
    dfpServices.get(session, InventoryServiceInterface.class);
    
NetworkServiceInterface networkService =
	dfpServices.get(session, NetworkServiceInterface.class)

AdUnitFinder adUnitFinder = new AdUnitFinder(inventoryService, networkService);

// Initialization of the Finder class with the utility service class of DFP
AdUnitFinder adUnitFinder = new AdUnitFinder(dfpServices, dfpSession);

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
StatementBuilder statementBuilder = new AdUnitStatementBuilderCreator()
                .withStatus("ACTIVE", StatementCondition.EQUAL)
                .withLastModifiedDateTime(new DateTime(), StatementCondition.GREATER_OR_EQUAL)
                .toStatementBuilder();

List<AdUnit> adUnits = adUnitFinder.findByStatementBuilder(statementBuilder);
```

### Find LineItems

```java
// Initialization of the Finder class if you cached the services
LineItemServiceInterface lineItemService =
    dfpServices.get(session, LineItemServiceInterface.class);

LineItemFinder lineItemFinder = new LineItemFinder(lineItemService);

// Initialization of the Finder class with the utility service class of DFP
LineItemFinder lineItemFinder = new LineItemFinder(dfpServices, dfpSession);

// Find one LineItem
LineItem lineItem = lineItemFinder.findById(123456l);

// Custom
StatementBuilder statementBuilder = new LineItemStatementBuilderCreator()
                .withId(123456l, StatementCondition.EQUAL)
                .withLastModifiedDateTime(new DateTime(), StatementCondition.GREATER_OR_EQUAL)
                .toStatementBuilder();

List<LineItem> lineItems = lineItemFinder.findByStatementBuilder(statementBuilder);
```

### Find Orders

```java
// Initialization of the Finder class if you cached the services
OrderServiceInterface orderService =
    dfpServices.get(session, OrderServiceInterface.class);

OrderFinder orderFinder = new OrderFinder(orderService);

// Initialization of the Finder class with the utility service class of DFP
OrderFinder orderFinder = new OrderFinder(dfpServices, dfpSession);

// Find one Order
Order order = orderFinder.findById(123456l);

// Custom
StatementBuilder statementBuilder = new OrderStatementBuilderCreator()
                .withId(123456l, StatementCondition.EQUAL)
                .withLastModifiedDateTime(new DateTime(), StatementCondition.GREATER_OR_EQUAL)
                .toStatementBuilder();

List<Order> orders = orderFinder.findByStatementBuilder(statementBuilder);
```

### Find Placements

```java
// Initialization of the Finder class if you cached the services
PlacementServiceInterface placementService =
    dfpServices.get(session, PlacementServiceInterface.class);

PlacementFinder placementFinder = new PlacementFinder(placementService);

// Initialization of the Finder class with the utility service class of DFP
PlacementFinder placementFinder = new PlacementFinder(dfpServices, dfpSession);

// Find one Placement
Placement placement = placementFinder.findById(123456l);

// Custom
StatementBuilder statementBuilder = new OrderStatementBuilderCreator()
                .withId(123456l, StatementCondition.EQUAL)
                .withLastModifiedDateTime(new DateTime(), StatementCondition.GREATER_OR_EQUAL)
                .toStatementBuilder();

List<Placement> placements = placementFinder.findByStatementBuilder(statementBuilder);
```
