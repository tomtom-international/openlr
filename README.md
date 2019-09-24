# openlr
----------

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/7d866929be7c43cdae32dac4be3eaa6f)](https://www.codacy.com/app/rijnb/openlr?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=tomtom-international/openlr&amp;utm_campaign=Badge_Grade)
[![Build Status](https://img.shields.io/travis/tomtom-international/openlr.svg?branch=master)](https://travis-ci.org/tomtom-international/openlr.svg?branch=master)
[![Coverage Status](https://coveralls.io/repos/github/tomtom-international/openlr/badge.svg)](https://coveralls.io/github/tomtom-international/openlr)
[![License](http://img.shields.io/badge/license-APACHE2-blue.svg)]()
[![Release](https://img.shields.io/github/release/tomtom-international/openlr.svg?maxAge=3600)](https://github.com/tomtom-international/openlr/releases)

## Installation
----------------

To build all the modules go to the project folder:

```maven
mvn clean install
```

To build modules invidually go to module folder:


```maven
mvn clean install
```


## Usage
---------

### 9 Modules:
#### OpenLR - parent: OpenLR - open standard for location referencing
#### OpenLR - encoder:	The encoder package holds the reference implementation for the OpenLR encoder. It takes a (map-dependent) location as input and generates a corresponding (map-agnostic) location reference. This package uses the OpenLR map package and the OpenLR data package.
#### OpenLR - map:	The map package consists of the map interface and map tools being used by the OpenLR encoder and decoder. The user of this package needs to implement the required methods and needs to translate the internal data structure to the OpenLR map interface.
#### OpenLR - maploader-tt-sqlite:	This library provides an access layer which enables OpenLR to use TomTom Digital Maps in SQLite Format.
#### OpenLR - decoder:	The decoder package holds the reference implementation for the OpenLR decoder. It takes a (map-agnostic) location reference as input and finds back a corresponding (map-dependent) location reference. This package uses the OpenLR map package and the OpenLR binary package
#### OpenLR - data:	The OpenLR data package comprises interfaces for OpenLR location references and also interfaces for encoding and decoding the internal data into a defined physical format and the other way round.
#### OpenLR - xml:	OpenLR XML physical format
#### OpenLR - binary:	The binary package consists of classes for reading and writing binary location reference data. The OpenLR encoder uses this package to create a binary representation of a location reference. The OpenLR decoder uses this package to receive and decode binary location reference data.
#### OpenLR - datex2:	OpenLR - open standard for location referencing
OpenLR
