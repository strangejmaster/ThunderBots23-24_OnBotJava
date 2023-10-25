package org.firstinspires.ftc.test;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import java.lang.Math;


@TeleOp
public class DrivetrainControl extends OpMode{
    DcMotor mtFL = null; // Front Left
    DcMotor mtFR = null; // Front Right
    DcMotor mtBL = null; // Back Left
    DcMotor mtBR = null; // Back Right

    DcMotor[] motors = {mtFL, mtFR, mtBL, mtBR};

    // To make the joysticks easier to understand there are 4 boxes placed on each joystick (Imaginary)
    // If the joystick is in the center the box is 0, right or up is 1, and left or down is -1
    // l and r corresponds to left and right joysticks
    // The X and Y are the axis that the boxes are on
    int lXBox = 0;
    int lYBox = 0;
    int rXBox = 0;
    int rYBox = 0;

    // Array which indicates if a wheel will be powered on this tick (Power Matrix)
    // In this order FL, FR, BL, BR
    float[] powMat = {0, 0, 
                      0, 0};

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

        // Apply configuration settings to the motors quickly
        for (int i = 0; i < motors.length; i++) {
            motors[i].setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            motors[i].setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }

        mtFL.setDirection(DcMotorSimple.Direction.FORWARD);
        mtFR.setDirection(DcMotorSimple.Direction.FORWARD);
        mtBL.setDirection(DcMotorSimple.Direction.FORWARD);
        mtBR.setDirection(DcMotorSimple.Direction.FORWARD);

    // Setup Gamepad
        Gamepad pad = new GamePad();
        pad.setJoyStickDeadzone(CONFIG.CONTROLLER.DEADZONE);
    }
    

    public void loop() {
        // Once a joystick has left the deadzone check whats going on
        if( !gamepad1.atRest() ) {
            powMat = {0, 0, 
                      0, 0};
            
            lXBox = calcBox(pad.left_stick_x, CONFIG.CONTROLLER.DEADZONE);
            lYBox = calcBox(pad.left_stick_y, CONFIG.CONTROLLER.DEADZONE);
            rXBox = calcBox(pad.right_stick_x, CONFIG.CONTROLLER.DEADZONE);
            lXBox = calcBox(pad.right_stick_y, CONFIG.CONTROLLER.DEADZONE);
            
            if (CONFIG.CONTROLLER.ADVANCED_CONTROL == false) {
                // Only the left joystick is active
                if (lXBox != 0 && rYBox == 0) {
                    switch(lXBox)
                        case 0:
                            break;
                        case 1:
                            powMat = {1, 1, 
                                    1, 1};
                            break;
                        case -1:
                            powMat = {-1, -1, 
                                    -1, -1};
                        default:
                            break;
                    }
                } 
                // Only the right joystick is active
                else if (rYBox != 0 && lXBox == 0) {
                    switch(rYBox) {
                        case 0:
                            break;
                        case 1:
                            powerMat = {1, -1, 
                                        -1, 1};
                        case -1:
                            powMat = {-1, 1, 
                                    1, -1};
                        default:
                            break;
                    }
                }
                // Both the joysticks are active (Combos)
                else {
                    if (rYBox == 1) {
                        if (lXBox == 1) {
                            powMat = {1, 0, 
                                      0, 1};
                        }
                        else if (lXBox == -1) {
                            powMat = {0, 1,
                                      1, 0};
                        }
                    }
                    else if (rYBox == -1) {
                        if (lXBox == 1) {
                            powMat = {0, -1, 
                                      -1, 0};
                        }
                        else if (lXBox == -1) {
                            powMat = {-1, 0,
                                      0, -1};
                        }
                    }
                }

            // Turning
                if (pad.left_bumper) {
                    powMat = {-1, 1, 
                              -1, 1};
                }
                if (pad.right_bumper) {
                    powMat = {1, -1, 
                              1, -1};
                }
            }
            else if (CONFIG.CONTROLLER.ADVANCED_CONTROL) {
                // Only the left joystick is active
                if ( (lXBox != 0 || lYBox != 0) && (rXBox == 0 && rYBox == 0) ) {
                    switch(lXBox) {
                        case 0:
                            break;
                        case 1:
                            powerMat = {1, -1, 
                                        -1, 1};
                        case -1:
                            powMat = {-1, 1, 
                                    1, -1};
                        default:
                            break;
                    }
                    switch(lYBox)
                        case 0:
                            break;
                        case 1:
                            powMat = {1, 1, 
                                    1, 1};
                            break;
                        case -1:
                            powMat = {-1, -1, 
                                    -1, -1};
                        default:
                            break;
                    }
                } 
                // Only the right joystick is active
                else if ( (lXBox == 0 && lYBox == 0 ) && (rXBox != 0 || rYBox != 0) ) {

                }
                // Both the joysticks are active (Combos)
                else {

                }
            }
    
            // Untested replacement for code below
            // setMotors(motors, powMat);

            mtFL.setPower(powMat[0] * CONFIG.DRIVETRAIN.SPEED);
            mtFR.setPower(powMat[1] * CONFIG.DRIVETRAIN.SPEED);
            mtBL.setPower(powMat[2] * CONFIG.DRIVETRAIN.SPEED);
            mtBR.setPower(powMat[3] * CONFIG.DRIVETRAIN.SPEED);
        }
    }

    public static void setMotors(DcMotor[] motors, float[] matrix) {
        for (int i = 0; i < motors.length; i++) {
            motors[i].setPower(matrix[i]);
        }
    }

    public static int calcBox(float position, float deadzone) {
        if (Math.abs(position) > deadzone) {
            if (position < 0) {
                return -1;
            }
            return 1;
        } 
        else {
            return 0;
        }
    }
}
