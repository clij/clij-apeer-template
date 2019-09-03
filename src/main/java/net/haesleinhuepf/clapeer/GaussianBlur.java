package net.haesleinhuepf.clapeer;

import ij.IJ;
import ij.ImagePlus;
import net.haesleinhuepf.clij.CLIJ;
import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;

import java.util.HashMap;
import java.util.Map;

/**
 * This is a template workflow executing a Gaussian blur on an input image.
 *
 * Author: @haesleinhuepf
 *         September 2019
 */
public class GaussianBlur {
    public Map<String, Object> run(String inputImagePath) {
        var outputs = new HashMap<String, Object>();

        // some command line output for debugging
        System.out.println("clij-apeer-template");
        System.out.println(CLIJ.clinfo());

        ImagePlus imp = IJ.openImage(inputImagePath);

        // init clij / GPU
        CLIJ clij = CLIJ.getInstance();

        // push image to GPU
        ClearCLBuffer input = clij.push(imp);
        // allocate memory for result image
        ClearCLBuffer output = clij.create(input);

        // process image
        clij.op().blur(input, output, 5f, 5f, 0f);

        // pull result back
        ImagePlus result = clij.pull(output);

        IJ.save(result, "output_image.jpg");
        outputs.put("output_image", "output_image.jpg");

        // cleanup
        input.close();
        output.close();

        return outputs;
    }
}