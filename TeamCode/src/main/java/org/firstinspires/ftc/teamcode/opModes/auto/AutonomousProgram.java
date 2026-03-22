package org.firstinspires.ftc.teamcode.opModes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.opModes.subSystems.Claw;
import org.firstinspires.ftc.teamcode.opModes.subSystems.Lift;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.delays.Delay;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.components.BulkReadComponent;
import dev.nextftc.ftc.NextFTCOpMode;

@Autonomous(name = "NextFTC Autonomous Java", group = "Bot")
public class AutonomousProgram extends NextFTCOpMode {
    public AutonomousProgram() {
        addComponents(
                new SubsystemComponent(Lift.INSTANCE, Claw.INSTANCE),
                BulkReadComponent.INSTANCE,
                BindingsComponent.INSTANCE
        );
    }

    private Command autonomousRoutine() {
        return new SequentialGroup(
                Lift.INSTANCE.toHigh,
                new ParallelGroup(
                        Lift.INSTANCE.toMiddle,
                        Claw.INSTANCE.close
                ),
                new Delay(0.5),
                new ParallelGroup(
                        Claw.INSTANCE.open,
                        Lift.INSTANCE.toLow
                )
        );
    }

    @Override
    public void onStartButtonPressed() {
        autonomousRoutine().schedule();
    }
}
