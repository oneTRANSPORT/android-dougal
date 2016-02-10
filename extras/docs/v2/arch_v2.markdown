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
the CSE will integrate into the app.  To retrieve and use a single value from
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

Regular resources / announced
Filters
Announcements / notifications
Variations of CRUD by resource:
  ContentInstance cannot be updated
  Request cannot be created or updated by API
  Group?
  Fan out point
  Polling channel Uri cannot be created
  Virtual resources like oldest, latest

Registration / deregistration


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


