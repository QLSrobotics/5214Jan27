package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


@Autonomous(name="RedRelic", group="Team5214")
//@Disabled
public class RedRelic extends LinearOpMode {

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
    private Servo flickServo;
    private String colorid;
    // declare color sensor
    private ColorSensor colorFront;

    //use the two variables in two color sensors situation
//    ColorSensor colorFront;
//    ColorSensor colorBack;

    final double currentRatio = 1.3; //ratio set for red/blue, for color id function



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
        colorServo = hardwareMap.get(Servo.class, "COLORSERVO");
        flickServo = hardwareMap.get(Servo.class, "FLICKSERVO");

        //mapping color sensor to configuration
        colorFront = hardwareMap.get(ColorSensor.class, "CSF");

        //use the two mapping where there are two color sensors
//        colorFront  = hardwareMap.get(ColorSensor.class, "CSF");
//        colorBack = hardwareMap.get(ColorSensor.class, "CSB");

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

            //color gives the output of the front ball (the one which is closer to colorFront sensor)
            colorid = checkColor(colorFront, currentRatio);
            //print color state and update on display
            telemetry.addLine(colorid);
            telemetry.update();

            sleep(1000);

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
    private void turn(double power, int time){
        //left turn is positive power
        leftBack.setPower(-power); //sets left wheels to move backward
        leftFront.setPower(-power);
        rightBack.setPower(power); // makes right hand wheels to move forward
        rightFront.setPower(power);
        sleep(time);
        //those things happen for this amount of time and then all the wheels stop
        leftBack.setPower(0);
        leftFront.setPower(0);
        rightBack.setPower(0);
        rightFront.setPower(0);
    }
    private void dump(double left, double right) {
        //setting the two dump servo to an input value
        leftDump.setPosition(left);
        rightDump.setPosition(right);
    }
    private void flicker(double position) {
        //setting the flicker servo to an input value
        flickServo.setPosition(position);
        sleep(2000);
        flickServo.setPosition(0.5);

    }
    private void arm(double position) {
        //setting the color servo to an input value
        colorServo.setPosition(position);
    }
    private void sleep(int i) {
        //initial time takes the current hardware time in milliseconds
        long initial_time = System.currentTimeMillis();
        //inside the while loop cpu will stop working when the input time is more than the time passed in this loop
        //cpu will be back working when the loop reaches the target time
        while (System.currentTimeMillis() - initial_time < i) {

        }
    }
    public String checkColor(ColorSensor sensor, double ratio) {
        double redOverBlue = (sensor.red()+1) / (sensor.blue() + 1);
        if (redOverBlue >= ratio) {
            //if it is greater than ratio, it is red
            return "RED";
        }
        else if (redOverBlue <= ratio) {
            //if it is less than ratio, it is blue
            return "BLUE";
        }
        else {
            //if nothing is detected, return not defined
            return "UNDEF";
        }
    }
//    public String checkTwoColor_(ColorSensor frontSensor, ColorSensor backSensor, double ratio) {
//        double redOverBlueFront = (frontSensor.red()+1)/(frontSensor.blue()+1); //red over blue ratio for front color sensor
//        double redOverBlueBack = (backSensor.red()+1)/(backSensor.blue()+1);//red over blue ratio for back color sensor
//        if(1/redOverBlueBack >= ratio && redOverBlueFront >= ratio){ //if front is red and back is blue, return red
//            return "RED";
//        }
//        else if (((redOverBlueBack)>=ratio) && ((1/redOverBlueFront)>=ratio)){
//            //if front is blue and back is red, return blue
//            return "BLUE";
//        }
//        else if (((1/redOverBlueBack)>=ratio) && ((redOverBlueFront)<=ratio) && ((redOverBlueFront)>=1/ratio)){
//            //if back is blue and front is unsure, return red
//            return "RED";
//        }
//        else if (((redOverBlueBack)>=ratio) && ((redOverBlueFront)<=ratio) && ((redOverBlueFront)>=1/ratio)){
//            //if back is red and front is unsure, return blue
//            return "BLUE";
//        }
//        else if (((1/redOverBlueFront)>=ratio) && ((redOverBlueBack)<=ratio) && ((redOverBlueBack)>=1/ratio)){
//            //if front is blue and back is unsure, return blue
//            return "BLUE";
//        }
//        else if (((redOverBlueFront)>=ratio) && ((redOverBlueBack)<=ratio) && ((redOverBlueBack)>=1/ratio)){
//            //if front is red and back is unsure, return red
//            return "RED";
//        }
//        else {
//            //if none of the above, we don't know what's happening, so return undefined.
//            return "UNDEF";
//        }
//    }
}
