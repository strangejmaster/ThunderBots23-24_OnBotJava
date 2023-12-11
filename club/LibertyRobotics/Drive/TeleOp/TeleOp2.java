package club.LibertyRobotics.Drive.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.*;
import org.firstinspires.ftc.robotcore.external.*;

import java.lang.Math;

import club.LibertyRobotics.CONFIG;

@TeleOp
public class Teleop2 extends OpMode {
    // Quick use CONFIG values
	float SPEED = CONFIG.DRIVETRAIN.SPEED;
    float STICK_DEADZONE = CONFIG.CONTROLLER.STICK_DEADZONE;
    int DRIVE_MODE = CONFIG.CONTROLLER.DRIVE_MODE;

    // Power Matrixes for different modes
    public final static float[][][] DRIVE_ARRAY = {
        // Mecanum Driving
        {
            // Left-Stick Up
            {1f, 1f, 
            1f, 1f},

            // Left-Stick Down
            {-1f, -1f, 
            -1f, -1f},

            // Right-Stick Right
            {1f, -1f, 
            -1f, 1f},

            // Right-Stick Left
            {-1f, 1f, 
            1f, -1f},

            // Right Trigger
            {1f, -1f, 
            1f, -1f},

            // Left Trigger
            {-1f, 1f, 
            -1f, 1f}
        }
    };

    // To make the joysticks easier to understand there are 4 boxes placed on each joystick (Imaginary)
    // If the joystick is in the center the box is 0, right or up is 1, and left or down is -1
    // l and r corresponds to left and right joysticks
    // The X and Y are the axis that the boxes are on
    int lXBox = 0;
    int lYBox = 0;
    int rXBox = 0;
    int rYBox = 0;

	// Applys
	float leftApply = 1;
	float rightApply = 1;
	float max = 1;

    // Array which indicates if a wheel will be powered on this tick (Power Matrix)
    // In this order FL, FR, BL, BR
    float[] powMat = {0f, 0f, 
                      0f, 0f};

    // Drivetrain motors
    DcMotor mtFL = null; // Front Left
    DcMotor mtFR = null; // Front Right
    DcMotor mtBL = null; // Back Left
    DcMotor mtBR = null; // Back Right
    
    // Control Surfaces
    Servo drone = null;

    public void init() {
    // Setup motors
      // Motors are first identified with the 'mt' (Motor)
      // They're then identified with F (Front) or B (Back)
      // Next the side is identified with L (Left) or R (Right)  
        mtFL = hardwareMap.get(DcMotor.class, CONFIG.DRIVETRAIN.FRONT_LEFT);
        mtFR = hardwareMap.get(DcMotor.class, CONFIG.DRIVETRAIN.FRONT_RIGHT);
        mtBL = hardwareMap.get(DcMotor.class, CONFIG.DRIVETRAIN.BACK_LEFT);
        mtBR = hardwareMap.get(DcMotor.class, CONFIG.DRIVETRAIN.BACK_RIGHT);
        
        mtFL.setDirection(intToDir(CONFIG.DRIVETRAIN.FL_DIR));
        mtFR.setDirection(intToDir(CONFIG.DRIVETRAIN.FR_DIR));
        mtBL.setDirection(intToDir(CONFIG.DRIVETRAIN.BL_DIR));
        mtBR.setDirection(intToDir(CONFIG.DRIVETRAIN.BR_DIR));


        // Configure drone
        drone = hardwareMap.get(Servo.class, CONFIG.CONTROL_SURFACES.DRONE);
		drone.setPosition(0.5);
        // int[] paLaRange = CONFIG.CONTROL_SURFACES.SERVO_PLANE_RANGE[0];
        // Scale paLa rotation range to specified range (Also convert from 0 to 360 degrees from 0 to 1 max)
    }

