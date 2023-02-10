# Senegal Code Generator

## The Senegal process

TODO

## Subjects to modulate an idea as code 

````




                                                                + - - - - - - - - + 
  + - - - - - - - - + <------  parent/enclosing concept  -------| Concept (Child) |
  | Concept         |                                           + - - - - - - - - +
  + - - - - - - - - + <-----------+
                                  |                                     + - - - - - - - +
                                  +--  parent/enclosing concept  -------| Facet         |
                                                                        + - - - - - - - +                                          
                                                                               ^ 
                                                                               | 
                                                                               | 
                               +-------  purpose depends on this facet  -------+                                     
                               | 
                               | 
                               | 
                       + - - - - - - - - +                + - - - - - - - - +
                       | Purpose         | -------------> | Template        |
                       + - - - - - - - - +                + - - - - - - - - +


````

### Concept
The _concept_ is the main element to structure items in a hierarchical way. A _concept_ may have a parent _concept_ and may have one or multiple child _concepts_. 

With a _concept_, you can represent whatever you can build code for, like ...
* A database table.
* A database field (as a child of a database table).
* A UI (=user interface) section to group multiple fields together.
* A field in the UI (e.g. as child element of an ui section to group fields).
* A description of a cache.
* A description of a technical interface like a REST-Endpoint.

Let's say, you want to generate SQL script files for create database tables; than you introduce a concept named "SqlDatabaseTable" and a child concept named "SqlDatabaseField".
With these two concepts, you can model all your different database tables and its fields.

### Facet

A _facet_ make it possible to add all kind of values to a concept. Each _facet_ belongs exactly to one _concept_.

Examples:
If we have the _concept_ "SqlDatabaseTable", we want to add a facet "tableName" to provide a name for the database table.
If we have the _concept_ "SqlDatabaseField", we want to add a facet "fieldName" to provide a name for the database field and another facet "fieldType" to provide information whether this database field contains text, numbers or timestamps.

The facet itself can have only the following values:
* A text
* A boolean (=yes/no) value 
* A number value. Only non-floating values are supported.
* An enumeration, that means one value from a set of predefined values.
* A directory
* A file

### Template

A _template_ describes a file that is generated. It contains placeholders that will be replaced with concrete values. Templates can generate all kind of files, e.g. a java file, XML or JSON files, an HTML file, etc.

With a _template_, you describe the real code of a file like ...
* an SQL database script with CREATE TABLE statements. The names of the tables are placeholders that will be filled out dynamically.
* a java DAO file that contains the fields of a database table and is named with the table name.
* an angular or react component to visualize a certain text field like the firstname or lastname of a person. 
* an angular or react component to group different field (firstname and lastname of a person) together as a UI section.
* an angular or react component to provide a navigation for a list of navigation entries.

#### Template Target
A template target is the combination of a template and the file location (file path and file name) where the resolved content of the template is written to. 

### Purpose

A _purpose_ is responsible to transform an idea to code. 
It provides a list of templates that will be filled out to generate the files that represents this idea.
The templates will be fed with the _concepts_ and its _facets_ to replace the template placeholders with real values.

A _purpose_ connects _concepts_ and its _facets_ and transform them with help of _templates_ to generated code. 

With a _purpose_, you can represent an idea like ...
* a template to write SQL Scripts that contains CREATE TABLE statements for each table that is described.
* a template to write Java JPA entities or DAO beans to fetch data from database tables.
* a template to write an angular or react UI component for a set of properties.
* a template to write an OpenAPI specification for a set of REST endpoints

### Plugin
With the plugin mechanism, you can expose your _concepts_ and _purposes_ to the code generator. By providing the concepts and purposes, all the nested items like facets, templates, etc. are provided to the code generator and will be processed.

## Classes to run the code generation process
### Model Tree
The model tree represents all the concepts, its purposes and its facets as a hierarchical tree structure.
The class itself is mutable to build the tree but can be passed to other methods in an immutable form (similar as List and MutableList in Kotlin).


