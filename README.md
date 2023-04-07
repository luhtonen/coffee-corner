# Charlene's Coffee Corner

Application to print the receipts of customer orders.

## Implementation plan

_Possible Elements:_
* _Main_ - main program
* _OrderService_ - service, which collects the products into the order
* _PrintService_ - service, which prints the receipt of customer order
* _Order_ - customer's order (collection of products)
* _Product_ - product or product with size (optional) and price, which customer can select into the order
* _Extra_ - extra component with the price, which can be attached to particular product
* _Bonus_ - discount rules to be applied to the customer order
* _Receipt_ - printable customer's order receipt

## Receipt layout
```text

      🅲🅷🅰🆁🅻🅴🅽🅴❜🆂 🅲🅾🅵🅵🅴🅴 🅲🅾🆁🅽🅴🆁                                                                  

----------------------------------------------------
 Product               Amnt   Price    Disc   Total

 Coffee (small)           2    2.50   -2.50    2.50
 Coffee (large)           1    3.50            3.50
  + Extra milk            1    0.30   -0.30    0.00
 Coffee (medium)          1    3.00            3.00
  + Foamed milk           1    0.50            0.50
  + Special roast         1    0.90            0.90
 Bacon Roll               1    4.50            4.50
 Freshly squeezed orange
 juice                    1    3.95            3.95

 TOTAL CHF                                    18.85
----------------------------------------------------
 07.04.2023                                17:26:35
```

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
