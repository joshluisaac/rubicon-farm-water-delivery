

## Application layers

The application is made of up three core layers.

[businessactivities](src/main/java/com/rubiconwater/codingchallenge/joshluisaac/businessactivities)

## Specifications
### Order record  - specification
Order record is the representation of an order request. It has the following attributes

1. `id`: A identifier which uniquely identifies this record making it different from others.
2. `dateReceived`: Represented as a [LocalDateTime](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/time/LocalDateTime.html). 
   This is the date the order was created. It is autogenerated on the back-end and formatted as `yyyy-MM-dd HH:mm:ss` when serialized into JSON.
   
   
### Order record  - invariants
The following invariants and preconditions must be met for `Order` to be in a valid state.
1. `orderStartDate` cannot be in the past
2. `supplyDuration` must be non-negative
   
   
## Order state diagram
1. A newly created order is flagged as `OrderStatus.REQUESTED`.
2. It is flagged as `OrderStatus.IN_PROGRESS` when the order is being delivered.Delivered in this context is defined as any period that falls between
`orderStartDate + supplyDuration`. Please note that the `Order` has an invariant on checking the value of `supplyDuration` for it to be in a valid state. 
3. 