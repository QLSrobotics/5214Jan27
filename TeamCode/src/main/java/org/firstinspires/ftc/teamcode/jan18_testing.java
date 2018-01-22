package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;


@Autonomous(name="jan18_testing", group="Team5214")
//@Disabled
public class jan18_testing extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    //declare drive motors
    private DcMotor leftBack;
    private DcMotor rightBack;
    private DcMotor leftFront;
    private DcMotor rightFront;
    // declare dump servo
    private Servo leftDump;
    private Servo rightDump;
    // declare color servo
    private Servo colorServo;
    private String colorid;
    // declare color sensor
    ColorSensor colorFront;
    ColorSensor colorBack;



    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        //mapping drive motors to configuration
        leftBack  = hardwareMap.get(DcMotor.class, "LB");
        rightBack = hardwareMap.get(DcMotor.class, "RB");
        leftFront  = hardwareMap.get(DcMotor.class, "LF");
        rightFront = hardwareMap.get(DcMotor.class, "RF");

        //mapping dump servos to configuration
        leftDump  = hardwareMap.get(Servo.class, "LD");
        rightDump = hardwareMap.get(Servo.class, "RD");

        //mapping color servo to configuration
        colorServo  = hardwareMap.get(Servo.class, "COLORSERVO");

        //mapping color sensor to configuration
        colorFront  = hardwareMap.get(ColorSensor.class, "CSF");
        colorBack = hardwareMap.get(ColorSensor.class, "CSB");

        //drive motor directions
        leftBack.setDirection(DcMotor.Direction.FORWARD);
        rightBack.setDirection(DcMotor.Direction.REVERSE);
        leftFront.setDirection(DcMotor.Direction.FORWARD);
        rightFront.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            arm(0.1);


            sleep(1000);

            colorid = checkColor(colorFront, colorBack, 1.25);
            telemetry.addLine(colorid);
            telemetry.update();


            sleep(1000);

            if(colorid == "BLUE") {
                turn(.25, 200);
                sleep(1000);
                arm(.9);
                sleep(1000);
                turn(-.25, 200);
                sleep(1000);
                driveStraight(.25, 2000);
                sleep(1000);
                turn(-.25,500);
                sleep(1000);
                driveStraight(-.25, 500);
                sleep(1000);
                driveStraight(.25,250);
                sleep(1000);
                dump(.15,.85);
                sleep(1000);
                driveStraight(.25, 250);
                sleep(1000);
                driveStraight(-.25, 250);
                sleep(1000);
                driveStraight(.25, 250);
                sleep(1000);
                dump(.7, .3);
                sleep(1000);
                sleep(1000);
                sleep(30000);

            }
            else if(colorid == "RED") {
                turn(-.25, 200);
                sleep(1000);
                arm(.9);
                sleep(1000);
                turn(.25, 200);
                sleep(1000);
                driveStraight(.25, 2000);
                sleep(1000);
                turn(-.25,500);
                sleep(1000);
                driveStraight(-.25, 500);
                sleep(1000);
                driveStraight(.25,250);
                sleep(1000);
                dump(.15,.85);
                sleep(1000);
                driveStraight(.25, 250);
                sleep(1000);
                driveStraight(-.25, 250);
                sleep(1000);
                driveStraight(.25, 250);
                sleep(1000);
                dump(.7, .3);
                sleep(1000);
                sleep(1000);
                sleep(30000);
            }
            else if (colorid == "UNDEF") {

                arm(.9);
                sleep(1000);
                turn(-.25, 200);
                sleep(1000);
                driveStraight(.25, 2000);
                sleep(1000);
                turn(-.25,500);
                sleep(1000);
                driveStraight(-.25, 500);
                sleep(1000);
                driveStraight(.25,250);
                sleep(1000);
                dump(.15,.85);
                sleep(1000);
                driveStraight(.25, 250);
                sleep(1000);
                driveStraight(-.25, 250);
                sleep(1000);
                driveStraight(.25, 250);
                sleep(1000);
                dump(.7, .3);
                sleep(1000);
                sleep(1000);
                sleep(30000);
            }


            telemetry.update();
            //break;
        }
    }
    private void driveStraight (double power, int time) {
        leftBack.setPower(power);
        rightBack.setPower(power);
        leftFront.setPower(power);
        rightFront.setPower(power);

        sleep(time);

        leftBack.setPower(0);
        rightBack.setPower(0);
        leftFront.setPower(0);
        rightFront.setPower(0);
    }
    private void turn(double power, int time){ //left turn is positive power
        leftBack.setPower(-power); //sets left wheels to move backward
        leftFront.setPower(-power);
        rightBack.setPower(power); // makes right hand wheels to move forward
        rightFront.setPower(power);
        sleep(time); //those things happen for this amount of time
        //and then all the wheels stop
        leftBack.setPower(0);
        leftFront.setPower(0);
        rightBack.setPower(0);
        rightFront.setPower(0);
    }
    private void dump(double left, double right) {
        leftDump.setPosition(left);
        rightDump.setPosition(right);
    }
    private void arm(double position) {

        colorServo.setPosition(position);
    }
    private void sleep(int i) {
        long initial_time = System.currentTimeMillis();
        while (System.currentTimeMillis() - initial_time < i) {

        }
    }
    public String checkColor(ColorSensor front, ColorSensor back, double ratio) {
        double redOverBluFront = (front.red()+1)/(front.blue()+1); //red over blue ratio for front color sensor
        double redOverBluBack = (back.red()+1)/(back.blue()+1);//red over blue ratio for back color sensor
        if(1/redOverBluBack >= ratio && redOverBluFront >= ratio){ //if front is red and back is blue, return red
            return "RED";
        }
        else if (((redOverBluBack)>=ratio) && ((1/redOverBluFront)>=ratio)){ //if front is blue and back is red, return blue
            return "BLUE";
        }
        else if (((1/redOverBluBack)>=ratio) && ((redOverBluFront)<=ratio) && ((redOverBluFront)>=1/ratio)){ //if back is blue and front is unsure, return red
            return "RED";
        }
        else if (((redOverBluBack)>=ratio) && ((redOverBluFront)<=ratio) && ((redOverBluFront)>=1/ratio)){ //if back is red and front is unsure, return blue
            return "BLUE";
        }
        else if (((1/redOverBluFront)>=ratio) && ((redOverBluBack)<=ratio) && ((redOverBluBack)>=1/ratio)){ //if front is blue and back is unsure, return blue
            return "BLUE";
        }
        else if (((redOverBluFront)>=ratio) && ((redOverBluBack)<=ratio) && ((redOverBluBack)>=1/ratio)){ //if front is red and back is unsure, return red
            return "RED";
        }
        else { //if none of the above, we don't know what's happening, so return undefined.
            return "UNDEF";
        }
    }
}
