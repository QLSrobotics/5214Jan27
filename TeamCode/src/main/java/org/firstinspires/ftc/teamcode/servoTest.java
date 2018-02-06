package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by hima on 2/3/18.
 */

@TeleOp(name="servo test", group="Team5214")
//@Disabled
public class servoTest extends LinearOpMode {

    private Servo servoTest;

    @Override
    public void runOpMode() throws InterruptedException{
        servoTest = hardwareMap.get(Servo.class, "CSF");
        servoTest.setPosition(0);
        double servoset = 0;


        waitForStart();

        while (opModeIsActive()){


            if(gamepad1.a){servoset=servoset+.1;}
            if(gamepad1.b){servoset=servoset-.1;}
            servoTest.setPosition(servoset);
            telemetry.addData("servo Pos", servoset);
            telemetry.update();

        }
    }
}
