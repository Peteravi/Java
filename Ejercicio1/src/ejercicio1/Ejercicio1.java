package ejercicio1;
import java.util.Scanner;
import static javax.management.Query.div;
public class Ejercicio1 {

    public static void main(String[] args) {
        Scanner cs = new Scanner(System.in);
        int  num;
        int limite=0;
        
        
        
            System.out.println("INGRESA UN NUMERO");
            num=cs.nextInt();
        double div = num/2;
              
              if(div==2) {
                  System.out.println("DIVISIBLE ENTRE 2");
                  limite++;
              }
              else{
                   System.out.println("NO ES DIVISIBLE ");
                  limite++;
              }
              
    }
}
        