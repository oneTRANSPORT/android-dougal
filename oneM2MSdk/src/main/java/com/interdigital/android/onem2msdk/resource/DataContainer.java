package com.interdigital.android.onem2msdk.resource;

import android.content.Context;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.interdigital.android.onem2msdk.network.RI;
import com.interdigital.android.onem2msdk.network.request.RequestHolder;
import com.interdigital.android.onem2msdk.network.response.ResponseHolder;

public class DataContainer extends BaseResource {

    @Expose
    @SerializedName("cbs")
    private long currentByteSize;
    @Expose
    @SerializedName("cni")
    private int currentNumberOfInstances;
    @Expose
    @SerializedName("mbs")
    private long maxByteSize;
    @Expose
    @SerializedName("mia")
    private long maxInstanceAge;
    @Expose
    @SerializedName("mni")
    private int maxNumberOfInstances;
    @Expose
    @SerializedName("st")
    private String stateTag;

    public static DataContainer create(Context context,
                                       String fqdn, int port, String cseName, String aeName, String dcName, String aeId) {
        RI ri = new RI(fqdn, port, "/" + cseName + "/" + aeName);
        DataContainer dataContainer = new DataContainer();
        dataContainer.setResourceName("cnt");
        dataContainer.setLabels(new String[]{"TestLabel1"});
        RequestHolder requestHolder = new RequestHolder();
        requestHolder.setDataContainer(dataContainer);
        requestHolder.putOriginProperty(aeId);
        requestHolder.putContentTypeProperty("application/json; ty=3");
        requestHolder.putNameProperty(dcName);
        ResponseHolder responseHolder = post(context, ri, requestHolder);
        if (responseHolder != null) {
            return responseHolder.getDataContainer();
        }
        return null;
    }

    public static DataContainer getByName(Context context,
                                          String fqdn, int port, String cseName, String aeName, String dcName, String aeId) {
        RI ri = new RI(fqdn, port, cseName + "/" + aeName + "/" + dcName);
        return get(context, ri, aeId).getDataContainer();
    }

    public static Discovery discoverByAe(Context context,
                                         String fqdn, int port, String cseName, String aeName, String aeId) {
        RI ri = new RI(fqdn, port, cseName + "/" + aeName + "?fu=1&rty=3");
        return discover(context, ri, aeId);
    }

    public static Discovery discoverAll(Context context, String fqdn, int port, String cseName, String aeId) {
        RI ri = new RI(fqdn, port, cseName + "?fu=1&rty=3");
        return discover(context, ri, aeId);
    }

    public long getCurrentByteSize() {
        return currentByteSize;
    }

    public void setCurrentByteSize(long currentByteSize) {
        this.currentByteSize = currentByteSize;
    }

    public int getCurrentNumberOfInstances() {
        return currentNumberOfInstances;
    }

    public void setCurrentNumberOfInstances(int currentNumberOfInstances) {
        this.currentNumberOfInstances = currentNumberOfInstances;
    }

    public long getMaxByteSize() {
        return maxByteSize;
    }

    public void setMaxByteSize(long maxByteSize) {
        this.maxByteSize = maxByteSize;
    }

    public long getMaxInstanceAge() {
        return maxInstanceAge;
    }

    public void setMaxInstanceAge(long maxInstanceAge) {
        this.maxInstanceAge = maxInstanceAge;
    }

    public int getMaxNumberOfInstances() {
        return maxNumberOfInstances;
    }

    public void setMaxNumberOfInstances(int maxNumberOfInstances) {
        this.maxNumberOfInstances = maxNumberOfInstances;
    }

    public String getStateTag() {
        return stateTag;
    }

    public void setStateTag(String stateTag) {
        this.stateTag = stateTag;
    }
}

//{"m2m:cnt":{
//        "cbs":3275,
//        "cni":5,
//        "ct":"20151118T171858",
//        "et":"20151122T043858",
//        "lbl":["myContainerInCCURL"],
//        "lt":"20151118T171858",
//        "mbs":60000000,
//        "mia":1600,
//        "mni":4,
//        "pi":"C-BUCKS-IMPORT",
//        "ri":"cnt_20151118T171858_7",
//        "rn":"carpark",
//        "st":284,
//        "ty":3}
//}
