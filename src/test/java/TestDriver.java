import org.json.JSONObject;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

public class TestDriver {

    //Fill in these parameters to run
    public static String API_KEY = "";
    public static String DEVICE_MODEL = "";
    public static String DEVICE_MAC= "";

    public static void main(String[] args) throws MalformedURLException {
        GoveeController controller = new GoveeController(API_KEY);
        JSONObject response;

        //Returns all devices connected
        response = controller.getConnectedDevices();
        System.out.println(response.toString(4));
        System.out.println("--------------------------------------------");
        //Returns information about your selected device
        //add a breakpoint here until you have your desired MAC and model
        response = controller.getDeviceState(DEVICE_MAC, DEVICE_MODEL);
        System.out.println(response.toString(4));
        System.out.println("--------------------------------------------");

        //Turns on device
        controller.turnDeviceOnOff(Device.ON, DEVICE_MAC, DEVICE_MODEL);

        //Gets your current API usage info
        Map<String, List<String>> map = controller.getRateLimit();
        for (Map.Entry<String, List<String>> entry : map.entrySet())
            System.out.println(entry.getKey() + " " + entry.getValue());

        System.out.println("--------------------------------------------");
        //sets the brightness of the device
        controller.setBrightness(25, DEVICE_MAC, DEVICE_MODEL);

        //look at the beauty
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //sets colortem of your device
        controller.setColorTem(9000, DEVICE_MAC,DEVICE_MODEL);

        //look at the beauty
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //sets the RGB of your device if applicable
        controller.setRGB(255,0,255, DEVICE_MAC, DEVICE_MODEL);

        //admire the colors
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        controller.turnDeviceOnOff(Device.OFF, DEVICE_MAC, DEVICE_MODEL);

        //updates API key
        //controller.updateAPIKey("newAPIKey");

    }
}
