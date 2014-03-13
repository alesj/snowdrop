Snowdrop Installer
==================

Purpose
--------

To enable ease of installation of the snowdrop module in the standalone version of EAP.

What-it-does
------------

It copies the necessary snowdrop and spring jars in their proper location within ${JBOSS_HOME}/modules.

It also creates a standalone-snowdrop.xml that registers the snowdrop extension and subsystem, removing the need for manual 
installation. It adds the following lines into `extensions` and `profile`:

        <extension module="org.jboss.snowdrop"/>
        
        <subsystem xmlns="urn:jboss:domain:snowdrop:1.0"/>


How-to-use
-----------

*NOTE: Make sure to pass in `-DJBOSS_HOME=/path/to/JBoss`.*

*NOTE: If you are installing a non released version, be sure to `mvn clean install` on the top level.*

*NOTE: If you are installing on Windows you may need to reverse the slashes.*

Install with defaults. By default Snowdrop version 3.1.1.Final and spring 4.0.2.RELEASE will be installed.

		mvn package -DJBOSS_HOME=/path/to/jboss_home

**OR**

Install and set Spring and Snowdrop versions:

		mvn package -DJBOSS_HOME=/path/to/jboss_home -P${desired-spring-version} -Dversion.snowdrop=${desired-snowdrop-version}

There are four possible spring version profiles: **spring-2.5**, **spring-3**, **spring-3.1**, **spring-3.2**, and **spring-4.0**(*the default*).
