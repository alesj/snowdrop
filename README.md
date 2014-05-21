SNOWDROP
========

Snowdrop is a utility package that contains JBoss-specific extensions to the Spring Framework. These extensions are either:

1. Extensions to Spring Framework classes that can be used wherever the generic implementations provided by the framework 
do not integrate correctly with JBoss Enterprise Application Platform.
2. Extensions for deploying and running Spring applications with the JBoss Enterprise Application Platform.

Usage/Setup:
---------
Deployments can also be configured to exclude specific implicit dependencies. This is done with the 
**jboss-deployment-structure.xml** deployment descriptor file. This is commonly done when an application bundles a specific 
version of a library that the application server will attempt to add as an implicit dependency.

Supported Version:
------------------
**Snowdrop 3.x works with the following version of JBoss:**

* JBoss AS 7.1.0, 7.1.1, 7.1.2, 7.1.3

* JBoss AS 7.2.0 - (This version of JBoss has a problem with Hibernate, specifically Hibernate 4.1.0.CR1)

* JBoss EAP 6.0.0, 6.0.1, 6.1.0, 6.1.1, 6.2.x

_NOTE: If you need to use JBoss AS 5/6 or EAP 5 then please use Snowdrop 2.1.1.Final (it is Tagged in this repo).  That 
version still supports the older versions of JBoss._

Build:
---------

_NOTE: You don't need to build if you wish to use a released version of snowdrop, just continue from the Install section. 
It will use the libraries in Maven._

        mvn clean install

Install snowdrop:
-----------------

**Automatic:**
See the complete [instructions here](https://github.com/snowdrop/snowdrop/tree/master/install).

        cd install

        mvn package -DJBOSS_HOME=/path/to/jboss_home -P${desired-spring-version} -Dversion.snowdrop=${snowdrop.version}

**Manual:**

To install the Snowdrop Deployment subsystem:

1. For JBoss AS 7.1(*or older*) or EAP 6.0.x(*or older*).
  1. Extract the `subsystem-as7/subsystem-as7/target/jboss-spring-deployer-as7.zip` into the `$JBOSS_HOME` directory.
  2. Extract the `subsystem-as7/modules/spring-x.x/target/spring-x.x-module.zip` into the `$JBOSS_HOME/modules` directory.

2. For JBoss AS 7.2(*or newer*) or EAP 6.1.x(*or newer*).
  1. Extract the `subsystem-as7/subsystem-as7/target/jboss-spring-deployer-as7.2.zip` into the `$JBOSS_HOME` directory.
  2. Extract the `subsystem-as7/modules/spring-x.x/target/spring-x.x-module.zip` into the `$JBOSS_HOME/modules/system` directory.

**To use JBoss with Snowdrop run:**

`$JBOSS_HOME/bin/standalone.sh -c standalone-snowdrop.xml`

Uninstall snowdrop:
-------------------

1. For JBoss AS 7.1(*or older*) or EAP 6.0.x(*or older*).
  1. Delete the `$JBOSS_HOME/modules/org/jboss/snowdrop` directory.
  2. Delete the `$JBOSS_HOME/modules/org/springframework` directory.

2. For JBoss AS 7.2(*or newer*) or EAP 6.1.x(*or newer*).
  1. Delete the `$JBOSS_HOME/modules/system/add-ons/snowdrop` directory.

3. Delete `$JBOSS_HOME/standalone/standalone-snowdrop.xml`.
