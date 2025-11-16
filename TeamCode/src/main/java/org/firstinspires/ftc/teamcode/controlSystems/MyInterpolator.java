package org.firstinspires.ftc.teamcode.controlSystems;

import androidx.annotation.NonNull;

import dev.nextftc.control.KineticState;
import dev.nextftc.control.interpolators.InterpolatorElement;

public class MyInterpolator implements InterpolatorElement {
    @NonNull
    @Override
    public KineticState getGoal() {
        return null;
    }

    @Override
    public void setGoal(@NonNull KineticState kineticState) {

    }

    @NonNull
    @Override
    public KineticState getCurrentReference() {
        return null;
    }

    @Override
    public void reset() {

    }
}
