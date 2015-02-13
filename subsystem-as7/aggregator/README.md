Snowdrop JBoss Subsystem Aggregator
===================================

Purpose:
--------

The Subsystem Aggregator pulls together the Snowdrop Subsystem code and the Spring modules into a single zip for easy deployment.

Structure:
----------

The Spring Deployer AS7 subsystem assumes the existence of a Spring module
in JBoss AS7 with the following coordinates:

        <module name="org.springframework.spring" slot="snowdrop"/>

The module may contain a Spring 3.2, or 4.x distribution.

The full distribution contains:

- A 'module-deployer' directory containing the Snowdrop subsystem (which will be paired with a Spring 4 module during installation)
- A 'module-3.2' directory containing a Spring 3.2 module which can be used for replacing the Spring 4 module


The distribution without dependencies contains:

- A 'module-deployer' directory containing the Snowdrop subsystem (which will be paired with a Spring 4 module during installation)
- A full set of spring modules without any jars so that you can fill it with the specific versions and dependencies that you need.
