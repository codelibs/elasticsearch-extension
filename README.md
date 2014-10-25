Elasticsearch Extension Pugin
=======================

## Overview

This plugin extends Elasticsearch's features to modify them by your plugin.
For example, this plugin has EngineFilter to insert your programe into InternalEngine.

## Version

| Version   | Elasticsearch |
|:---------:|:-------------:|
| master    | 1.4.X         |
| 1.4.1     | 1.4.0.Beta1   |

### Issues/Questions

Please file an [issue](https://github.com/codelibs/elasticsearch-extension/issues "issue").
(Japanese forum is [here](https://github.com/codelibs/codelibs-ja-forum "here").)

## Installation

### Install Extension Plugin

    $ $ES_HOME/bin/plugin --install org.codelibs/elasticsearch-extension/1.4.1

## Reference

### EngineFilter

[EngineFilter](https://github.com/codelibs/elasticsearch-extension/blob/master/src/main/java/org/codelibs/elasticsearch/extension/filter/EngineFilter.java "EngineFitler") is an interface to filter InternalEngine.

The sample implementation is:
* [QueryResultCachePlugin](https://github.com/codelibs/elasticsearch-qrcache/blob/elasticsearch-qrcache-1.4.1/src/main/java/org/codelibs/elasticsearch/qrcache/QueryResultCachePlugin.java "QueryResultCachePlugin"): Register RefreshEngineFilter
* [RefreshEngineFilter](https://github.com/codelibs/elasticsearch-qrcache/blob/elasticsearch-qrcache-1.4.1/src/main/java/org/codelibs/elasticsearch/qrcache/filter/RefreshEngineFilter.java "RefreshEngineFilter"): EngineFilter implementation

