package org.firstinspires.ftc.teamcode.opModes.controlSystems;

import dev.nextftc.control.ControlSystem;
import dev.nextftc.control.feedback.PIDCoefficients;
import dev.nextftc.control.feedforward.ArmFeedforward;
import dev.nextftc.control.feedforward.GravityFeedforwardParameters;

public class MyControlSystem {
    public static volatile PIDCoefficients coefficients = new PIDCoefficients(1, 0,0);
    public ControlSystem controlSystem = ControlSystem.builder()
                //.feedback(/* feedback element */ new PIDElement(FeedbackType.POSITION, 1, 0, 0))
                .posPid(coefficients)
                .feedforward(/* feedforward element */ new ArmFeedforward(new GravityFeedforwardParameters(1, 2, 3,4)))
                //.feedforward(new FullPowerFeedforward())
                .posFilter(filter -> filter.custom(/* position filter */ new MyFilter() ))
                .velFilter(filter -> filter.custom(/* velocity filter */ new MyFilter() ))
                .accelFilter(filter -> filter.custom(/* acceleration filter */ new MyFilter() ))
                //.interpolator(/* interpolator element */ new ConstantInterpolator(new KineticState(1)))
                .interpolator(/* interpolator element */ new MyInterpolator())
                .build();
}
