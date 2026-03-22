package org.firstinspires.ftc.teamcode.pedroPathing;

import static org.firstinspires.ftc.teamcode.pedroPathing.Tuning.follower;

import static java.lang.Math.PI;
import static java.lang.Math.abs;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.units.Angle;

public class TurnTo extends Command {
    private final Angle angle;

    public TurnTo(Angle angle) {
        this.angle = angle;
        named("TurnTo(" + angle + ")");
    }

    @Override
    public boolean isDone() {
        return !follower.isTurning();
    }

    @Override
    public void start() {
        double current = follower.getHeading();
        double target = angle.inRad;

        // Compute signed difference manually
        double diff = target - current;
        // Normalize to [-PI, PI]
        while (diff > PI) diff -= 2 * PI;
        while (diff < -PI) diff += 2 * PI;

        follower.turn(abs(diff), diff > 0);
    }
}
