Feature: Verification Requests

  Background:
    * url baseUrl + "/v1/otp-verifications"

  Scenario: Create verification and send otp
    * def channelId = "test-channel"
    Given header correlation-id = "dfc73919-ee54-4444-809e-7aa3793f6b1d"
    And header channel-id = channelId
    And request
      """
      {
        "contextId": "2a3559bd-0071-4bbf-8901-42b9f17dd93f",
        "deliveryMethodId": "c9959188-969e-42f3-8178-42ef824c81d3"
      }
      """
    When method POST
    Then status 201
    And match response ==
      """
      {
        "id": "81e11840-140e-45ac-a6af-40aa653a146e",
        "contextId": "2a3559bd-0071-4bbf-8901-42b9f17dd93f",
        "protectSensitiveData": true,
        "created": "#notnull",
        "expiry": "#notnull",
        "complete": false,
        "activity": {
          "name": "online-purchase",
          "merchantName": "Amazon",
          "reference": "ABC123",
          "cost": {
            "amount": 10.99,
            "currency": "GBP"
          },
          "card": {
            "number": "4929111111111111",
            "expiry": "2025-12"
          },
          "timestamp": "2020-06-06T12:36:15.179Z"
        },
        "deliveryMethod": {
          "id":"c9959188-969e-42f3-8178-42ef824c81d3",
          "type":"sms",
          "value":"+4407808247743",
          "lastUpdated": "2020-09-29T06:49:59.960Z",
          "eligibility": {
            "eligible": true
          }
        },
        "config": {
          "duration": 300000,
          "maxNumberOfAttempts": 3,
          "passcodeConfig": {
            "duration": 120000,
            "length": 8,
            "maxNumberOfDeliveries": 2
          }
        },
        "deliveries": {
          "max": 2,
          "values": [
            {
              "method": {
                "lastUpdated": "2020-09-29T06:49:59.960Z",
                "eligibility": {
                  "eligible": true
                },
                "id": "#uuid",
                "type": "sms",
                "value": "+4407808247743"
              },
              "messageId": "#uuid",
              "message": {
                "text": "Use one time code 00000001 to make a payment of GBP 10.99 to Amazon with card ending 1111. Never share this code with anyone.",
                "passcode": {
                  "created": "#notnull",
                  "expiry": "#notnull",
                  "value": "00000001"
                }
              },
              "sent": "#notnull"
            }
          ]
        },
        "successful": false
      }
      """