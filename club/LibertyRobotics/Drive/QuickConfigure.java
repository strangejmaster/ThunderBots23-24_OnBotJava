package club.LibertyRobotics.Drive;

import com.qualcomm.robotcore.hardware.*;

public final class QuickConfigure {
    public static DcMotor motorConfig(String hardwareMap, boolean isFoward) {
        DcMotor motor = null;

        motor = hardwareMap.get(DcMotor.class, hardwareMap);
        motor.setDirection( (isFoward ? DcMotorSimple.Direction.FORWARD : DcMotorSimple.Direction.FORWARD) );
        
        return motor;
    }
}