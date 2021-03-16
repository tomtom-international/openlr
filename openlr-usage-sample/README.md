# OpenLR Usage Sample

## Introduction

The following is a little code sample showing how to use the [OpenLR Java reference implementation](https://github.com/tomtom-international/openlr)
to encode and decode OpenLR location references on a map.

## Getting started

In order to use the OpenLR reference implementation, the library should be included in your project. The library is published as maven
artifacts. To use the reference implementation, include the following artifacts in your project:

```
<dependency>
    <groupId>org.openlr</groupId>
    <artifactId>encoder</artifactId>
    <version>1.4.3</version>
</dependency>
<dependency>
    <groupId>org.openlr</groupId>
    <artifactId>decoder</artifactId>
    <version>1.4.3</version>
</dependency>
<dependency>
    <groupId>org.openlr</groupId>
    <artifactId>binary</artifactId>
    <version>1.4.3</version>
</dependency>
```

## Map connector

The OpenLR encoder and decoder work with a map connector interface. This map connector interface provides the encoder and decoder
with the map information needed to perform encoding and decoding. A user of the reference implementation wishing to encode or decode OpenLR
locations references on their map should implement this map connector interface for their map.

The code sample here shows a simple map connector implementation for a little map snippet stored as [GeoJSON](https://geojson.org/).
This simple map connector is `org.openlr.sample.map.SampleMapDatabase` and implements the `openlr.map.MapDatabase` interface.

## Using the encoder and decoder

Once the specific map connector has been implemented, the user can use the API to encode and decode location references on
their map. In the tests portion of the project are some unit tests showing how to use the encoder and decoder API in conjunction
with the map connector.  The tests work off a tiny [OSM](https://www.openstreetmap.org/) map snippet as GeoJSON stored in the test resources directory.

The `org.openlr.sample.EncodeTest` shows how to encode OpenLR location references for paths and positions in the map.
The `org.openlr.sample.DecodeTest` shows how to decode OpenLR location references to paths and positions in the map.
The code has been commented extensively. 

## Running the sample

The sample project uses Java 8. To compile the project and run the tests with maven:

```shell script
mvn test
```