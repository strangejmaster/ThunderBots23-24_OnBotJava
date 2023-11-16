package club.LibertyRobotics;

// This file contains all configuration stuff
public final class CONFIG {
    public final class CONTROLLER {
        // How far a joystick has to move before anything is detected
        public final static float STICK_DEADZONE = 0.60f;
        
        // Minimum deadzone before the triggers activate
        public final static float TRIGGER_DEADZONE = 0.40f;

        // **EXPERIMENTAL** (NOW RECOMMENEDED)
        // Tries to blend multiple moves together for quicker movement
        public final static boolean SMOOTH_DRIVING = true;

        // The mode for driving (0 = Mecanum, 1 = Tank, 2 = Arcade)
        public final static int DRIVE_MODE = 0;

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

                // Left Trigger
                {1f, -1f, 
                1f, -1f},

                // Right Trigger
                {-1f, 1f, 
                -1f, 1f}
            },

            // Tank Driving
            {
                // Left-Stick Up
                {1f, 1f, 
                1f, 1f},

                // Left-Stick Down
                {-1f, -1f, 
                -1f, -1f},

                // Right-Stick Right
                {1f, -1f,
                1f, -1f},

                // Right-Stick Left
                {-1f, 1f, 
                -1f, 1f},

                // Left Trigger
                {-1f, 1f, 
                -1f, 1f},
                
                // Right Trigger
                {1f, -1f, 
                1f, -1f}
            },

            // Arcade Driving
            {
                // Left-Stick Up
                {1f, 1f, 
                1f, 1f},

                // Left-Stick Down
                {-1f, -1f, 
                -1f, -1f},

                // Right-Stick Right
                {1f, 0f, 
                1f, 0f},

                // Right-Stick Left
                {0f, 1f, 
                0f, 1f},

                // Left Trigger
                {0f, 0f, 
                0f, 0f},

                // Right Trigger
                {0f, 0f, 
                0f, 0f}
            }
        };
    }
    public final class DRIVETRAIN {
        // A value between 0 and 1 which the motor power will be set to
        public final static float SPEED = 0.50f;

        // Radius of the drive train wheels in mm
        public final static float WHEEL_RADIUS = 6.0f;

        // String Values which correspond to the DriverHub's hardware map
        public final static String FRONT_RIGHT = "FRMotor";
        public final static String FRONT_LEFT = "FLMotor";
        public final static String BACK_RIGHT = "BRMotor";
        public final static String BACK_LEFT = "BLMotor";
    }

    public final class CONTROL_SURFACES {
        public final static String HOOK_MOTOR = "HMotor";

        // String value that corresponds to the servo device for the servo which launches the paper airplane
        public final static String SERVO_PLANE = "SERPlane";
        // The servo's range in degrees, [0] is the minimum range, [1] is the maximum range
        public final static int[] SERVO_PLANE_RANGE = {0, 360};
    }
}