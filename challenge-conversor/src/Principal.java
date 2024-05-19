import java.io.IOException;
import java.net.http.HttpClient;
import java.util.Scanner;


public class Principal {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner teclado = new Scanner(System.in);
        boolean salir = false;
        HttpClient client = HttpClient.newHttpClient();

        while (!salir) {
            String texto = """
                    ************************************************
                  Sea bienvenido/a al Conversor de Monedas
                  
                  
                  1) Dólar =>> Peso Argentino
                  2) Peso Argentino =>> Dólar
                  3) Dólar =>> Real Brasileño
                  4) Real Brasileño =>> Dólar
                  5) Dólar =>> Peso Colombiano
                  6) Peso Colombiano =>> Dólar
                  7) Salir
                  Elija una opción válida
                  ************************************************
                  """;

            System.out.println(texto);

            int opcion = teclado.nextInt();

            switch (opcion) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                    Conversor.caseHandler(opcion, client);
                    break;
                case 7:
                    System.out.println("Usted abandonó el sistema, gracias por usar nuestro servicio.");
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida, por favor intente nuevamente");
            }
        }
    }
}