package net.haesleinhuepf.clapeer;

import com.apeer.sdk.ApeerDevKit;
import com.apeer.sdk.ApeerEnvironmentException;
import com.apeer.sdk.ApeerInputException;
import com.apeer.sdk.ApeerOutputException;
import net.haesleinhuepf.clapeer.GaussianBlur;

/**
 * This is the main method executed from the docker container. If you rename the class, you need to update pom.xml.
 * The code originates from an example module provided by Apeer via apeer.com.
 */
public class ApeerMain {
    public static void main(String[] args) {
        try {
            var adk = new ApeerDevKit();
            var inputImagePath = adk.getInput("input_image", String.class);

            var outputs = new GaussianBlur().run(inputImagePath);

            adk.setFileOutput("output_image", (String) outputs.get("output_image"));
            adk.finalizeModule();

        } catch (ApeerEnvironmentException | ApeerInputException | ApeerOutputException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());
        }
    }
}
