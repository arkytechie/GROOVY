import groovy.json.JsonSlurper
import groovy.json.JsonOutput

/*
    */

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
      "custType": "Business2"
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
      },
            {
        "CUSTNO": "B31000",
        "PRODUCTID": "MT1122890",
        "PRODUCTNAME": "NEW_MONITOR",
        "PRODUCTSTATUS": "Yet to be dispatched",
      }
    ]
  }
}
'''
def mReqOrders = new JsonSlurper().parseText(req_json)
def mRespOrders = new JsonSlurper().parseText(resp_json)
Set finalJsonSet = []

def reqRespMatchCount = 0
mReqOrders.val.each { reqOrder -> 
                        mRespOrders.SELECT_FROM_DB_response.row.each { 
                          responseOrder ->
                                if (reqOrder.custNumber == responseOrder.CUSTNO) {
                                    finalJsonSet << [
                                    order_custNumber: responseOrder.CUSTNO,
                                    order_custID: reqOrder.custID,
                                    order_custName: reqOrder.custName, 
                                    order_custType: reqOrder.custType,
                                    order_productID: responseOrder.PRODUCTID,
                                    order_productNAME: responseOrder.PRODUCTNAME,
                                    order_productSTATUS: responseOrder.PRODUCTSTATUS
                                ]
                            reqRespMatchCount += 1
                        }                      
                    }
                    if (reqRespMatchCount == 0) {
                                finalJsonSet << [
                                order_custNumber: reqOrder.custNumber,
                                order_custID: reqOrder.custID,
                                order_custName: reqOrder.custName, 
                                order_custType: reqOrder.custType,
                                order_productID: "not found",
                                order_productNAME: "not found",
                                order_productSTATUS: "not found"
                                ]       
                        }
                    else {
                          reqRespMatchCount = 0
                          }
                }
        //Convert the Set to JSON Format    
        finalJson = JsonOutput.prettyPrint(JsonOutput.toJson(finalJsonSet))
        println (finalJson)