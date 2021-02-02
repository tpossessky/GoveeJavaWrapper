import org.json.JSONObject;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;


enum Device{ON, OFF}

public class GoveeController {
    private String API_KEY = null;

    /**
     * Public constructor requiring the client to set their API key
     * @param API_KEY API Key provided by Govee via app
     */
    public GoveeController(String API_KEY){ this.API_KEY = API_KEY; }


    /**
     * Creates a GET request to the Govee API obtaining connected Govee devices
     * @return JSONObject response from API call
     */
    public JSONObject getConnectedDevices() {
        if(!checkAPI()){
            System.out.println("API Key not Set");
            return null;
        }
        URL url;
        HttpURLConnection con = null;
        try {
            url = new URL("https://developer-api.govee.com/v1/devices");
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Govee-API-Key", API_KEY);
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);

            return new JSONObject(getJSONResponse(con.getInputStream()));

        } catch (IOException e) {
            try {
                if(con.getResponseCode() == 401) System.out.println("Invalid API Key!");
                else e.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }


    /**
     * Returns rate limit information contained in the headers of the API response
     * @return Map containing header data
     */
    public Map<String, List<String>> getRateLimit() throws MalformedURLException {
        if(!checkAPI()){
            System.out.println("API Key not Set");
            return null;
        }
        URL url = new URL("https://developer-api.govee.com/v1/devices");
        HttpURLConnection con = null;
        try {
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Govee-API-Key", API_KEY);
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);

            return con.getHeaderFields();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Sets the brightness of a device.
     * @param brightness brightness int 0-100
     * @param deviceMAC MAC Address of desired device (obtained through the getConnectedDevices method)
     * @param deviceModel model number of the desired device (obtained through the getConnectedDevices method)
     * @return status code from API request. 200-OK, 400-error in input. 403-invalid API key. 500-internal server error
     */
    public int setBrightness(int brightness, String deviceMAC, String deviceModel){
        if(brightness < 0 || brightness > 100){
            System.out.println("Invalid Brightness! Must be between 0-100");
            return 400;
        }
        if(!checkAPI()){
            System.out.println("API Key not Set");
            return 403;
        }

        URL url = null;
        try {
            url = new URL("https://developer-api.govee.com/v1/devices/control");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("PUT");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Govee-API-Key", API_KEY);
            con.setDoOutput( true );
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);

            JSONObject requestBody = new JSONObject();
            requestBody.put("device", deviceMAC);
            requestBody.put("model", deviceModel);
            JSONObject command = new JSONObject();
            command.put("name", "brightness");
            command.put("value", brightness);
            requestBody.put("cmd", command);

            byte[] input = requestBody.toString().getBytes(StandardCharsets.UTF_8);
            OutputStream os = con.getOutputStream();
            os.write(input, 0, input.length);
            return con.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
            return 400;
        }
    }


    /**
     * Turns the requested device on or off.
     * @param onOff Enum provided containing either Device.ON or Device.OFF
     * @param deviceMAC MAC Address of desired device
     * @param deviceModel model number of desired device
     * @return status code from api request (see setBrightness() for more information)
     */
    public int turnDeviceOnOff(Device onOff, String deviceMAC, String deviceModel){
        if(!checkAPI()){
            System.out.println("API Key not Set");
            return 403;
        }
        URL url = null;
        try {
            url = new URL("https://developer-api.govee.com/v1/devices/control");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("PUT");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Govee-API-Key", API_KEY);
            con.setDoOutput( true );
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);

            JSONObject requestBody = new JSONObject();
            requestBody.put("device", deviceMAC);
            requestBody.put("model", deviceModel);
            JSONObject command = new JSONObject();
            command.put("name", "turn");

            if(onOff == Device.OFF) command.put("value", "off");
            else command.put("value", "on");

            requestBody.put("cmd", command);

            byte[] input = requestBody.toString().getBytes(StandardCharsets.UTF_8);
            OutputStream os = con.getOutputStream();
            os.write(input, 0, input.length);
            return con.getResponseCode();

        } catch (IOException e) {
            e.printStackTrace();
            return 400;
        }
    }


