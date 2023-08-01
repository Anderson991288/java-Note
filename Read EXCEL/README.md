## ERROR 修正 : ERROR StatusLogger No Log4j 2 configuration file found.
* 在 Eclipse 中，您可以使用以下步驟來創建和設置 Log4j 2 配置文件：

在您的項目根目錄或者 src 目錄下建一個新的 XML 文件，例如 log4j2.xml。將以下內容添加到該文件中：
```
<?xml version="1.0" encoding="UTF-8"?>
<Configuration status="WARN">
    <Appenders>
        <Console name="Console" target="SYSTEM_OUT">
            <PatternLayout pattern="%d{HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n"/>
        </Console>
    </Appenders>
    <Loggers>
        <Root level="error">
            <AppenderRef ref="Console"/>
        </Root>
    </Loggers>
</Configuration>
```

1.在項目上右鍵單擊，選擇 "Run As" -> "Run Configurations..."。

2.在左側列表中選擇您的應用程序。

3.在右側選擇 "Arguments" 頁面。

4.在 "VM arguments" 區域中，添加以下行（假設您的 log4j2.xml 文件位於 src 目錄下）：

```
-Dlog4j.configurationFile=src/log4j2.xml
```

5.點擊 "Apply" 和 "Run"。

現在，Log4j 2 應該能夠找到並使用您的配置文件了。




