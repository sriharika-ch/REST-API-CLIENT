import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class WeatherApp {

    private static final String API_KEY = "0b67a8b41960c318ef0ede0091f028c2"; // Replace with your actual OpenWeatherMap API key

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter city name: ");
        String city = scanner.nextLine().trim();

        String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=" + city +
                "&appid=" + API_KEY + "&units=metric";

        try {                        //handles exceptions
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int status = conn.getResponseCode();
            if (status != 200) {
                System.out.println("Failed: HTTP error code " + status);
                return;
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder responseText = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                responseText.append(line);
            }

            reader.close();
            conn.disconnect();

            JsonObject json = JsonParser.parseString(responseText.toString()).getAsJsonObject();

            String cityName = json.get("name").getAsString();
            double temp = json.getAsJsonObject("main").get("temp").getAsDouble();
            String weather = json.getAsJsonArray("weather")
                                .get(0).getAsJsonObject()
                                .get("description").getAsString();
                // print weather information of entered city
            System.out.println("\n--- Weather Info ---");
            System.out.println("City:        " + cityName);
            System.out.println("Temperature: " + temp + " °C");
            System.out.println("Condition:   " + weather);

        } catch (Exception e) {         //to handle exceptions
            System.out.println("Error: " + e.getMessage());
        }

        scanner.close();
    }
}
