package factorial;  		//程式所在資料夾名稱

import java.math.BigDecimal;
import java.util.Scanner;

/*public class factorial0704 {
   
	public static void main(String[] args) {
        Scanner m = new Scanner(System.in);    //如果要輸入數字:Scanner 變數名稱 = new Scanner(System.in);
        System.out.print("請輸入一個正整數 N：");  //提示輸入數字
            if (m.hasNextInt()) {
                int n = m.nextInt();

                // 檢查輸入是否為正整數
                if (n < 0) {
                    System.out.println("請輸入正整數！");
                }		
                else {
                    BigDecimal factorial = calculateFactorial(n);
                    System.out.println(n + "的階乘為：" + factorial);
                }
            } 
    }

    // 計算
    public static BigDecimal calculateFactorial(int n) {
        BigDecimal factorial = BigDecimal.ONE;
        for (int i = 2; i <= n; i++) {
            factorial = factorial.multiply(BigDecimal.valueOf(i));
        }
        return factorial;
    }
}*/


/*public class factorial0704 {
    public static void main(String[] args) {
    	//如果要輸入數字:  
    	//Scanner 變數名稱 = new Scanner(System.in);
        Scanner k = new Scanner(System.in);
        
        //提示輸入數字
        System.out.print("請輸入一個非負整數：");
        while(){
        if (k.hasNextInt()) {
            int number = k.nextInt();   //將輸入的整數儲存在 number 變數中。
            if (number < 0) {
                System.out.println("錯誤：輸入的數字不能為負數。");
            } 
            else {
                System.out.println(number + " 的階乘為 " + factorial(number));
            }
        }
        else {
            System.out.println("錯誤，請輸入一個非負整數");
        }
        k.close();
    }
    }

 public static BigDecimal factorial(int n) {
        if (n == 0) {
        	return new BigDecimal(1);
        } else {
            return new BigDecimal(n).multiply(factorial(n - 1));
        }
 }
 
}*/

public class factorial0704 {
    public static void main(String[] args) {
        Scanner k = new Scanner(System.in);
        while(true){ 					//連續執行
            System.out.print("請輸入一個非負整數：");
            if (k.hasNextInt()) {
                int 數字= k.nextInt();   //將輸入的整數儲存在 number 變數中。
                if (數字 < 0) {
                    System.out.println("錯誤：輸入的數字不能為負數。");
                } 
                else {
                		int result = 1;
                		for (int i = 1; i <= 數字; i++) {
                        result = result*i;
                    }
                		System.out.println(數字 + " 的階乘為 " + result);
                }   
                }
            	else {
            		k.next();  // 清除錯誤的輸入
            		System.out.println("錯誤，請輸入一個非負整數");
            }
        }
    }
    
}
        

    

   /*public static long factorial(int n) {
        long result = 1;
        for (int i = 1; i <= n; i++) {
            result = result*i;
        }
        return result;
    }
}*/

/*public static BigDecimal factorial(int n) {
    if (n == 0) {
    	return new BigDecimal(1);
    } else {
        return new BigDecimal(n).multiply(factorial(n - 1));
    }
}
}*/



