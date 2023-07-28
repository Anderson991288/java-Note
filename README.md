# java-StudyNote

## 基本語法
### input and output
  ### 1.import:
  
  import java.util.Scanner  // 讀取輸入用
    
  ### 2.Scanner:
  
  Scanner k = new Scanner(System.in)  // k就像是鍵盤，代表我們輸入的東西
    |方法 | 回傳型態 | 描述 |
| :-----| :----: | :----: |
| nextInt() | int | 讀取使用者的下一個整數輸入|
| nextDouble() | double | 讀取使用者的下一個浮點數輸入 |
| next() | double | 讀取輸入中的下一個以空白（包括空格、制表符和換行符）分隔的字詞 |

  ### 3.output:
  * System.out.println("文字" + 要顯示的變數 ); 
  * System.out.print("文字" + 要顯示的變數 );


    
    println：用於輸出文字並換行。它會將輸出列出到下一行並自動換行

    print：用於輸出文本。它會將輸出打印在同一行上，而不會換行
    
### 物件導向設計
* 對程式而言，物件就是一群資料(存在記憶體)，或是一群記憶體的集合
* 一大群資料中，又可以分很多一小群資料，物件中還包含物件
* 針對要開發的專案(你要做什麼運算)，去分析要用到哪幾群的資料(可能是時間或金錢)，將資料分類後，一群資料就是一個物件
* 有物件的需求才去開發類別

### 如何存取記憶體? 
* 透過宣告變數，要求環境配置記憶體
* 一群資料就是一群變數，透過變數來存取資料
  
### 物件擁有資料與函式
* 物件擁有資料來存放數值
* 物件擁有函式來運算這些值(在JAVA中稱為*方法*)

## ERROR 修正 : ERROR StatusLogger No Log4j 2 configuration file found.
在 Eclipse 中，可以通過以下步驟來設置系統屬性：

1.在項目上右鍵單擊，選擇 "Run As" -> "Run Configurations..."。
2.在左側列表中選擇您的應用程序。
3.在右側選擇 "Arguments" 頁面。
4.在 "VM arguments" 區域中，添加以下行（假設您的 log4j2.xml 文件位於 src/main/resources 目錄下）：-Dlog4j.configurationFile=src/main/resources/log4j2.xml
5.點擊 "Apply" 和 "Run"。

現在，Log4j 2 應該能夠找到並使用您的配置文件了。









