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
	<artifactId>secure-http-properties-connector</artifactId>
	<version>0.0.10</version>
	<classifier>mule-plugin</classifier>
</dependency>
```

## publish

put below xml snipped in your pom.xml file. and run mvn deploy in order to put this jar to artifact repository

```xml
	<distributionManagement>
		<repository>
			<id>repo-mule</id>
			<url>https://pkgs.dev.azure.com/$organization/$project/_packaging/cci-repo-mule/maven/v1</url>
		</repository>
		<snapshotRepository>
			<id>repo-mule</id>
			<url>https://pkgs.dev.azure.com/$organization/$project/_packaging/cci-repo-mule/maven/v1</url>
		</snapshotRepository>
	</distributionManagement>
```

**know issues**
 - There's no XML files that has a <module> root element, thus is impossible to auto generate a [mule-artifact.json] descriptor file. The file must start with [module-] and end with [.xml], such as [module-foo.xml] 
 - - set JAVA_HOME to your env. in my case export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0_301.jdk/Contents/Home# mule-log4m-connector

*references
 - https://docs.mulesoft.com/mule-runtime/4.4/custom-configuration-properties-provider
 - https://www.salesforce.com/blog/custom-connector-mule-sdk/
 - https://blogs.mulesoft.com/dev-guides/api-connectors-templates/custom-connector-mule-sdk/
 - https://github.com/amitsethi0843/mule4-url-property-placeholder
 - https://docs.mulesoft.com/mule-runtime/4.2/custom-configuration-properties-provider
 - https://www.linkedin.com/pulse/mule4-loading-properties-file-from-httphttps-url-amit-sethi-1d/