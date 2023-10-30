package club.LibertyRobotics.Drive.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import java.lang.Math;

@TeleOp
public class Teleop extends OpMode {
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

    // Quick use CONFIG values
    float SPEED = CONFIG.DRIVETRAIN.SPEED;
    float DEADZONE = CONFIG.CONTROLLER.DEADZONE;
    boolean SMOOTH_DRIVING = CONFIG.CONTROLLER.SMOOTH_DRIVING;

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
        if( !pad.atRest() ) {
            powMat = new float[] {0, 0, 
                                  0, 0};
            
            
            // Y values must be inverted as going up is -1
            lXBox = calcBox(pad.left_stick_x, DEADZONE);
            lYBox = calcBox(-pad.left_stick_y, DEADZONE);
            rXBox = calcBox(pad.right_stick_x, DEADZONE);
            lXBox = calcBox(-pad.right_stick_y, DEADZONE);
        
            // Only the left joystick is active
            if (lXBox != 0 && rYBox == 0) {
                switch(lXBox) {
                    case 0:
                        break;
                    case 1:
                        powMat = addMat(powMat, new float[]{1, 1, 
                                                            1, 1}, SMOOTH_DRIVING);
                        break;
                    case -1:
                        powMat =  addMat(powMat, new float[]{-1, -1, 
                                                             -1, -1}, SMOOTH_DRIVING);
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
                        powerMat =  addMat(powMat, new float[]{1, -1, 
                                                              -1, 1}, SMOOTH_DRIVING);
                    case -1:
                        powMat =  addMat(powMat, new float[]{-1, 1, 
                                                              1, -1}, SMOOTH_DRIVING);
                    default:
                        break;
                }
            }
            // Both the joysticks are active (Combos)
            else {
                if (rYBox == 1) {
                    if (lXBox == 1) {
                        powMat = addMat(powMat, new float[]{1, 0, 
                                                            0, 1}, SMOOTH_DRIVING);
                    }
                    else if (lXBox == -1) {
                        powMat = addMat(powMat, new float[]{0, 1,
                                                            1, 0}, SMOOTH_DRIVING);
                    }
                }
                else if (rYBox == -1) {
                    if (lXBox == 1) {
                        powMat = addMat(powMat, new float[]{0, -1, 
                                                           -1, 0}, SMOOTH_DRIVING);
                    }
                    else if (lXBox == -1) {
                        powMat = addMat(powMat, new float[]{-1, 0,
                                                             0, -1}, SMOOTH_DRIVING);
                    }
                }
            }

            // Turning
            if (pad.left_trigger) {
                powMat = addMat(powMat, new float[]{-1, 1, 
                                                    -1, 1}, SMOOTH_DRIVING);
            }
            if (pad.right_trigger) {
                powMat = addMat(powMat, new float[]{1, -1, 
                                                    1, -1}, SMOOTH_DRIVING);
            }
    
            // Untested replacement for code below
            // setMotors(motors, powMat);   
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
            matrix[i] = (matrixA[i] + matrixB[i]);
        }
        return matrix;
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