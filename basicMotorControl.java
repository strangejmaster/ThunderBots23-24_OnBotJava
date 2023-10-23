package org.firstinspires.ftc.test;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;


@TeleOp
public class Test1 extends OpMode{
    DcMotor RightMotor = null;
    DcMotor LeftMotor = null;
    double deadzone = 0.05;
    
    public void init() {
        RightMotor = hardwareMap.get(DcMotor.class, "RM");
        LeftMotor = hardwareMap.get(DcMotor.class, "LM");
        
        RightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        LeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
    }
    
    public void loop() {
        double absValY = (gamepad1.left_stick_y > 0) ? gamepad1.left_stick_y : -gamepad1.left_stick_y;
        if (absValY > deadzone) {
            RightMotor.setPower(-gamepad1.left_stick_y);
            LeftMotor.setPower(-gamepad1.left_stick_y);
        }    
    }
}
