package cajeroautomatico;
import java.util.Scanner;
public class CajeroAutomatico {
public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Cuenta cuenta = new Cuenta(1000); // saldo inicial de la cuenta
    
while (true) {
            System.out.println("Bienvenido al cajero automático");
            System.out.println("1. Consultar saldo");
            System.out.println("2. Retirar dinero");
            System.out.println("3. Depositar dinero");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    System.out.println("Saldo actual: " + cuenta.getSaldo());
                    break;
                case 2:
                    System.out.print("Ingrese la cantidad a retirar: ");
                    double cantidadRetirar = sc.nextDouble();
                    cuenta.retirar(cantidadRetirar);
                    break;
                case 3:
                    System.out.print("Ingrese la cantidad a depositar: ");
                    double cantidadDepositar = sc.nextDouble();
                    cuenta.depositar(cantidadDepositar);
                    break;
                case 4:
                    System.out.println("Gracias por usar el cajero automático");
                    return;
                default:
                    System.out.println("Opción inválida");
                    break;
            }
        }
    }
}
