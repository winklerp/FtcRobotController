package org.firstinspires.ftc.teamcode.controlSystems;

import dev.nextftc.control.feedforward.FeedforwardElement;
import dev.nextftc.control.KineticState;

public class FullPowerFeedforward implements FeedforwardElement {
    @Override
    public double calculate(KineticState reference) {
        return 1;
    }

    @Override
    public void reset() {

    }
}
