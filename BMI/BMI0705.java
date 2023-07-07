import java.util.Scanner;

public class BMI0705 {

    public static void main(String[] args) {
        Scanner k = new Scanner(System.in);

        while(true) {
        System.out.print("依序輸入身高(m)及體重(kg)=");
        
        double h =0;
        double w=0;
        if(k.hasNextDouble()) {
        	h = k.nextDouble();
        	w = k.nextDouble();

        	double BMI = w / (h * h);
        	BMI = Math.round(BMI * 100.0) / 100.0;
        	System.out.println("BMI=" + BMI);

        if (BMI < 18.5) {
            System.out.println("體重不足!");
        } 
        else if (BMI < 25 && BMI > 18.5) {
            System.out.println("正常!");
        } 
        else if (BMI > 25 && BMI < 30) {
            System.out.println("超重!");
        } 
        else if (BMI > 30) {
            System.out.println("肥胖!");
        }
    }
        else {
        	k.next();
        	System.out.println("錯誤!");
        }
}
}
}