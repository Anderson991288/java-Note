import java.util.Scanner;

public class BMI0705 {

    public static void main(String[] args) {
        Scanner k = new Scanner(System.in);

        while(true) {
        System.out.print("�̧ǿ�J����(m)���魫(kg)=");
        
        double h =0;
        double w=0;
        if(k.hasNextDouble()) {
        	h = k.nextDouble();
        	w = k.nextDouble();

        	double BMI = w / (h * h);
        	BMI = Math.round(BMI * 100.0) / 100.0;
        	System.out.println("BMI=" + BMI);

        if (BMI < 18.5) {
            System.out.println("�魫����!");
        } 
        else if (BMI < 25 && BMI > 18.5) {
            System.out.println("���`!");
        } 
        else if (BMI > 25 && BMI < 30) {
            System.out.println("�W��!");
        } 
        else if (BMI > 30) {
            System.out.println("�έD!");
        }
    }
        else {
        	k.next();
        	System.out.println("���~!");
        }
}
}
}