import groovy.json.JsonSlurper
import groovy.json.JsonOutput

req_json = ''' 
{
  "val": [
    {
      "custNumber": "Z10000",
      "custID": "1000",
      "custName": "Jack",
      "custType": "Private"
    },
    {
      "custNumber": "Z20000",
      "custID": "2000",
      "custName": "Tina",
      "custType": "Private"
    },
    {
      "custNumber": "B31000",
      "custID": "3100",
      "custName": "ACME Holdings",
      "custType": "Business"
    },
    {
      "custNumber": "B898900",
      "custID": "4100",
      "custName": "LG Holdings",
      "custType": "Business"
    }
  ]
} 
'''

resp_json = '''
{
  "SELECT_FROM_DB_response": {
    "row": [
      {
        "CUSTNO": "Z10000",
        "PRODUCTID": "K11000",
        "PRODUCTNAME": "KEYBOARD",
        "PRODUCTSTATUS": "Shipped",
      },
      {
        "CUSTNO": "Z20000",
        "PRODUCTID": "MO53000",
        "PRODUCTNAME": "MOUSE",
        "PRODUCTSTATUS": "Processing",
      },
      {
        "CUSTNO": "B31000",
        "PRODUCTID": "MT99500",
        "PRODUCTNAME": "MONITOR",
        "PRODUCTSTATUS": "Delivered",
      }
    ]
  }
}
'''
def mReqOrders = new JsonSlurper().parseText(req_json)
def mRespOrders = new JsonSlurper().parseText(resp_json)
Set finalJsonSet = []

def mReqBatchNumbers = [:]
def list 
for (entry in mReqOrders)
    {
        list.add($entry.key = "custNumber")
    }
//print ("Type: " + mReqOrders.getClass())
//print (mReqOrders.val[0])