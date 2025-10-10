package com.pedropathing.ftc;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "TurretPID")
public class TurretPID extends OpMode {

    /*
    +-------------------+--------------+-------------+-------------------+--------------------+-----------+
    |                   |  Rise Time   | Overshoot   |  Settling Time    | Steady-State Error | Stability |
    +-------------------+--------------+-------------+-------------------+--------------------+-----------+
    | Increasing Kp     |  Decrease    |  Increase   |  Small Increase   |  Decrease          |  Degrade  |
    | Increasing Ki     |  Small Decr. |  Increase   |  Increase         |  Large Decrease    |  Degrade  |
    | Increasing Kd     |  Small Decr. |  Decrease   |  Decrease         |  Minor Change      |  Improve  |
    +-------------------+--------------+-------------+-------------------+--------------------+-----------+
     */

    // TODO: TUNE PID VALUES
    DcMotor motor;
    public static double integralSum = 0;
    public static double kp = 0;
    public static double ki = 0;
    public static double kd = 0;
    public static double target = 0;
    public static double targetAngle = 90;
    double targetAngleRadians = Math.toRadians(targetAngle);

    IMU imu;

    ElapsedTime timer = new ElapsedTime();
    public double lastError = 0;

    @Override
    public void init() {
        motor = hardwareMap.get(DcMotor.class, "motor");
        IMU imu = hardwareMap.get(IMU.class, "imu");
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                // TODO: FIND OUT REV ORIENTATIONS
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD
        ));
        imu.initialize(parameters);
        imu.resetYaw();
    }

    @Override
    public void loop() {
        /* encoder values
        double power = PIDControl(target, motor.getCurrentPosition());
        motor.setPower(power);
        */

        /*
        IMU/ Gyro values
        YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();
        double currentAngle = orientation.getYaw(AngleUnit.RADIANS);
        double power = PIDControl(targetAngleRadians, currentAngle);
        motor.setPower(power);
         */
    }

    public double PIDControl(double reference, double state) {
        // IF CONTINUOUS
        // double error = angleWrap(reference - state);
        double error = reference - state;
        integralSum += error * timer.seconds();
        double derivitive = (error - lastError) / timer.seconds();
        lastError = error;



        timer.reset();

        double output = (error * kp) + (derivitive * kd) + (integralSum * ki);
        return output;
    }

    // IF CONTINUOUS
    public double angleWrap(double radians) {
        while (radians > Math.PI) {
            radians -= 2 * Math.PI;
        }
        while (radians < Math.PI) {
            radians += 2 * Math.PI;
        }
        return radians;
    }

}
