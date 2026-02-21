package org.firstinspires.ftc.teamcode.helper;

import com.qualcomm.robotcore.hardware.PwmControl;
import com.qualcomm.robotcore.hardware.Servo;

public class Helper {
    int counter = 0;
    boolean setPwm(Servo servo, double usPwm)
    {
        if (usPwm >= 500.0 && usPwm <= 2500.0) {
            if (PwmControl.class.isInstance(servo)) {
                PwmControl pwm = (PwmControl) servo;
                PwmControl.PwmRange range = pwm.getPwmRange();
                pwm.setPwmRange(new PwmControl.PwmRange(usPwm, usPwm, range.usFrame));

                //pwm.setPwmRange(new PwmControl.PwmRange(500, usPwm, range.usFrame));

                //servo.setPosition(0.0);  // Not 100% effective
                //servo.setPosition(1.0);  // Workaround

                servo.setPosition(++counter % 2);  // Workaround with only one invocation of setPosition()

                return true;
            }
        }
        return false;
    }
}
