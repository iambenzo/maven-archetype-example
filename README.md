# Webflux-Archetype

Generate the scaffolding for a Spring Webflux API which connects to an embedded Mongo DB.

## Installation

Clone/Download this repository and run the following command:

```sh
mvn clean install
```

From there, you can run the generation command:

```sh
mvn archetype:generate \
"-DarchetypeGroupId=com.iambenzo" \
"-DarchetypeArtifactId=webflux-crud-archetype"
```

## Parameters

|Parameter|Example Value|Description|
|:---|:---|:---|
|archetypeGroupId|com.iambenzo|Should always use this value|
|archetypeArtifactId|mvc-quickstart|Should always use this value|
|groupId|com.iambenzo||
|artifactId|dave-api-example|App name|
|version|1.0.0|App Version|
|entityName|Dave|Used to create Object names (Use Capital letter at start)|
|entityNameLowerCase|dave|used for parameter names (Use camelCase for multiple words)|
|includeCreate|true|Include a create resource (true or false)|
|includeRead|true|Include a read resource (true or false)|
|includeUpdate|true|Include an update resource (true or false)|
|includeDelete|true|Include a delete resource (true or false)|
|includeEvent|true|Include an event resource (true or false)|
