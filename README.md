[This document is now out of date]
==================================

Currently incomplete, proof-of-concept only
===========================================

There is some basic functionality for discovery of data containers and content
instances.  It is also possible to upload CIs.

This is the source structure:

        +-- SDK.java
        |
        +-- network
        |     |
        |     +-- RI.java
        |     |
        |     +-- request
        |     |     |
        |     |     +-- BaseRequest.java
        |     |     |
        |     |     +-- RequestHolder.java
        |     |
        |     +-- response
        |           |
        |           +-- ResponseHolder.java
        |
        +-- resource
              |
              +-- ApplicationEntity.java
              |
              +-- BaseResource.java
              |
              +-- CommonServicesEntity.java
              |
              +-- ContentInstance.java
              |
              +-- DataContainer.java
              |
              +-- Discovery.java

SDK.java
--------

The top-level class is currently a singleton although actually it doesn't need to be
and a better option would probably be to make it static at some point.

It does need to persist a unique request identifier, but the incrementing counter
used at the moment may not be the best way of generating a unique id.

### Public methods

* __SDK getInstance()__

    Returns the single instance of the SDK.

* __String getUniqueRequestId()__

    Returns a new unique request identifier.

The last three methods provide GET, POST and DELETE HTTP requests.

* __ResponseHolder getResource(Context context, RI ri, Map&lt;String, List&lt;String&gt;&gt; propertyValues)__

    Given an Android context, a resource identifier (see below) and a list of headers,
__getResource__ executes an HTTPS GET request and returns the resource if available.

* __ResponseHolder postResource(Context context, RI ri, Map&lt;String, List&lt;String&gt;&gt; propertyValues, String body)__

    Similar to GET above, the the POST method allows a message body to be included in
the package sent to the server.

* __ResponseHolder deleteResource(Context context, RI ri, Map&lt;String, List&lt;String&gt;&gt; propertyValues)__

    The HTTPS DELETE method asks the server to remove a resource.

These methods are implemented around a BaseRequest that uses Android's HttpUrlConnection
class to create a secure channel to the remote end.  However, I suggest that we should
replace this with the request class from OkHttp.

The downside with OkHttp is that it is harder to set up insecure connections that are
helpful for testing.  For example, the domain name configuration on the test server does
not meet OkHtp's security requirements, so we cannot use it.  However, fixing the server
DNS will be a lot simpler than duplicating the functionality of OkHttp ourselves.

RI.java
-------

This class provides a simple formatting constructor for a resource identifier.  We
should expand it to cover all the possible cases.

### Methods

* __RI(String fqdn, String ri)__

    The constructor takes a fully-qualified domain name (or null) and a path to a
resource and creates a valid string resource.

BaseRequest.java
----------------

By implementing a trust manager that allows all certificates to pass, this class can create possibly insecure HTTPS connections, useful for test servers.

### Methods

* __BaseRequest(Context context, int pkcs12Resource, String url, String method, Map&lt;String, List&lt;String&gt;&gt; propertyValues, String body)__

    Each request class is good for one request only, so the constructor initialises
member variables that cannot be altered later.

* __int connect()__

    Makes the request to the server using HttpUrlConnection.  Currently, basic
authentication credentials are hard-coded, so we need to fix that.  Connection and
read time-outs are set to 1500 milliseconds, but perhaps they should be longer as the
server is timing out on large POSTs.  The call is synchronous and a response code is
returned.

* __String getResponseText()__

    Provides the mechanism to get the body of a response after a server request.

* __Map&lt;String, List&lt;String&gt;&gt; getHeaderMap()__

   Returns the list of headers in the response, correctly preserving multiple lines
with the same key, although that is unlikely to happen often.

RequestHolder.java
------------------

We use Gson to serialise and deserialise objects and JSON data.  This class creates
the correct hierarchy of objects so that Gson produces a JSON string with the right
labels at each level.

### Member fields

    @Expose
    @SerializedName("m2m:ae")
    private ApplicationEntity applicationEntity;
    @Expose
    @SerializedName("m2m:cin")
    private ContentInstance contentInstance;
    @Expose
    @SerializedName("m2m:cnt")
    private DataContainer dataContainer;

The annotation `@Expose` tells Gson that it should serialise this field, other fields
are ignored.  `@SerializedName` provides the label that will go into the JSON string
for this object.  Serialisation then proceeds recursively into whichever objects are
not null.

So for example, suppose we assign an instance of ContentInstance to the second member
field.  ContentInstance has these fields:

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

So when Gson serialises the RequestHolder object, the JSON string will look like
this:

    {"m2m:cin":{
      "cnf":"...",
      "con":"...",
      "cs":"...",
      "st":"..."
      }
    }

The RequestHolder will only contain one of an ApplicationEntity, ContentInstance or DataContainer, and the other fields will be null.

### Methods

There are setters for each of the nested objects:

* __void setApplicationEntity(ApplicationEntity applicationEntity)__

* __void setContentInstance(ContentInstance contentInstance)__

* __void setDataContainer(DataContainer dataContainer)__

And also methods for manipulating request headers (properties):

* __void putOriginProperty(String origin)__

    Sets the X-M2M-Origin header.

* __void putNameProperty(String name)__

    Sets the X-M2M-NM header.

* __void putContentTypeProperty(String contentType)__

    Sets the Content-Type header.

* __HashMap&lt;String, List&lt;String&gt;&gt; getPropertyValues()__

    Returns the list of properties set for this request.

