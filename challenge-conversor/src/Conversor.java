import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public abstract class Conversor {
    public static void caseHandler(int opcion, HttpClient client) throws IOException, InterruptedException {
        String url = setURL(opcion);
        Scanner teclado = new Scanner(System.in);
        System.out.println("Ingrese el valor que desea convertir: ");
        double monto = teclado.nextDouble();
        double tasaDeCambio = obtenerTasaDeCambio(url, client);
        String monedaBase = obtenerMoneda(url, client, 1);
        String monedaTarget = obtenerMoneda(url, client, 2);
        double montoConvertido = monto * tasaDeCambio;
        String salida = "El valor " + monto + " ["+monedaBase+"]" + " corresponde al valor final de =>>> "+ montoConvertido + " ["+monedaTarget+"]";
        Historial.agregarEntrada(salida);
        System.out.println(salida);
    }

    public static String obtenerMoneda(String url, HttpClient client, int coin) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JSONObject jsonObject = new JSONObject(response.body());
        if(coin == 1){
            return jsonObject.getString("base_code");
        } else if (coin == 2){
            return jsonObject.getString("target_code");
        } else {
            return "";
        }
    }

    public static double obtenerTasaDeCambio(String url, HttpClient client) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JSONObject jsonObject = new JSONObject(response.body());
        return jsonObject.getDouble("conversion_rate");
    }

    public static String setURL(int opcion){
        return switch (opcion) {
            case 1 -> "https://v6.exchangerate-api.com/v6/6feee6061220b2d6a02d6e4f/pair/USD/ARS";
            case 2 -> "https://v6.exchangerate-api.com/v6/6feee6061220b2d6a02d6e4f/pair/ARS/USD";
            case 3 -> "https://v6.exchangerate-api.com/v6/6feee6061220b2d6a02d6e4f/pair/USD/BRL";
            case 4 -> "https://v6.exchangerate-api.com/v6/6feee6061220b2d6a02d6e4f/pair/BRL/USD";
            case 5 -> "https://v6.exchangerate-api.com/v6/6feee6061220b2d6a02d6e4f/pair/USD/COP";
            case 6 -> "https://v6.exchangerate-api.com/v6/6feee6061220b2d6a02d6e4f/pair/COP/USD";
            default -> "";
        };
    }
}