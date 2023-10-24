package org.firstinspires.ftc.test;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;


@TeleOp
public class DrivetrainControl extends OpMode{
    DcMotor mtFR = null;
    DcMotor mtFL = null;
    double deadzone = 0.05;

    // Positions of joysticks
    float leftX = 0;
    float leftY = 0;

    float rightX = 0;
    float rightY = 0;

    public void init() {
    // Setup motors
        // Motors are first identified with the 'mt' (Motor)
        // They're then identified with F (Front) or B (Back)
        // Next the side is identified with L (Left) or R (Right) 
        
        // Map the motors
        mtFR = hardwareMap.get(DcMotor.class, CONFIG.DRIVETRAIN.FRONT_RIGHT);
        mtFL = hardwareMap.get(DcMotor.class, CONFIG.DRIVETRAIN.FRONT_LEFT);
        mtBR = hardwareMap.get(DcMotor.class, CONFIG.DRIVETRAIN.BACK_RIGHT);
        mtBL = hardwareMap.get(DcMotor.class, CONFIG.DRIVETRAIN.BACK_LEFT);

        // What to do if there's no power being provided
        // mtFR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        // mtFL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        // mtFR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        // mtFL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // mtFR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        // mtFL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        // mtFR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        // mtFL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    
        // mtFR.setDirection(DcMotorSimple.Direction.FORWARD);
        // mtFL.setDirection(DcMotorSimple.Direction.FORWARD);

    // Gamepad setup
        gamepad1 = new GamePad();
        gamepad1.setJoyStickDeadzone(CONFIG.CONTROLLER.DEADZONE);
    }
    

    public void loop() {
        // If the gamepad has input from a joystick then run
        if( !gamepad1.atRest() ) {
            leftX = gamepad1.left_stick_x;
            leftY = gamepad1.left_stick_y;
            rightX = gamepad1.right_stick_x;
            rightY = gamepad1.right_stick_y;

            float dz = CONFIG.CONTROLLER.PROCCESS_DEADZONE;
            float th = CONFIG.DRIVETRAIN.THROTTLE;

            // Array to store what each wheel will do at the end of the end of the cycle
            // In this order FL, FR, BL, BR
            float[] wheelMoves = {0, 0, 
                                  0, 0};

            // Diagonal Movement
            if (-dz > leftX && dz < leftY) {
                // D FL
                wheelMoves = {0, 1, 
                              1, 0};
            }
            if (dz < leftX && -dz > leftY) {
                // D BL
                wheelMoves = {0, -1, 
                              -1, 0};
            }
            if (dz < leftX && dz < leftY) {
                // D FR
                wheelMoves = {1, 0, 
                              0, 1};
            }
            if (-dz > leftX && -dz > leftY) {
                // D BL
                wheelMoves = {-1, 0, 
                              0, -1};
            }

            mtFL.setPower(wheelMoves[0] * th);
            mtFR.setPower(wheelMoves[1] * th);
            mtBL.setPower(wheelMoves[2] * th);
            mtBR.setPower(wheelMoves[3] * th);
        }
    }

}
