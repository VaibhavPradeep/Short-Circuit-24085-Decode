package com.pedropathing.ftc;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;

import java.util.List;

public class ColorSensorTest extends OpMode {

    ColorSensor colorSensor;

    @Override
    public void init() {
        colorSensor = hardwareMap.get(ColorSensor.class, "colorSensor");
    }

    @Override
    public void loop() {
        telemetry.addData("Red: ", colorSensor.red());
        telemetry.addData("Green: ", colorSensor.green());
        telemetry.addData("Red: ", colorSensor.blue());

        List<double[]> colors = List.of();

        double r = colorSensor.red();
        double g = colorSensor.green();
        double b = colorSensor.blue();

        // Add to list
        colors.add(new double[]{r, g, b});

        telemetry.addData("averages: ", averageDoubles(colors));


    }

    public static List<double[]> averageDoubles(List<double[]> list) {

        double sumX = 0;
        double sumY = 0;
        double sumZ = 0;
        for (double[] t: list) {
            sumX += t[0];
            sumY += t[0];
            sumZ += t[0];
        }

        int n = list.size();
        List<double[]> averages = List.of(new double[]{sumX / n, sumY / n, sumZ / n});
        return averages;
    }

}
