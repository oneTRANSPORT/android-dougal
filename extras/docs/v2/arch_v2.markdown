OneM2M API
==========
Android SDK Exploration
-----------------------

### Overview

So that Android app developers have a quick way to develop the OneM2M
interactions in their software, we would like to provide a software
development kit in the form of an Android library that can be included in
apps.

The SDK will manage communications between the app as an Application Entity
and a Common Services Entity, as well as providing higher level abstractions
for functionality like registration or discovery.

There are two application programming interfaces that we would like to offer,
a lower level, object-oriented API that mirrors the structure of resources
maintained on a CSE directly, and a higher-level abstraction in the form of
an Android content provider that deals in tabular data like a SQL database.

Which API an app developer would use would depend on how the data provided by
the CSE would integrate into the app.  To retrieve and use a single value from
a container, using the object-oriented API to make a single query would be
the simplest way to go.

However, if a stream of values from a group of containers was to be injected
into an adapter-backed UI component like a list or grid, then reading that
data from a content provider allows the developer to use nice features like
preservation of cursors between configuration changes (such as screen
rotation).

#### Network layer

In either case, we plan to use a library such as OkHttp
(http://square.github.io/okhttp) to manage HTTP connections with the CSE.
This would save a lot of development time, as it supports Android versions
all the way back to Gingerbread, has compression support, response caching if
needed and allows for both blocking and asynchronous requests.

One downside is that it does not yet support client certificates as a form of
authentication.  If we are prepared to accept HTTP basic authentication over
HTTPS, then we can use this library.

If support for client certificates is seen as essential for the Android SDK,
then we would either have to fork OkHttp and add support, or start again with
our own network layer.  Probably the first option would be quickest, but
either way would take a while to implement.

There are four request cases that must be supported by the network layer.

1. A blocking request where the response is returned as part of the same TCP
connection.
2. An asynchronous version of 1.  This would be asynchronous to the calling
application, but the communication with the CSE would still be blocking at a
network level.
3. A blocking request where instead of the response, the CSE sends back an
acknowledgement and a reference to where the response will be when it is
ready.
4. An asynchronous version of 3.

In cases 3 and 4 above, there is the possibility of the app wanting to cancel
processing of any result that is not immediately available.  So it probably
makes sense to hand back control to the caller rather than have the SDK wait
on an acknowledgement from the CSE and then poll for a result itself.

The SDK could provide a helper routine that, when given the URI of a
not-yet-available resource, would start a background service to poll the CSE
for the resource.

### Object-oriented API

Android application software is written in Java, and since that is an
object-oriented language then one API would consist of passing and retrieving
data as objects across the interface to a CSE.  CSE resources map directly to
Java objects, for example a container would have its own class definition and
mechanism for serialising and deserialising into and from a JSON or XML
representation.

#### Serialisation

Generally the serialisation of JSON data requires less memory and CPU
resources, and therefore battery energy, than serialising XML.  So on an
Android client we will use JSON in CSE communications.

To turn objects into JSON and vice-versa efficiently, the plan is to use a
Google library called Gson.  This can take a Java object that has its member
fields annotated with JSON identifiers and creates a JSON string from it.
Gson can also take a JSON string and parse it to produce an object
representation.

A container class member field definition would look a bit like this:

        @SerializedName("cbs")
        private long currentByteSize;
        @SerializedName("cni")
        private int currentNumberOfInstances;
        @SerializedName("mbs")
        private long maxByteSize;
        @SerializedName("mia")
        private long maxInstanceAge;
        @SerializedName("mni")
        private int maxNumberOfInstances;
        @SerializedName("st")
        private String stateTag;

The Gson annotation `@SerializedName` denotes the JSON label that will be
applied to the value of the annotated variable when the class is serialised.
So we will end up with a JSON string that looks like this:

        {
          "cbs":...,
          "cni":...,
          "mbs":...,
          "mia":...,
          "mni":...,
          "st:"..."
        }

So that the application developer can inject values into the class and
retrieve values set by the CSE, each variable has a Java getter and setter as
usual.  It's clearly beneficial for an application developer to use
`getMaxInstanceAge()` than to have to remember the meaning of the JSON label
"mia".

#### CSE requests

Resource objects all extend from a `BaseResource` superclass.  This contains
attributes common to all resources such as resourceId, resourceName and
creationTime.  The superclass also contains routines to provide GET, POST,
PUT and DELETE HTTP methods.

As a result, resource objects can create and execute their own CSE requests.
They also run discovery requests and can search CSEs for other resources.

In order that requests are serialised correctly according to the
specification, resources need to be encapsulated in a `RequestHolder` that looks
like this:

        public class RequestHolder {

            @SerializedName("m2m:ae")
            private ApplicationEntity applicationEntity;
            @SerializedName("m2m:cin")
            private ContentInstance contentInstance;
            @SerializedName("m2m:cnt")
            private Container container;
            ...
        }

So to create a request body for POSTing a container, the Java container object
needs to be referenced by the `RequestHolder.container` member field.  All other
member fields are null.  Then Gson can serialise the request holder as follows:

        {
          "m2m:cnt":{
            "cbs":...,
            "cni":...,
            "mbs":...,
            "mia":...,
            "mni":...,
            "st:"..."
          }
        }

Similarly, responses are returned from the CSE in a `ResponseHolder` object.
Only one of the sub-object slots is full in any given response and this gives
the correct deserialisation from the JSON string.

        public class ResponseHolder {

            @SerializedName("m2m:ae")
            private ApplicationEntity applicationEntity;
            @SerializedName("m2m:cnt")
            private DataContainer dataContainer;
            @SerializedName("m2m:cin")
            private ContentInstance contentInstance;
            @SerializedName("m2m:discovery")
            private Discovery discovery;
            ...
        }

The response holder also encapsulates the CSE status code and other HTTP
headers.

#### Filters

Requests for regular resources may contain filters to restrict the number of
resources returned by the CSE.  This isn't much of a problem on the request
side, where the filter criteria parameter of the request needs to be present,
including filters like `createdAfter` or `sizeBelow`.

However, discovery using filters, for example, will return a discovery
resource depending on the type of resource that passes the filter.  We will
need to correctly deserialise each of the possibilities.  It may make sense
for the SDK to spawn further requests to recover the actual resources when
a list of identifiers is returned from the filter request.

A response to the request to retrieve the list of `userSleepData` content
instances as used by our Commuter Alarm app, might look like this:

    {"m2m:discovery":{
      "discoveredURI":
        "/ONETCSE01/BUCKS-OXON-TRANSPORT/userSleepData/cin_20160126T142158_6790_588703085_nm 
         /ONETCSE01/BUCKS-OXON-TRANSPORT/userSleepData/cin_20160126T142943_1990_1980171536_nm 
         /ONETCSE01/BUCKS-OXON-TRANSPORT/userSleepData/cin_20160126T143100_2010_30197557_nm 
         /ONETCSE01/BUCKS-OXON-TRANSPORT/userSleepData/cin_20160126T143333_2052_1822293153_nm",
      "truncated":false
      }
    }

It might be a nice feature for the SDK to then acquire all of the resources in
the list and return four `UserSleepData` objects back to the calling app.  We
would probably make that an option, just in case the number of returned URIs
was large and we didn't have the time to retrieve all resources.

#### Groups

It is possible to instruct a CSE to organise a list of resources into a
group that can be manipulated as one new resource.  To do this also
requires the support of a virtual resource that is a child of the group
called the `fanOutPoint`.  Any requests to the fan out point are applied to
each member in the group and the results are returned in an aggregated
response object.

Again, the request side is not too much of a problem, but we will have to
support the aggregations that are returned.

#### Partial resources

The oneM2M specification allows for partial resources to be sent in requests
or returned from the CSE.  A partial resource has missing fields, even ones
that may be mandatory according to the XML schema definition.  The only
requirement is that the fields that are present must contain valid data.

One of the benefits of using Gson for serialisation and deserialisation is
that it does not mind missing fields going in either direction.  So it will be
more of an app development issue, where the calling routine must be aware that
it might not have access to the whole resource.

#### Subscription and notification

A resource such as a container can have a child that is a subscription
resource.  This specifies when an AE should receive notifications about events
pertaining to the parent resource.

Creating a subscription is already covered by the usual create resource
mechanism in the SDK.  The question is, how will notifications be delivered to
an Android device endpoint?

Google's cloud messaging system can send a notification to a specific device,
presumably with the oneM2M SDK installed.  It would be possible for a CSE to
create an interface to GCM and send notifications to devices depending on
subscriptions that had been created.

These notifications are at a user level, rather than being M2M.  The user will
see a notification pop-up in the status bar at the top of the screen, and the
payload will not be delivered until the user drags the notification open and
clicks on it.  Then the SDK can take delivery of the message and do something
with the contents.

But the user could decide to dismiss the notification without executing the
action, and it would be lost to the SDK.  In many cases, when the user clicks
on a notification, the corresponding app starts up in some way---presumably
apps based on the SDK would fetch the subscribed-to resource from the CSE and
present that to the user.

Perhaps a simple use case for subscriptions and notifications could be a user
telling a parking app to notify them when a bookable parking space becomes
available in High Wycombe near the library.  When the event occurs, they
receive a cloud message, pop open the app and book parking.

There's a nice value-add for the app developer here when using oneM2M.  The
CSE and SDK between them take care of all the registration and dealings with
GCM and the developer simply has to create a subscription object on the
required resource, maybe with a callback for the user clicking on the
notification.

#### Long polling

CSEs also support another type of notification for devices that are behind
NAT'ed firewalls, which is the situation with mobile devices.  By creating a
`pollingChannel` resource on the CSE, the device can open a network connection
to this resource and the CSE will block until a notification is ready to be
delivered before sending a response.

The TCP connection may eventually time out before a notification is ready, in
which case the caller has to recreate the connection.

How practical this is on a mobile device probably depends on the connection
type, and what happens when the handset goes to sleep.  On a wifi network, it
may be possible to keep open a long-polling connection without using much
battery power, but it is not clear that this would be the case over GSM.

Perhaps it could be an option for times when the app is open and visible.
There could be a navigation app that would update in real time with traffic
or other transport data, all of which would only be useful when actually using
the app.

#### Registration and deregistration

In order to consume services from a CSE, an application has to first register
with the CSE.  A question we still have to answer is at what point in the
Android app lifecycle this should occur.

When the app is first started, a unique installation id can be created and
stored permanently in the app's private storage.  It will persist until the
app is uninstalled and can be used to generate part of the AE-ID and AE-NAME
if those are not supplied by the CSE.

One option would be to register the app with a CSE at first start-up.  This
would be efficient, but there is a problem that Android apps do not get a
chance to tidy up when they are uninstalled.  So there would be no
opportunity to deregister tidily with the CSE.  We need to understand what
happens when an AE just goes away.  Does the CSE delete it automatically after
a period of inactivity?

On app uninstallation, the install id is of course lost and can never be
recovered, so a subsequent install cannot tidy up after the previous one.

Unfortunately we have the same problem if we try to register on each app
start-up and deregister when the app terminates.  There's no way to get a
callback before the app is removed from phone memory, so deregistration would
not be possible.

The solution seems to be to allow the CSE to automatically deregister an app
after a period of inactivity.  On start-up, an app can check that registration
still exists, and if not then can re-register.

### Content provider API

Right from the beginning, the Android framework has exposed a content provider
interface for storing and retrieving app data.  Content providers work with
tabular data, like SQL tables, and return cursors containing data that can be
injected into activities, fragments and adapters via cursor loaders.

Although the hierarchical or named containers and content instances stored on
the CSE don't map directly to any tabular data structure, it is quite possible
that single containers or groups of containers could form rows of a table that
could be returned in a cursor.

In one scheme, a content instance could contain a JSON representation of a
database row.  Older content instances would form more rows.  Selecting
oldest or latest for example, would return one row.  Querying for a range of
values, perhaps over a time interval for example, will give a set of rows
returned.

Alternatively, each content instance could form a table cell, and a group of
containers yields a row of content instance values.

Clearly more advanced SQL operations like joins will not be possible on the
CSE.  However, returned data could be injected into an in-memory Sqlite table
or set of tables and then operations like join, sort and group by, would be
possible.

#### Performance


