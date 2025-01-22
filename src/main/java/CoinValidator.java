 import org.json.JSONObject;

 import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;


public class CoinValidator {

    public static void main(String[] args) {
        try {

            URL url = new URL("https://api.coindesk.com/v1/bpi/currentprice.json");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {


                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                JSONObject jsonResponse = new JSONObject(response.toString());
                JSONObject bpi = jsonResponse.getJSONObject("bpi");


                if (bpi.has("USD") && bpi.has("GBP") && bpi.has("EUR")) {
                    System.out.println("Success: All 3 BPIs (USD, GBP, EUR) are present.");
                } else {
                    System.out.println("Error: Missing one or more required BPIs.");
                }

                JSONObject gbp = getJsonObject(bpi);
                String gbpDescription = gbp.getString("description");
                if ("British Pound Sterling".equals(gbpDescription)) {
                    System.out.println("Success: GBP description is 'British Pound Sterling'.");
                } else {
                    System.out.println("Error: GBP description is '" + gbpDescription + "', expected 'British Pound Sterling'.");
                }
            } else {
                System.out.println("Error: Failed to fetch data. Status code " + responseCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static JSONObject getJsonObject(JSONObject bpi) {
        JSONObject gbp = bpi.getJSONObject("GBP");
        return gbp;
    }
}


