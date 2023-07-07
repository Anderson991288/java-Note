## 列出九九乘法表並輸出成.txt檔

### 這段程式的主要功能是創建一個名為 "multiplication table.txt" 的文件，並在其中寫入九九乘法表。

* 這兩行程式引入了Java的兩個重要的I/O類別，分別是FileWriter和IOException。FileWriter類別用於寫入字符到文件，而IOException類別用於處理I/O錯誤。
```
import java.io.FileWriter;
import java.io.IOException;
```


* 定義了一個名為 "nine" 的公共類別
```
public class nine { ... }
```

* 定義了一個字符串變量 filename，並將其檔名設為 "multiplication table.txt"
```
String filename = "multiplication table.txt";
```


* 創建了一個新的FileWriter對象，並將其與我們先前定義的文件名關聯
```
FileWriter writer = new FileWriter(filename);
```

* 這兩個for迴圈用於生成1到9的乘法表
```
for (int i = 1; i <= 9; i++) { ... } 和 for (int j = 1; j <= 9; j++) { ... }
```

* 計算乘法的結果
```
int result = i * j;
```

* 將乘法表的每一行寫入到文件中
```
writer.write(i + " x " + j + " = " + result + "\n");
```

* 關閉文件寫入器。這是一個好的習慣，因為它可以釋放系統資源
```
writer.close();
```

* 如果文件成功寫入，則印出成功寫入!
```
System.out.println("成功寫入!：" + filename);
```


* 如果在寫入文件過程中發生任何I/O錯誤，則印出錯誤
```
catch (IOException e) { ... }
```

