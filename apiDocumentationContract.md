# API contracts


## Bulk order placing API

__Request endpoint__

`PUT /api/farmers`
     
URL: `http://localhost:8887/api/farmers`

__Request body__

```json
{
  "farm_id": "975eebdd-b9fa-493b-ac55-273383b02c86",
  "orders": [
    {
      "order_start_date": "2020-02-01T06:10:11",
      "duration": 1
    },
    {
      "order_start_date": "2020-02-02T06:10:11",
      "duration": 2
    },
    {
      "order_start_date": "2020-02-03T06:10:11",
      "duration": 3
    },
    {
      "order_start_date": "2020-02-04T06:10:11",
      "duration": 4
    },
    {
      "order_start_date": "2020-02-05T06:10:11",
      "duration": 5
    },
    {
      "order_start_date": "2020-01-06T06:10:11",
      "duration": 6
    }
  ]
}
```
__Response body__

```json
{
  "http_status": "Created",
  "http_response_code": 201,
  "request_date": "2019-12-30T21:47:28.224908",
  "water_delivery_orders": [
    {
      "order_id": "ebe6356a-bdbf-4e86-98b7-82d5c1c8ed1e",
      "delivery_status": "Requested",
      "delivery_start_date": "2020-01-01T06:10:11",
      "delivery_end_date": "2020-01-01T07:10:11"
    },
    {
      "order_id": "ca64ae4a-8217-463b-83ae-ff04fdfe0d96",
      "delivery_status": "Requested",
      "delivery_start_date": "2020-01-02T06:10:11",
      "delivery_end_date": "2020-01-02T08:10:11"
    }
  ]
}
```

## Querying existing orders API by FarmId: Collection of orders

__Request endpoint__

`GET /api/farmers/{farmId}`

For example, `/api/farmers/975eebdd-b9fa-493b-ac55-273383b02c86`
URL: `http://localhost:8887/api/farmers/975eebdd-b9fa-493b-ac55-273383b02c86`

__Response body__

```json
{
  "http_status": "OK",
  "http_response_code": 200,
  "request_date": "2019-12-30T22:26:03.005567",
  "water_delivery_orders": [
    {
      "order_id": "9312a335-75ac-4804-8d7d-64d28b411c28",
      "delivery_status": "Cancelled",
      "delivery_start_date": "2019-01-05T06:10:11",
      "delivery_end_date": "2019-01-05T11:10:11"
    },
    {
      "order_id": "c3a4e80f-beb0-4321-93fd-a7fe8d78fd01",
      "delivery_status": "Delivered",
      "delivery_start_date": "2019-12-30T00:25:01",
      "delivery_end_date": "2019-12-30T22:25:01"
    }
  ]
}
```

## Querying an existing order API by FarmId and orderId: Single order item

__Request endpoint__

`GET /api/farmers/{farmId}/orders/{orderId}`

For example, `/api/farmers/975eebdd-b9fa-493b-ac55-273383b02c86/orders/c3a4e80f-beb0-4321-93fd-a7fe8d78fd01`

URL: `http://localhost:8887/api/farmers/975eebdd-b9fa-493b-ac55-273383b02c86/orders/c3a4e80f-beb0-4321-93fd-a7fe8d78fd01`

__Response body__

```json
{
  "http_status": "OK",
  "http_response_code": 200,
  "request_date": "2019-12-30T22:58:54.256481",
  "water_delivery_orders": [
    {
      "order_id": "c3a4e80f-beb0-4321-93fd-a7fe8d78fd01",
      "delivery_status": "Delivered",
      "delivery_start_date": "2019-12-30T00:25:01",
      "delivery_end_date": "2019-12-30T22:25:01"
    }
  ]
}
```

## Bulk order cancellation API

__Request endpoint__

     `PUT /api/farmers?cancel=true`

__Request body__

    ```json
    {
        "farm_id": "975eebdd-b9fa-493b-ac55-273383b02c86",
        "orders": [
          "1334d383-78a1-4cd9-ac9d-c5faf980d6b4", 
          "9ef5c0f7-1d1f-4955-a051-ce9ba1eb2812", 
          "736038b4-481a-41a7-96d6-be9f3fd95fc4"
        ]
    }
    ```

