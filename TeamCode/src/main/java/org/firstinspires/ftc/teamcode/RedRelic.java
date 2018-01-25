package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;


@Autonomous(name="RedRelic", group="Team5214")
//@Disabled
public class RedRelic extends LinearOpMode {

    //declare vuforia recognizing engine
    VuforiaLocalizer vuforia;
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
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Status", "Initialized");
        telemetry.update();


        //using phone camera for image recognition
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        //License key, do not change
        parameters.vuforiaLicenseKey = "AVOFXuz/////AAAAGf45mZPfQEQBt1NyBSqlPuYQkVhLXgkSQpOqQqWb/FoWqJ" +
                "WqG7KKeaIVeJzCSsLJ58FGWwE0Z/vvzSHrZBeZN9jN7c+gru1h0T3k0wLaoN1b6bFIHn93evRQ0DcFcgy4uMHZ1" +
                "T87fT4WrKldfG6XT7PyThP2Fk5C8SbASqna7IKl26eb+zdOFXRKG+U1pZyV9yGgMsmBVZCxDZiT/G6JUpg4DMGrZVT" +
                "8BXdVvs+mpDLQ4tH/XL5ikYp1++1fbYhJtA3naS5/laHiPiHONGAdLbHkE4s8EOxpB8+lqpJN6hlcqtMegarTOuwWYXXP" +
                "jSnNnkUWBKuW6nWqtF3k1CIUoSTBuFpbwAvf+T6i1CkL6IoB";
        //using back camera for recognizing
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.FRONT;

        //importing the three image asset and hook up to vuforia engine
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);
        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate"); // can help in debugging; otherwise not necessary


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
        //enable image tracking
        relicTrackables.activate();

        runtime.reset();

        // run until the end of the match (driver presses STOP)

        while (opModeIsActive()){


            RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
            if (vuMark != RelicRecoveryVuMark.UNKNOWN) {

                telemetry.addData("VuMark", "%s visible", vuMark);
                telemetry.update();

            }
            else {
                telemetry.addData("VuMark", "not visible");
                telemetry.update();
            }


            sleep(3000);


            arm(.1); // put arm down
            sleep(2000);

            colorid = checkColor(colorFront, currentRatio);

            telemetry.addLine(colorid);
            telemetry.update();

            sleep(2000);

            if (colorid == "RED"){flickServo.setPosition(.75);
            }else if(checkColor(colorFront,.4) == "BLUE"){flickServo.setPosition(.25);}

            sleep(2000);
            flickServo.setPosition(.5);
            sleep(2000);
            arm(.9); // put arm up
            sleep(2000);

            driveStraight(.25,1000); // drive forward

            sleep(1000);

            turn(.25,1000); // turn right towards glyph

            sleep(1000);

            driveStraight(.25,1000); // drive straight to glyph

            sleep(1000);

            dump(.15,.85); // dump cube

            sleep(1000);

            dump(.7,.3); // reset platform

            sleep(1000);

            driveStraight(.25,200);

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
    private String checkColor(ColorSensor sensor, double ratio) {
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
