package club.LibertyRobotics.Drive.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.*;

import java.lang.Math;

import club.LibertyRobotics.CONFIG;

@TeleOp
public class Teleop extends OpMode {
    // Quick use CONFIG values
    float SPEED = CONFIG.DRIVETRAIN.SPEED;
    float STICK_DEADZONE = CONFIG.CONTROLLER.STICK_DEADZONE;
    boolean SMOOTH_DRIVING = CONFIG.CONTROLLER.SMOOTH_DRIVING;
    float[][][] DRIVE_ARRAY = CONFIG.CONTROLLER.DRIVE_ARRAY;
    int DRIVE_MODE = CONFIG.CONTROLLER.DRIVE_MODE;

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
    float[] powMat = {0f, 0f, 
                      0f, 0f};

    DcMotor mtFL = null; // Front Left
    DcMotor mtFR = null; // Front Right
    DcMotor mtBL = null; // Back Left
    DcMotor mtBR = null; // Back Right

    public void init() {
    // Setup motors
      // Motors are first identified with the 'mt' (Motor)
      // They're then identified with F (Front) or B (Back)
      // Next the side is identified with L (Left) or R (Right)  
        mtFL = hardwareMap.get(DcMotor.class, CONFIG.DRIVETRAIN.FRONT_LEFT);
        mtFR = hardwareMap.get(DcMotor.class, CONFIG.DRIVETRAIN.FRONT_RIGHT);
        mtBL = hardwareMap.get(DcMotor.class, CONFIG.DRIVETRAIN.BACK_LEFT);
        mtBR = hardwareMap.get(DcMotor.class, CONFIG.DRIVETRAIN.BACK_RIGHT);

        mtFL.setDirection(DcMotorSimple.Direction.FORWARD);
        mtFR.setDirection(DcMotorSimple.Direction.REVERSE);
        mtBL.setDirection(DcMotorSimple.Direction.FORWARD);
        mtBR.setDirection(DcMotorSimple.Direction.REVERSE);

    // Setup Gamepad
        // gamepad1 and gamepad2 are inherited from the OpMode class
        gamepad1.setJoystickDeadzone(STICK_DEADZONE);
    }
    

    public void loop() {
        // Once a joystick has left the deadzone check whats going on
        if( !gamepad1.atRest() ) {
            powMat = new float[] {0f, 0f,
                                  0f, 0f};
            
            
            // Y-values are inverted on gamepad as up returns negative values
            lXBox = calcBox(gamepad1.left_stick_x, STICK_DEADZONE);
            lYBox = calcBox(-gamepad1.left_stick_y, STICK_DEADZONE);
            rXBox = calcBox(gamepad1.right_stick_x, STICK_DEADZONE);
            lXBox = calcBox(-gamepad1.right_stick_y, STICK_DEADZONE);
        
            // Left joystick is active
            if (lYBox != 0) {
                switch(lYBox) {
                    case 0:
                        break;
                    case 1:
                        powMat = addMat(powMat, DRIVE_ARRAY[DRIVE_MODE][0], SMOOTH_DRIVING);
                        break;
                    case -1:
                        powMat = addMat(powMat, DRIVE_ARRAY[DRIVE_MODE][1], SMOOTH_DRIVING);
                    default:
                        break;
                }
            } 
            // Right joystick is active
            if (rXBox != 0) {
                switch(rXBox) {
                    case 0:
                        break;
                    case 1:
                        powMat =  addMat(powMat, DRIVE_ARRAY[DRIVE_MODE][2], SMOOTH_DRIVING);
                    case -1:
                        powMat =  addMat(powMat, DRIVE_ARRAY[DRIVE_MODE][3], SMOOTH_DRIVING);
                    default:
                        break;
                }
            }

            // Turning
            if (gamepad1.left_trigger > CONFIG.CONTROLLER.TRIGGER_DEADZONE) {
                powMat = addMat(powMat, DRIVE_ARRAY[DRIVE_MODE][4], SMOOTH_DRIVING);
            }
            if (gamepad1.right_trigger > CONFIG.CONTROLLER.TRIGGER_DEADZONE) {
                powMat = addMat(powMat, DRIVE_ARRAY[DRIVE_MODE][5], SMOOTH_DRIVING);
            }
      
            mtFL.setPower(powMat[0] * SPEED);
            mtFR.setPower(powMat[1] * SPEED);
            mtBL.setPower(powMat[2] * SPEED);
            mtBR.setPower(powMat[3] * SPEED);
        }
    }

    // Returns an array where each value of matrixB is added to matrixA
    public static float[] addMat(float[] matrixA, float[] matrixB, boolean smooth) {
        float[] matrix = matrixA;

        if (!smooth) {
            return matrixB;
        }
        
        for (int i = 0; i < matrixA.length; i++) {
            matrix[i] = (matrixA[i] + matrixB[i]) / 2.0f;
        }
        return matrix;
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