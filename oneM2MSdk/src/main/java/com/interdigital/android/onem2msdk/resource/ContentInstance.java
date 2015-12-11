package com.interdigital.android.onem2msdk.resource;

import android.content.Context;

import com.google.gson.annotations.SerializedName;
import com.interdigital.android.onem2msdk.network.RI;

public class ContentInstance extends BaseResource {

    private static final String LAST_CI = "la";

    @SerializedName("cnf")
    private String contentInfo;
    @SerializedName("con")
    private String content;
    @SerializedName("cs")
    private long contentSize;
    @SerializedName("st")
    private String stateTag;

    public static ContentInstance getByName(Context context, String fqdn,
                                            String cseName, String aeName, String dcName, String ciName, String aeId) {
        RI ri = new RI(fqdn, cseName + "/" + aeName + "/" + dcName + "/" + ciName);
        return get(context, ri, aeId).getContentInstance();
    }

    public static ContentInstance getLast(Context context, String fqdn,
                                          String cseName, String aeName, String dcName, String aeId) {
        RI ri = new RI(fqdn, cseName + "/" + aeName + "/" + dcName + "/" + LAST_CI);
        return get(context, ri, aeId).getContentInstance();
    }

    public static Discovery discoverByDc(Context context,
                                         String fqdn, String cseName, String aeName, String dcName, String aeId) {
        RI ri = new RI(fqdn, cseName + "/" + aeName + "/" + dcName + "?fu=1&rty=4");
        return discover(context, ri, aeId);
    }

    public static Discovery discoverByAe(Context context,
                                         String fqdn, String cseName, String aeName, String aeId) {
        RI ri = new RI(fqdn, cseName + "/" + aeName + "?fu=1&rty=4");
        return discover(context, ri, aeId);
    }

    public static Discovery discoverAll(Context context, String fqdn, String cseName, String aeId) {
        RI ri = new RI(fqdn, cseName + "?fu=1&rty=4");
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
