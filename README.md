
# buster-maven-plugin

A maven plugin for running [BusterJS](http://busterjs.org) tests.


## Getting started

For the moment, this plugin does not exists in the public maven repo. So for now you will need to clone the project and do a **mvn install**

### Configure plugin

The plugin has 2 modes;
- **With local buster server** where it will use a local running buster. You need to start a buster server manually and use the desired browser to capture the tests, then run the buster test goal.
- **With embedded buster server** where the plugin will start a buster server on a random port and use [PhantomJS](http://phantomjs.org/) as a headless browser to capture the tests.

#### With local buster server
<plugin>
	<groupId>org.mw.buster</groupId>
	<artifactId>buster-maven-plugin</artifactId>
	<version>1.4.2-SNAPSHOT</version>
	<configuration>
		<busterJsFilePath>${basedir}/src/test/javascript/buster.js</busterJsFilePath>
		<hostname>localhost</hostname>
		<port>1234</port>
		<buildDirectory>${project.build.directory}</buildDirectory>
	</configuration>
</plugin>

#### With embedded buster server and PhantomJS

For this mode to work you will need to install **PhantomJS* and be accessible on PATH

<plugin>
	<groupId>org.mw.buster</groupId>
	<artifactId>buster-maven-plugin</artifactId>
	<version>1.4.2-SNAPSHOT</version>
	<configuration>
		<busterJsFilePath>${basedir}/src/test/javascript/buster.js</busterJsFilePath>
		<embeddedBusterServer>true</embeddedBusterServer>
		<buildDirectory>${project.build.directory}</buildDirectory>
	</configuration>
	<dependencies>
		<dependency>
			<groupId>org.seleniumhq.selenium</groupId>
			<artifactId>selenium-java</artifactId>
			<version>${version.selenium}</version>
		</dependency>
		<dependency>
			<groupId>com.github.detro.ghostdriver</groupId>
			<artifactId>phantomjsdriver</artifactId>
			<version>${version.phantomjs}</version>
		</dependency>
	</dependencies>
</plugin>	