# http-properties connector for mule4

## compile

```sh
mvn clean package -DskipTests
```

## use

Add this dependency to your application pom.xml

```xml
<dependency>
	<groupId>github.com.farukonder.mule4.connectors</groupId>
	<artifactId>mule-http-properties-connector</artifactId>
	<version>0.0.1</version>
	<classifier>mule-plugin</classifier>
</dependency>
```

## publish

put below xml snipped in your pom.xml file. and run mvn deploy in order to put this jar to artifact repository

```xml
	<distributionManagement>
		<repository>
			<id>cci-repo-mule</id>
			<url>https://pkgs.dev.azure.com/$organization/$project/_packaging/cci-repo-mule/maven/v1</url>
		</repository>
		<snapshotRepository>
			<id>cci-repo-mule</id>
			<url>https://pkgs.dev.azure.com/$organization/$project/_packaging/cci-repo-mule/maven/v1</url>
		</snapshotRepository>
	</distributionManagement>
```

**know issues**
 - There's no XML files that has a <module> root element, thus is impossible to auto generate a [mule-artifact.json] descriptor file. The file must start with [module-] and end with [.xml], such as [module-foo.xml] 
 - - set JAVA_HOME to your env. in my case export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0_301.jdk/Contents/Home# mule-log4m-connector
