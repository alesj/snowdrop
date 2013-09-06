SNOWDROP
========

Snowdrop is a utility package that contains JBoss-specific extensions to the Spring Framework. These extensions are either:

        extensions to Spring Framework classes that can be used wherever the generic implementations provided by the framework do not integrate correctly with JBoss Enterprise Platforms.

        extensions for deploying and running Spring applications with the JBoss Enterprise Platforms (JBoss Enterprise Application Platform and JBoss Enterprise Web Platform).

Usage/Setup:
---------
Deployments can also be configured to exclude specific implicit dependencies. This is done with the 
**jboss-deployment-structure.xml** deployment descriptor file. This is commonly done when an application bundles a specific 
version of a library that the application server will attempt to add as an implicit dependency.

Build:
---------

_NOTE: You don't need to build if you wish to use a released version of snowdrop, just continue from the Install section._

`mvn clean install`

Install snowdrop:
-----------------

**Automatic:**

`cd install`

`mvn package -P${desired-spring-version} -DJBOSS_HOME=/path/to/jboss_home -Dversion.snowdrop=${snowdrop.version}`

**Manual:**

To install the Snowdrop Deployment subsystem, unzip the `subsystem-as7/subsystem-as7/target/jboss-spring-deployer-as7.zip` file in the `$JBOSS_HOME` dir if using JBOSS AS 7.1 or lower.

For JBOSS 7.2 or EAP 6.1.Alpha1 or higher, unzip the `subsystem-as7/subsystem-as7/target/jboss-spring-deployer-as7.2.zip` file in the `$JBOSS_HOME` dir.

Then extract the `subsystem-as7/modules/spring-x.x/target/spring-x.x-module.zip` in the `$JBOSS_HOME/modules` directory of your JBoss Application Server installation.

To use JBoss with Snowdrop run $JBOSS_HOME/bin/standalone.sh -c "standalone-snowdrop.xml".

Uninstall snowdrop:
-------------------

For JBOSS AS 7.1 or lower, delete `$JBOSS_HOME/modules/org/jboss/snowdrop` and `$JBOSS_HOME/module/org/springframework` dirs.

For JBOSS AS 7.2/EAP 6.1.Alpha1 or higher, delete `$JBOSS_HOME/modules/add-ons/org/jboss/snowdrop` and `$JBOSS_HOME/module/add-ons/org/springframework` dirs.

Finally, delete `$JBOSS_HOME/standalone/standalone-snowdrop.xml`.