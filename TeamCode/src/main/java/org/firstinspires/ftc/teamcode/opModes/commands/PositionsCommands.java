package org.firstinspires.ftc.teamcode.opModes.commands;

import dev.nextftc.control.ControlSystem;
import dev.nextftc.control.KineticState;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.utility.LambdaCommand;

public class PositionsCommands {
    public static Command runToPosition(ControlSystem system, double position, KineticState tolerance) {
        return new LambdaCommand("RunToPosition(" + position + ")")
                .setStart(() -> system.setGoal(new KineticState(position)))
                .setIsDone(() -> system.isWithinTolerance(tolerance))
                .requires(system);
    }
    public static Command runToPosition(ControlSystem system, double position) {
        return runToPosition(system, position, new KineticState(10.0));
    }
}
