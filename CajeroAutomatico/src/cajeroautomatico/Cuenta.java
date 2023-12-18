package cajeroautomatico;
public class Cuenta {
    
 private double saldo;

    public Cuenta(double saldoInicial) {
        saldo = saldoInicial;
    }

    public double getSaldo() {
        return saldo;
    }

    public void depositar(double cantidad) {
        saldo += cantidad;
        System.out.println("Depósito exitoso");
    }

    public void retirar(double cantidad) {
        if (cantidad > saldo) {
            System.out.println("Saldo insuficiente");
        } else {
            saldo -= cantidad;
            System.out.println("Retiro exitoso");
        }
    }
}
