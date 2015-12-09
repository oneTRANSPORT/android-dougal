//package com.interdigital.android.onem2msdk;
//
//import android.content.Context;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.HashMap;
//
//public class CommonServicesEntity extends BaseResource {
//
//    private String id;
//    private String[] pointsOfAccess;
//    private ApplicationEntity[] applicationEntities;
//    private String[] accessControlPolicyIDs;
//
//
//    // Retrieve synchronously.
//    public CommonServicesEntity(Context context, RI ri) throws JSONException {
//        super(context);
//        HashMap<String, String> propertyValues = new HashMap<>();
//        propertyValues.put("X-M2M-RI", String.valueOf(SDK.getInstance().getUniqueRequestId()));
//        propertyValues.put("X-M2M-Origin", "C_ANDROID");
//        getFromServer(ri, propertyValues);
//decodeResponse();
//    }
//
//    private void decodeResponse() throws JSONException {
//        JSONObject json=new JSONObject(getResponseText());
//
//    }
//}
//
//// REQUEST
////CSE_BASE_URL=https://onet-cse-01.cloudapp.net
////        WGET_OPT="-S --no-check-certificate --private-key=client0/client.nopwd.key --certificate=client0/client.crt"
////        CSE_ID=ONET-CSE-01
////        CSE_NAME=ONETCSE01
////        AE_ID="C-URL"
////        AE_NAME="CURL"
////        wget $WGET_OPT \
////        --header="X-M2M-RI: xyz1" \
////        --header="X-M2M-Origin: $AE_ID" \
////        --header="Accept: application/json" \
////        --save-headers=on \
////        "$CSE_BASE_URL/$CSE_ID"
//
//// RESPONSE
////{"m2m:cb":                                                CSE_BASE
////        {"acpi":["/ONET-CSE-01Acp"],     ACCESS_CONTROL_POLICY_IDS
////        "csi":"ONET-CSE-01",                     CSE_ID
////        "cst":1,                                                CSE_TYPE
////        "ct":"20151118T094448",                CREATION_TIME
////        "lbl":["ONET-CSE-01"],                   ??? LABELS (from members)
////        "lt":"20151118T094448",                 LAST_MODIFIED_TIME
////        "poa":["http://127.0.0.1:9011"],     POINT_OF_ACCESS
////        "ri":"ONET-CSE-01",                        RESOURCE_ID
////        "rn":"ONETCSE01",                          RESOURCE_NAME
////        "srt":[1,2,3,4,9,11,12,13,14,15,16,17,18,19,20,23,10001,
//// 10002,10003,10004,10009,10013,10014,10016,10018],  SUPPORTED_RESOURCE_TYPE
////        "ty":5}                                                 RESOURCE_TYPE (from parameters)
////        }
//
////<?xml version="1.0"?>
////<m2m:cb xmlns:m2m="http://www.onem2m.org/xml/protocols" rn="ONETCSE01">
////<ty>5</ty>
////<ri>ONET-CSE-01</ri>
////<pi/>
////<ct>20151118T094448</ct>
////<lt>20151118T094448</lt>
////<lbl>ONET-CSE-01</lbl>
////<acpi>/ONET-CSE-01Acp</acpi>
////<cst>1</cst>
////<csi>ONET-CSE-01</csi>
////<srt>1 2 3 4 9 11 12 13 14 15 16 17 18 19 20 23 10001 10002 10003 10004 10009 10013 10014 10016 10018</srt>
////<poa>http://127.0.0.1:9011</poa>
////</m2m:cb>
