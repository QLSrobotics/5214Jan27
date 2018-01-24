package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by aliva on 1/23/18.
 */

@Autonomous(name = "red Relic", group = "Team5214")
public class redRelic extends LinearOpMode {

    //declares motors
    private DcMotor leftBack;
    private DcMotor rightBack;
    private DcMotor leftFront;
    private DcMotor rightFront;

    private DcMotor liftMotor;
    private DcMotor ramp;

    private DcMotor lBelt;
    private DcMotor rBelt;

    private Servo colSer;
    private Servo knckSer;

    private Servo align;

    private Servo rDum;
    private Servo lDum;

    private ColorSensor colorFront;


    private DigitalChannel limtTop;
    private DigitalChannel limtBot;


    @Override
    public void runOpMode() throws InterruptedException {



        leftBack  = hardwareMap.get(DcMotor.class, "LB");
        rightBack = hardwareMap.get(DcMotor.class, "RB");
        leftFront  = hardwareMap.get(DcMotor.class, "LF");
        rightFront = hardwareMap.get(DcMotor.class, "RF");

        rDum = hardwareMap.servo.get("RD");
        lDum = hardwareMap.servo.get("LD");
        colSer =hardwareMap.servo.get("COLORSERVO");
        knckSer = hardwareMap.servo.get("KNOCKSERVO");
        align = hardwareMap.servo.get("ALIGN1");

        lBelt = hardwareMap.dcMotor.get("LBELT");
        rBelt = hardwareMap.dcMotor.get("RBELT");

        liftMotor = hardwareMap.dcMotor.get("LIFT");
        ramp = hardwareMap.dcMotor.get("ramp");

        limtTop = hardwareMap.get(DigitalChannel.class, "touch_top");
        limtBot = hardwareMap.get(DigitalChannel.class, "touch_bot");

        colorFront= hardwareMap.get(ColorSensor.class, "CS");

        align.setPosition(0);

        knckSer.setPosition(.5);
        colSer.setPosition(.9);

        waitForStart();
        while (opModeIsActive()){
            arm(.1); // put arm down
            sleep(1000);
            if (checkColor(colorFront,.4) == "red"){knckSer.setPosition(1);
            }else if(checkColor(colorFront,.4) == "blue"){knckSer.setPosition(0);}

            sleep(1000);

            driveStraight(.25,1000); // drive forward
            turn(.25,1000); // turn right towards glyph
            driveStraight(.25,1000); // drive straight to glyph
            dump(.25,.25); // dump cube
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
        lDum.setPosition(left);
        rDum.setPosition(right);
    }
    private void arm(double position) {
        //setting the color servo to an input value
        colSer.setPosition(position);
    }
    private void sleep(int i) {
        //initial time takes the current hardware time in milliseconds
        long initial_time = System.currentTimeMillis();
        //inside the while loop cpu will stop working when the input time is more than the time passed in this loop
        //cpu will be back working when the loop reaches the target time
        while (System.currentTimeMillis() - initial_time < i) {

        }
    }

    public String checkColor(ColorSensor front, double ratio) {
        return "nothing";
    }
}

