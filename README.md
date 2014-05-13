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
    <version>0.0.4</version>
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
// Get the LineItemService.
LineItemServiceInterface lineItemService =
    dfpServices.get(session, LineItemServiceInterface.class);

LineItemFinder lineItemFinder = new LineItemFinder(lineItemService);

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
// Get the OrderService
OrderServiceInterface orderService =
    dfpServices.get(session, OrderServiceInterface.class);

OrderFinder orderFinder = new OrderFinder(orderService);

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
// Get the PlacementService
PlacementServiceInterface placementService =
    dfpServices.get(session, PlacementServiceInterface.class);

PlacementFinder placementFinder = new PlacementFinder(placementService);

// Find one Placement
Placement placement = placementFinder.findById(123456l);

// Custom
StatementBuilder statementBuilder = new OrderStatementBuilderCreator()
                .withId(123456l, StatementCondition.EQUAL)
                .withLastModifiedDateTime(new DateTime(), StatementCondition.GREATER_OR_EQUAL)
                .toStatementBuilder();

List<Placement> placements = placementFinder.findByStatementBuilder(statementBuilder);
```
