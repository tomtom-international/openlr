#1. Overview
In this document, we're going to focus on how to encode and decode OpenLR locations 
on digital maps using modules in this package.

#2. SetUp
We'll need to add the following dependencies to our pom.xml
```xml
<dependency>
 <groupId>org.openlr</groupId>
 <artifactId>encoder</artifactId>
 <version>${openlr.version}</version>
</dependency>
```
```xml
<dependency>
 <groupId>org.openlr</groupId>
 <artifactId>decoder</artifactId>
 <version>${openlr.version}</version>
</dependency>
```
```xml
<dependency>
 <groupId>org.openlr</groupId>
 <artifactId>map</artifactId>
 <version>${openlr.version}</version>
</dependency>
```
```xml
<dependency>
 <groupId>org.openlr</groupId>
 <artifactId>data</artifactId>
 <version>${openlr.version}</version>
</dependency>
```
```xml
<dependency>
 <groupId>org.openlr</groupId>
 <artifactId>binary</artifactId>
 <version>${openlr.version}</version>
</dependency>
```
```xml
<dependency>
 <groupId>org.openlr</groupId>
 <artifactId>xml</artifactId>
 <version>${openlr.version}</version>
</dependency>
```

```xml
<dependency>
 <groupId>org.openlr</groupId>
 <artifactId>proto</artifactId>
 <version>${openlr.version}</version>
</dependency>
```

#3. Map Implementation

**Implement the following interfaces against the map you want to use**

<ul>
<li>openlr.map.Line</li>
<li>openlr.map.Node</li>
<li>openlr.map.MapDatabase</li>
<li>openlr.map.loader.MapLoadParameter</li>
<li>openlr.map.loader.OpenLRMapLoader</li>
</ul> 

#4 Load Map

<p> We are using the sqlite database for the remaining part of the document </p>

```java
MapLoadParameter param = new DBFileNameParameter();
param.setValue("path/to/map");
OpenLRMapLoader loader = new SQLiteMapLoader();
MapDatabase map = loader.load(Arrays.asList(param));
```

#6. Physical Format

<p>Choose the physical format of the OpenLR location reference container<br>
The following three formats are available in this package:</p>
<ul>
<li>binary: openlr.binary</li>
<li>protobuf: openlr.proto</li>
<li>xml: openlr.xml</li>
</ul>

<p>We are choosing binary format for the remaining part of the document</p>

#5. Encoder

<p>The code snippet given below shows how to encode a line location on the map in OpenLR binary format.</p>

<p>you can find the default encoder properties file in openlr/encoder/src/main/resources/OpenLR-Encoder-Properties.xml
</p>

***In the example given below we are encoding a line location with connected lines (1,2,3,4) ***

```java
List<Line>  testLocation = Arrays.asList(mapDatabaseAdapter.getLine(1),
                                         mapDatabaseAdapter.getLine(2),
                                         mapDatabaseAdapter.getLine(3),
                                         mapDatabaseAdapter.getLine(4));
Location location = LocationFactory.createLineLocation("Test location", testLocation,0,0);
PhysicalEncoder physicalEncoder = new OpenLRBinaryEncoder();
Configuration encoderConfig = OpenLRPropertiesReader.loadPropertiesFromFile(new File("OpenLR-Encoder-Properties.xml"));
OpenLREncoderParameter params = new OpenLREncoderParameter.Builder().with(map).with(encoderConfig)
                .with(Arrays.asList(physicalEncoder))
                .buildParameter();
OpenLREncoder encoder = new openlr.encoder.OpenLREncoder();
LocationReferenceHolder locationReferenceHolder = encoder.encodeLocation(params, location);
String locationReferenceBinary = ((ByteArray) physicalEncoder.encodeData(locationReferenceHolder.getRawLocationReferenceData()).getLocationReferenceData()).getBase64Data();
```

#6. Decoder

<p>The code snippet given below shows how to decode an OpenLR binary location reference on to a map.</p>
<p>you can find the default encoder properties file in /Users/babub/scratch/TomTom_GitRepo/openlr/decoder/src/main/resources/OpenLR-Decoder-Properties.xml</p>

***In the example given below we are decoding OpenLR location reference binary string ***

```java
String openlr = "CwmQ9SVWJS2qBAD9/14tCQ==";
OpenLRBinaryDecoder binaryDecoder = new OpenLRBinaryDecoder();
ByteArray byteArray = new ByteArray(Base64.getDecoder().decode(openlr));
LocationReferenceBinaryImpl locationReferenceBinary = new LocationReferenceBinaryImpl("Test location", byteArray);
RawLocationReference rawLocationReference = binaryDecoder.decodeData(locationReferenceBinary);
Configuration decoderConfig = OpenLRPropertiesReader.loadPropertiesFromFile(new File(TestMapStubTest.class.getClassLoader().getResource("OpenLR-Decoder-Properties.xml").getFile()));
OpenLRDecoderParameter params = new OpenLRDecoderParameter.Builder().with(map).with(decoderConfig).buildParameter();
OpenLRDecoder decoder = new openlr.decoder.OpenLRDecoder();
Location location = decoder.decodeRaw(params, rawLocationReference);
```