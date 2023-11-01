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
    }
    public final class DRIVETRAIN {
        // A value between 0 and 1 which the motor power will be set to
        public final static float SPEED = 0.15f;

        // Radius of the drive train wheels in mm
        public final static float WHEEL_RADIUS = 6.0f;

        // String Values which correspond to the DriverHub's hardware map
        public final static String FRONT_RIGHT = "FRMotor";
        public final static String FRONT_LEFT = "FLMotor";
        public final static String BACK_RIGHT = "FRMotor";
        public final static String BACK_LEFT = "FLMotor";
    }
}