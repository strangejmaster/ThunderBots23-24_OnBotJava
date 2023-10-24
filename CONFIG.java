// This file contains all configuration stuff
class CONFIG {
    class CONTROLLER {
        final double DEADZONE = 0.05;
    }
    class DRIVETRAIN {
        // A value between 0 and 1 to which motor power will be scaled
        final double FOWARD_SPEED = 1.00;
        final double TURN_SPEED = 1.00;

        // String Values which correspond to the DriverHub's hardware map
        final String RIGHT = "RightMotor";
        final String LEFT = "LeftMotor";
    }
}