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
    
    public void init() {
    // Setup motors
        // Motors are first identified with the 'mt' (Motor)
        // They're then identified with F (Front) or B (Back)
        // Next the side is identified with L (Left) or R (Right) 
        
        // Map the motors
        mtFR = hardwareMap.get(DcMotor.class, CONFIG.DRIVETRAIN.RIGHT);
        mtFL = hardwareMap.get(DcMotor.class, CONFIG.DRIVETRAIN.RIGHT);

        // What to do if there's no power being provided
        mtFR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        mtFL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        mtFR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        mtFL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    
        mtFR.setDirection(DcMotorSimple.Direction.FORWARD);
        mtFL.setDirection(DcMotorSimple.Direction.FORWARD);

    // Gamepad setup
        gamepad1 = new GamePad();
        gamepad1.setJoyStickDeadzone(CONFIG.CONTROLLER.DEADZONE);
    }
    
    public void loop() {
        if( !gamepad1.atRest() ) {
            mtFR.setPower(-gamepad1.left_stick_y * THROTTLE);
            mtFL.setPower(gamepad1.left_stick_y * THROTTLE);
        }
    }

}
