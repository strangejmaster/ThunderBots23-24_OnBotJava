// This file contains all configuration stuff
class CONFIG {
    class CONTROLLER {
        // Distance from the center of the joystick before the action is considered a movement 
        final float PROCCESS_DEADZONE = 0.75;

        // How far a joystick has to move before anything is detected
        final float DEADZONE = 0.10;
    }
    class DRIVETRAIN {
        // A value between 0 and 1 which the motor power will be set to
        final float FOWARD_SPEED = 1.00;
        final float TURN_SPEED = 1.00;

        // String Values which correspond to the DriverHub's hardware map
        final String FRONT_RIGHT = "FRMotor";
        final String FRONT_LEFT = "FLMotor";
        final String BACK_RIGHT = "FRMotor";
        final String BACK_LEFT = "FLMotor";
    }
}