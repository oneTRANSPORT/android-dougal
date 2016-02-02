package com.interdigital.android.onem2msdk.resource;

import android.content.Context;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.interdigital.android.onem2msdk.network.RI;
import com.interdigital.android.onem2msdk.network.request.RequestHolder;
import com.interdigital.android.onem2msdk.network.response.ResponseHolder;

public class ContentInstance extends BaseResource {

    private static final String LAST_CI = "la";

    @Expose
    @SerializedName("cnf")
    private String contentInfo;
    @Expose
    @SerializedName("con")
    private String content;
    @Expose
    @SerializedName("cs")
    private long contentSize;
    @Expose
    @SerializedName("st")
    private String stateTag;

    public static ContentInstance getByName(Context context, String fqdn, int port,
                                            String cseName, String aeName, String dcName, String ciName, String aeId) {
        RI ri = new RI(fqdn, port, cseName + "/" + aeName + "/" + dcName + "/" + ciName);
        return get(context, ri, aeId).getContentInstance();
    }

    public static ContentInstance getLast(Context context, String fqdn, int port,
                                          String cseName, String aeName, String dcName, String aeId) {
        RI ri = new RI(fqdn, port, cseName + "/" + aeName + "/" + dcName + "/" + LAST_CI);
        return get(context, ri, aeId).getContentInstance();
    }


    public static ContentInstance create(Context context, String fqdn, int port,
                                         String cseName, String aeName, String dcName, String aeId, String content) {
        RI ri = new RI(fqdn, port, cseName + "/" + aeName + "/" + dcName);
        ContentInstance contentInstance = new ContentInstance();
        // TODO Is this needed?
//        contentInstance.setContentInfo("application/json:0");
        contentInstance.setContent(content);
        RequestHolder requestHolder = new RequestHolder();
        requestHolder.setContentInstance(contentInstance);
        requestHolder.putOriginProperty(aeId);
//        requestHolder.putContentTypeProperty("application/json; ty=4");
        requestHolder.putContentTypeProperty("application/vnd.onem2m-res+xml; ty=4");
        // TODO Assume an anonymous CI name for now.
        // The server will create a new CI name and add it to the data container.
//        requestHolder.putNameProperty(ciName);
        ResponseHolder responseHolder = postCin(context, ri, requestHolder);
        if (responseHolder == null) {
            return null;
        }
        return responseHolder.getContentInstance();
    }

    public static Discovery discoverByDc(Context context,
                                         String fqdn, int port, String cseName, String aeName, String dcName, String aeId) {
        RI ri = new RI(fqdn, port, cseName + "/" + aeName + "/" + dcName + "?fu=1&rty=4");
        return discover(context, ri, aeId);
    }

    public static Discovery discoverByAe(Context context,
                                         String fqdn, int port, String cseName, String aeName, String aeId) {
        RI ri = new RI(fqdn, port, cseName + "/" + aeName + "?fu=1&rty=4");
        return discover(context, ri, aeId);
    }

    public static Discovery discoverAll(Context context, String fqdn, int port, String cseName, String aeId) {
        RI ri = new RI(fqdn, port, cseName + "?fu=1&rty=4");
        return discover(context, ri, aeId);
    }

    public String getContentInfo() {
        return contentInfo;
    }

    public void setContentInfo(String contentInfo) {
        this.contentInfo = contentInfo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getContentSize() {
        return contentSize;
    }

    public void setContentSize(long contentSize) {
        this.contentSize = contentSize;
    }

    public String getStateTag() {
        return stateTag;
    }

    public void setStateTag(String stateTag) {
        this.stateTag = stateTag;
    }

}

//{"m2m:cin":{
//        "cnf":"text/plain:0",
//        "con":"0",
//        "cs":1,
//        "ct":"20151211T115813",
//        "et":"20151214T231813",
//        "lt":"20151211T115813",
//        "pi":"cnt_20151127T101353_104207",
//        "ri":"cin_20151211T115813_194",
//        "rn":"cin_20151211T115813_195201512111158_1975461145_nm",
//        "st":2508,
//        "ty":4}
//}
