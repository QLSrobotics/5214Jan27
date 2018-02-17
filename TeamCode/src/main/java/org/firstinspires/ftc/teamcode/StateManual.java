package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="StateManual", group="Team5214")
//@Disabled
public class StateManual extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();
    //declares motors
    private DcMotor leftBack;
    private DcMotor rightBack;
    private DcMotor leftFront;
    private DcMotor rightFront;

    private DcMotor liftMotor;
    private DcMotor ramp;
    private DcMotor worm;

    private DcMotor lBelt;
    private DcMotor rBelt;

    private Servo colSer;
    private Servo knckSer;

    private Servo align;

    private Servo rDum;
    private Servo lDum;
    private Servo cDum;
    private Servo wrist;
    private Servo hand;

    private DigitalChannel limtTop;
    private DigitalChannel limtBot;


    boolean limtHit = true;
    boolean go;

    long startTime = 0;


    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        //hooks up all of these motors with the config file
        leftBack = hardwareMap.get(DcMotor.class, "LB");
        rightBack = hardwareMap.get(DcMotor.class, "RB");
        leftFront = hardwareMap.get(DcMotor.class, "LF");
        rightFront = hardwareMap.get(DcMotor.class, "RF");

        cDum = hardwareMap.servo.get("CD");
        rDum = hardwareMap.servo.get("RD");
        lDum = hardwareMap.servo.get("LD");
        colSer = hardwareMap.servo.get("COLORSERVO");
        knckSer = hardwareMap.servo.get("FLICKSERVO");
        align = hardwareMap.servo.get("ALIGN1");
        wrist = hardwareMap.servo.get("wrist");
        hand = hardwareMap.servo.get("hand");

        lBelt = hardwareMap.dcMotor.get("LBELT");
        rBelt = hardwareMap.dcMotor.get("RBELT");

        liftMotor = hardwareMap.dcMotor.get("LIFT");
        ramp = hardwareMap.dcMotor.get("ramp");
        worm = hardwareMap.dcMotor.get("worm");

        limtTop = hardwareMap.get(DigitalChannel.class, "touch_top");
        limtBot = hardwareMap.get(DigitalChannel.class, "touch_bot");

        align.setPosition(0);

        knckSer.setPosition(.5);
        colSer.setPosition(.9);

        waitForStart();


        while (opModeIsActive()) {

            colSer.setPosition(.9);
            knckSer.setPosition(.5);
            //game pad one cotrls

            if (gamepad1.dpad_up) {
                lBelt.setPower(-1);
                rBelt.setPower(1);
            }
            if (gamepad1.dpad_left) {
                lBelt.setPower(0.5);
                rBelt.setPower(-0.5);
            }
            if (gamepad1.dpad_down) {
                lBelt.setPower(1);
                rBelt.setPower(-1);
            }
            if (gamepad1.dpad_right) {
                lBelt.setPower(0);
                rBelt.setPower(0);
            }

            if (gamepad1.right_bumper) {
                lDum.setPosition(0.8);
                rDum.setPosition(.21);
                sleep(500);
                cDum.setPosition(0.7);
                lBelt.setPower(1);
                rBelt.setPower(-1);
            }
            if (gamepad1.y) {
                lDum.setPosition(0.72);
                rDum.setPosition(0.28);
                sleep(500);
                cDum.setPosition(0.7);
            }
            if (gamepad1.left_bumper) {
                cDum.setPosition(0.25);
                lDum.setPosition(0.26);
                rDum.setPosition(0.77);
            }
            if (gamepad1.a) {
                lDum.setPosition(0.53);
                rDum.setPosition(0.5);
                cDum.setPosition(0.7);
                lBelt.setPower(0);
                rBelt.setPower(0);
            }

            if (gamepad2.left_trigger >= 0.05) {
                worm.setPower(-gamepad2.left_trigger);
            } else if (gamepad2.right_trigger >= 0.05) {
                worm.setPower(-gamepad2.right_trigger);
            } else {
                worm.setPower(0);
            }

            if (gamepad2.a) {
                wrist.setPosition(0.5);
            }
            if (gamepad2.b) {
                wrist.setPosition(1);
            }
            if (gamepad2.y) {
                hand.setPosition(0.7);
            }
            if (gamepad2.x) {
                hand.setPosition(0.05);
            }

            if (gamepad2.dpad_down) {
            }
            if (gamepad2.dpad_left) {
            }
            if (gamepad2.dpad_right) {
            }
            if (gamepad2.dpad_up) {
            }

            telemetry.update();
            leftBack.setPower(.9*((gamepad1.left_stick_y + gamepad1.left_stick_x) + (.75 * -(gamepad1.right_stick_x))
                    + (.5 * (gamepad1.right_trigger)) + -.5 * (gamepad1.left_trigger)-gamepad2.right_stick_x));

            leftFront.setPower(.9*((gamepad1.left_stick_y - gamepad1.left_stick_x) + (.75 * -(gamepad1.right_stick_x))
                    + (-.5 * (gamepad1.right_trigger)) + .5 * (gamepad1.left_trigger)-gamepad2.right_stick_x));

            rightBack.setPower(.9*((-gamepad1.left_stick_y + gamepad1.left_stick_x) + (.75 * -(gamepad1.right_stick_x))
                    + (.5 * (gamepad1.right_trigger)) + -.5 * (gamepad1.left_trigger)-gamepad2.right_stick_x));

            rightFront.setPower(.9*((-gamepad1.left_stick_y - gamepad1.left_stick_x) + (.75 * -(gamepad1.right_stick_x))
                    + (-.5 * (gamepad1.right_trigger)) + .5 * (gamepad1.left_trigger) -gamepad2.right_stick_x));

            if (gamepad1.dpad_up) {
                lBelt.setPower(-.75);
                rBelt.setPower(.75);
            }
            if (gamepad1.dpad_down) {
                lBelt.setPower(.75);
                rBelt.setPower(-0.75);
            }
            if (gamepad1.dpad_left) {
                colSer.setPosition(.9);
                knckSer.setPosition(.5);
            }
            if (gamepad1.dpad_right) {
                lBelt.setPower(0);
                rBelt.setPower(0);
            }
        }
    }
}