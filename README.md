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
    

