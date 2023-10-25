// This file contains all configuration stuff
class CONFIG {
    class CONTROLLER {
        // How far a joystick has to move before anything is detected
        final float DEADZONE = 0.60;

        // More advanced movement + combos, not recommended for competition
        boolean ADVANCED_CONTROL = false;
    }
    class DRIVETRAIN {
        // A value between 0 and 1 which the motor power will be set to
        final float SPEED = 1.00;

        // String Values which correspond to the DriverHub's hardware map
        final String FRONT_RIGHT = "FRMotor";
        final String FRONT_LEFT = "FLMotor";
        final String BACK_RIGHT = "FRMotor";
        final String BACK_LEFT = "FLMotor";
    }
}