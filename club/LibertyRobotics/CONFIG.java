package club.LibertyRobotics;

// This file contains all configuration stuff
static class CONFIG {
    static class CONTROLLER {
        // How far a joystick has to move before anything is detected
        final static float DEADZONE = 0.60;

        // **EXPERIMENTAL**
        // Tries to blend multiple moves together for quicker movement
        final static boolean SMOOTH_DRIVING = false;
    }
    static class DRIVETRAIN {
        // A value between 0 and 1 which the motor power will be set to
        final static float SPEED = 0.25;

        // String Values which correspond to the DriverHub's hardware map
        final static String FRONT_RIGHT = "FRMotor";
        final static String FRONT_LEFT = "FLMotor";
        final static String BACK_RIGHT = "FRMotor";
        final static String BACK_LEFT = "FLMotor";
    }
}