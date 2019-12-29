# API contracts


##Querying an order API

## Bulk order cancellation API

* __Request endpoint__

     `PUT /api/farmers?cancel=true`

* __Request body__

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
* __Response body__


* Bulk order cancellation server-side logs

The following will be written to log file when 