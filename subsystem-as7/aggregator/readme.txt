Spring Deployer JBoss AS7 subsystem
-----------------------------------

Structure:

The Spring Deployer AS7 subsystem assumes the existence of a Spring module
in JBoss AS7 with the following coordinates:

<module name="org.springframework.spring" slot="snowdrop"/>

The module may contain a Spring 2.5 or Spring 3 distribution.

The full distribution contains:

- a 'deployer' directory containing the Snowdrop subsystem + a Spring 3 module
- a 'module-2.5' directory containing a Spring 2.5 module which can be used for
replacing the Spring 3 module


The distribution without dependencies contains:
-
