#### Configure IntelliJ runner
_Error_:

```java
objc[20650]: Class JavaLaunchHelper is implemented in both /Library/Java/JavaVirtualMachines/jdk1.8.0_121.jdk/Contents/Home/bin/java (0x10663b4c0) and /Library/Java/JavaVirtualMachines/jdk1.8.0_121.jdk/Contents/Home/jre/lib/libinstrument.dylib (0x1066bb4e0). One of the two will be used. Which one is undefined.
Exception in thread "main" java.lang.NoClassDefFoundError: org/springframework/transaction/TransactionDefinition
	at java.lang.Class.getDeclaredMethods0(Native Method)
	at java.lang.Class.privateGetDeclaredMethods(Class.java:2701)
	at java.lang.Class.privateGetPublicMethods(Class.java:2902)
	at java.lang.Class.getMethods(Class.java:1615)
	at cucumber.runtime.java.MethodScanner.scan(MethodScanner.java:39)
	at cucumber.runtime.java.JavaBackend.loadGlue(JavaBackend.java:82)
	at cucumber.runner.Runner.<init>(Runner.java:38)
	at cucumber.runner.SingletonRunnerSupplier.createRunner(SingletonRunnerSupplier.java:38)
	at cucumber.runner.SingletonRunnerSupplier.get(SingletonRunnerSupplier.java:32)
	at cucumber.runtime.Runtime.run(Runtime.java:74)
	at cucumber.api.cli.Main.run(Main.java:26)
	at cucumber.api.cli.Main.main(Main.java:8)
Caused by: java.lang.ClassNotFoundException: org.springframework.transaction.TransactionDefinition
	at java.net.URLClassLoader.findClass(URLClassLoader.java:381)
	at java.lang.ClassLoader.loadClass(ClassLoader.java:424)
	at sun.misc.Launcher$AppClassLoader.loadClass(Launcher.java:331)
	at java.lang.ClassLoader.loadClass(ClassLoader.java:357)
	... 12 more
```

_Solution_:

1. Launch IntelliJ.
2. After opening IntelliJ, select **Run/Debug Configuration** and select **Edit Configurations...**

![](https://camo.githubusercontent.com/41be4a13000f0aecfe8cb993c2d717ad03710386/687474703a2f2f692e696d6775722e636f6d2f3953314672367a2e706e67)

3. Now select **Defaults** from the **Run/Debug Configuration** window and choose **Cucumber Java**.

![](https://camo.githubusercontent.com/9633dba433c6ece34bb33429e1afdeca3d48e307/687474703a2f2f692e696d6775722e636f6d2f31566b775359752e706e67)

4. Add **Glue** to `io.github.osvaldjr.stepdefinitions`