ResponseHolder.java
-------------------

In the same way that the RequestHolder contains nested objects so that Gson can
correctly serialise into JSON data expected by the server, the ResponseHolder is a
container for objects deserialised by Gson from the response body.

### Member fields

    @SerializedName("m2m:cb")
    private CommonServicesEntity commonServicesEntity;
    @SerializedName("m2m:ae")
    private ApplicationEntity applicationEntity;
    @SerializedName("m2m:cnt")
    private DataContainer dataContainer;
    @SerializedName("m2m:cin")
    private ContentInstance contentInstance;
    @SerializedName("m2m:discovery")
    private Discovery discovery;

The responses from the server can be any of the resources, CSE, AE, DC, CI and also a
discovery object.  Only one of these fields will be set in a given ResponseHolder, and
the others will be set to null.

### Methods

There are getters for each of the components and also the returned HTTP status code.

* __int getStatusCode()__

* __CommonServicesEntity getCommonServicesEntity()__

* __ApplicationEntity getApplicationEntity()__

* __DataContainer getDataContainer()__

* __ContentInstance getContentInstance()__

* __Discovery getDiscovery()__

BaseResource.java
-----------------

The superclass of all resources contains common fields that may be used in any of its
extended classses.  There are no constraints on which fields may be set, as this will
be determined by the response from the server.

### Member fields

    @Expose
    @SerializedName("ri")
    private String resourceId;
    @Expose
    @SerializedName("rn")
    private String resourceName;
    @Expose
    @SerializedName("ty")
    private String resourceType;
    @Expose
    @SerializedName("pi")
    private String parentId;
    @Expose
    @SerializedName("ct")
    private String creationTime;
    @Expose
    @SerializedName("lt")
    private String lastModifiedTime;
    @Expose
    @SerializedName("et")
    private String expiryTime;
    @Expose
    @SerializedName("acpi")
    private String[] accessControlPolicyIds;
    @Expose
    @SerializedName("lbl")
    private String[] labels;

### Methods

There are many getters and setters for the member fields, which we'll skip for brevity.
Also we have static methods for HTTP GET, POST and so on.  This design needs to be
revised, as Java does not allow overriding of static methods.

The method postCin exists because of this, as posting a new ContentInstance requires
an extra XML wrapper.  We will clean up this design in a future release, as it was
a mistake to give this responsibility to the base class.  However, it does mean that
in the short term the Commuter Alarm app can submit new ContentInstances to
oneTransport.

* __static ResponseHolder get(Context context, RI ri, String aeId)__

* __static ResponseHolder post(Context context, RI ri, RequestHolder requestHolder)__

* __static ResponseHolder postCin(Context context, RI ri, RequestHolder requestHolder)__

* __static ResponseHolder delete(Context context, RI ri, String aeId)__

ApplicationEntity.java
----------------------

Entity classes all extend BaseResource and provide customised methods for operations
like create, get and delete.  These also form part of Gson's serialisation and
deserialisation inputs and outputs, so have annotated member variables corresponding
to JSON labels.  All the fields in BaseResource are also available to Gson, of course.

### Member fields

    @Expose
    @SerializedName("aei")
    private String id;
    @Expose
    @SerializedName("api")
    private String applicationId;
    @Expose
    @SerializedName("rr")
    private boolean requestReachable;

### Methods

* __static ApplicationEntity get(Context context, String fqdn, String cseName, String aeName, String aeId)__

* __static ApplicationEntity create(Context context, String fqdn, String cseName, String aeName, String aeId)__

* __static int delete(Context context, String fqdn, String cseName, String aeName, String aeId)__

* __static Discovery discoverAll(Context context, String fqdn, String cseName, String aeId)__

CommonServicesEntity.java
-------------------------

At this point, we don't actually need to call any of the methods on the
CommonServicesEntity class.  If you know the CSE name then there is no need to
instantiate this object.

### Member fields

    @SerializedName("csi")
    private String id;
    @SerializedName("cst")
    private String type;
    @SerializedName("poa")
    private String[] pointsOfAccess;
    @SerializedName("srt")
    private int[] supportedResourceTypes;

### Methods

* __static CommonServicesEntity get(Context context, String fqdn, String cseName, String aeId)__

ContentInstance.java
--------------------

This object is what you need for sending data to a oneM2M server or retrieving content
from it.

### Member fields

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

### Methods

* __static ContentInstance getByName(Context context, String fqdn, String cseName, String aeName, String dcName, String ciName, String aeId)__

* __static ContentInstance getLast(Context context, String fqdn, String cseName, String aeName, String dcName, String aeId)__

* __static ContentInstance create(Context context, String fqdn, String cseName, String aeName, String dcName, String aeId, String content)__

* __static Discovery discoverByDc(Context context, String fqdn, String cseName, String aeName, String dcName, String aeId)__

* __static Discovery discoverByAe(Context context, String fqdn, String cseName, String aeName, String aeId)__

* __static Discovery discoverAll(Context context, String fqdn, String cseName, String aeId)__

DataContainer.java
------------------

### Member fields

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

### Methods

* __static DataContainer create(Context context, String fqdn, String cseName, String aeName, String dcName, String aeId)__

* __static DataContainer getByName(Context context, String fqdn, String cseName, String aeName, String dcName, String aeId)__

* __static Discovery discoverByAe(Context context, String fqdn, String cseName, String aeName, String aeId)__

* __static Discovery discoverAll(Context context, String fqdn, String cseName, String aeId)__


