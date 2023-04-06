# Charlene's Coffee Corner

Application to print the receipts of customer orders.

## Implementation plan

_Possible Elements:_
* _Main_ - main program
* _OrderService_ - service, which collects the products into the order
* _PrintService_ - service, which prints the receipt of customer order
* _Order_ - customer's order (collection of offers)
* _Offer_ - offer or product with size (optional) and price, which customer can select into the order
* _Extra_ - extra component with the price, which can be attached to particular offer
* _Bonus_ - discount rules to be applied to the customer order
* _Receipt_ - printable customer's order receipt

## Build application

This is a _Java_ application built with _Gradle_. 
Java 17 is required to build and run this application.
To build application execute following command:
```shell
./gradlew clean build
```

## Run application

This application can be run with _Gradle_ as following:
```shell
./gradlew run
```
