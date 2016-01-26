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
used at the moment is not thread safe so a better way of generating a unique id
should be used.

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