    public void loop() {
		// Define Things
		SPEED = CONFIG.DRIVETRAIN.SPEED;
		
		powMat = new float[] {0f, 0f,
							  0f, 0f};
		leftApply = 1;
		rightApply = 1;

		// Y-values are inverted on gamepad as up returns negative values
		lXBox = calcBox(gamepad1.left_stick_x, STICK_DEADZONE);
		lYBox = calcBox(-gamepad1.left_stick_y, STICK_DEADZONE);
		rXBox = calcBox(gamepad1.right_stick_x, STICK_DEADZONE);
		rYBox = calcBox(-gamepad1.right_stick_y, STICK_DEADZONE);

		// If the gamepad's x button is pressed launch the plane
		if (gamepad1.x) {
			drone.setPosition( 1 );
		}
		// If the gamepad's b button is reset position of servo
		if (gamepad1.b) {
			drone.setPosition(0.5);
		}
        
		// Left joystick is active
		if (lYBox != 0) {
			if (lYBox == 1) {
				powMat = addMat(powMat, DRIVE_ARRAY[DRIVE_MODE][0]);
			}
			else if (lYBox == -1) {
				powMat = addMat(powMat, DRIVE_ARRAY[DRIVE_MODE][1]);
			}
		} 
		// Right joystick is active
		if (rXBox != 0) {
			if (rXBox == 1) {
				powMat = addMat(powMat, DRIVE_ARRAY[DRIVE_MODE][2]);
			}
			else if (rXBox == -1) {
				powMat = addMat(powMat, DRIVE_ARRAY[DRIVE_MODE][3]);
			}
		}

		// Turning
		if (gamepad1.right_trigger > CONFIG.CONTROLLER.TRIGGER_DEADZONE) {
			powMat = addMat(powMat, DRIVE_ARRAY[DRIVE_MODE][4]);
		} else if (gamepad1.left_trigger > CONFIG.CONTROLLER.TRIGGER_DEADZONE) {
			powMat = addMat(powMat, DRIVE_ARRAY[DRIVE_MODE][5]);
		}

		if (gamepad1.left_bumper || gamepad1.right_bumper) {
			SPEED *= 0.50;
		}
/*
		int[] pos = {mtFL.getCurrentPosition() * CONFIG.DRIVETRAIN.FL_DIR,
					 mtFR.getCurrentPosition() * CONFIG.DRIVETRAIN.FR_DIR,
					 mtBL.getCurrentPosition() * CONFIG.DRIVETRAIN.BL_DIR,
					 mtBR.getCurrentPosition() * CONFIG.DRIVETRAIN.BR_DIR
					};*/

		// float left = (pos[0] + pos[2]) / 2.0;
		// float right = (pos[1] + pos[3]) / 2.0;


		// float ltr = left / right;

		/*if (ltr >= 1) {
				leftApply = 1 / ltr;
			} else {
				rightApply = 1 / ltr;
			}

			leftApply = (Math.min(leftApply, 1) || Math.max(leftApply, -1));*/
/*
		telemetry.addData("Front Left", powMat[0]);
		telemetry.addData("Front Right", powMat[1]);
		telemetry.addData("Back Left", powMat[2]);
		telemetry.addData("Back Right", powMat[3]);

		telemetry.addData("Right Stick X: ", gamepad1.right_stick_x);
		telemetry.addData("Left Stick Y: ", gamepad1.left_stick_y);

		telemetry.addData("lYBox: ", lYBox);
		telemetry.addData("rXBox: ", rXBox);*/
		
		for (int i = 0; i < powMat.length; i++) {
            if (Math.abs(powMat[i]) > max) {
                max = abs;
            }
		}
		for (int i = 0; i < powMat.length; i++) {
            powMat[i] /= max;
        }
		
		mtFL.setPower(powMat[0] * SPEED * leftApply);
		mtFR.setPower(powMat[1] * SPEED * rightApply);
		mtBL.setPower(powMat[2] * SPEED * leftApply);
		mtBR.setPower(powMat[3] * SPEED * rightApply);	

		// telemetry.update();
    }

    // Returns an array where each value of matrixB is added to matrixA
    public static float[] addMat(float[] matrixA, float[] matrixB) {
        float[] matrix = new float[matrixA.length];
        
        for (int i = 0; i < matrixA.length; i++) {
            matrix[i] = matrixA[i] + matrixB[i];
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
        return 0;
    }

    public static DcMotorSimple.Direction intToDir(int dir) {
        return (dir == 1) ? DcMotorSimple.Direction.FORWARD : DcMotorSimple.Direction.REVERSE;
    }
}