    /**
     * Sets the colortem of a desired device
     * @param colorTem colortem of device (between 2000-9000)
     * @param deviceMAC MAC address of desired device
     * @param deviceModel model number of desired device
     * @return status code from api request (see setBrightness() for more information)
     */
    public int setColorTem(int colorTem, String deviceMAC, String deviceModel){

        if(colorTem < 2000 || colorTem > 9000){
            System.out.println("Invalid ColorTem! Must be between 2000-9000");
            return 400;
        }
        if(!checkAPI()){
            System.out.println("API Key not Set");
            return 403;
        }

        URL url = null;
        try {
            url = new URL("https://developer-api.govee.com/v1/devices/control");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("PUT");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Govee-API-Key", API_KEY);
            con.setDoOutput( true );
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);

            JSONObject requestBody = new JSONObject();
            requestBody.put("device", deviceMAC);
            requestBody.put("model", deviceModel);
            JSONObject command = new JSONObject();
            command.put("name", "colorTem");
            command.put("value", colorTem);
            requestBody.put("cmd", command);

            byte[] input = requestBody.toString().getBytes(StandardCharsets.UTF_8);
            OutputStream os = con.getOutputStream();
            os.write(input, 0, input.length);
            return con.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
            return 400;
        }
    }


    /**
     * Sets rgb state of a device if supported
     * @param r red 0-255
     * @param g green 0-255
     * @param b blue 0-255
     * @param deviceMAC mac address of desired device
     * @param deviceModel model of desired device
     * @return status code from api request (see setBrightness() for more information)
     */
    public int setRGB(int r, int g, int b, String deviceMAC, String deviceModel){
        if(!checkAPI()){
            System.out.println("API Key not Set");
            return 403;
        }
        if(r < 0 || r > 255){
            System.out.println("Invalid r value! Must be between 0-255");
            return 400;
        }
        else if(g < 0 || g > 255){
            System.out.println("Invalid r value! Must be between 0-255");
            return 400;
        }
        else if(b < 0 || b > 255){
            System.out.println("Invalid r value! Must be between 0-255");
            return 400;
        }


        URL url = null;
        try {
            url = new URL("https://developer-api.govee.com/v1/devices/control");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("PUT");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Govee-API-Key", API_KEY);
            con.setDoOutput( true );
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);

            JSONObject requestBody = new JSONObject();
            requestBody.put("device", deviceMAC);
            requestBody.put("model", deviceModel);

            JSONObject command = new JSONObject();
            command.put("name", "color");
            JSONObject rgb = new JSONObject();
            rgb.put("r", r);
            rgb.put("g", g);
            rgb.put("b", b);

            command.put("value", rgb);
            requestBody.put("cmd", command);

            byte[] input = requestBody.toString().getBytes(StandardCharsets.UTF_8);
            OutputStream os = con.getOutputStream();
            os.write(input, 0, input.length);
            return con.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
            return 400;
        }
    }


    /**
     * Gets device specific device information with provided MAC and model
     * @param deviceMAC MAC address of desired device
     * @param deviceModel model number of desired device
     * @return JSONObject containing the device's information
     */
    public JSONObject getDeviceState(String deviceMAC, String deviceModel){
        if(!checkAPI()){
            System.out.println("API Key not Set");
            return null;
        }
        URL url;
        HttpURLConnection con = null;
        try {
            url = new URL("https://developer-api.govee.com/v1/devices/state?device="+deviceMAC+"&model="+deviceModel);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Govee-API-Key", API_KEY);
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);

            return new JSONObject(getJSONResponse(con.getInputStream()));

        } catch (IOException e) {
            try {
                if(con.getResponseCode() == 401) System.out.println("Invalid API Key!");
                else e.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }


    /**
     * Gets response from HTTP request
     * @param inputStream input stream from HTTPURLConnection
     * @return String containing JSON response
     */
    private String getJSONResponse(InputStream inputStream){
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        String output;
            try {
                while ((output = br.readLine()) != null)
                    sb.append(output);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return sb.toString();
    }


    /**
     * Checks if the user set an API key
     * @return boolean if the key was set
     */
    private boolean checkAPI(){
        return API_KEY != null;
    }


    /**
     * Setter method for Govee API_KEY
     * @param API_KEY new API_KEY to be set
     */
    public void updateAPIKey(String API_KEY){
        this.API_KEY = API_KEY;
    }

}