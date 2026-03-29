package org.firstinspires.ftc.teamcode.opModes.teleOp;

import static dev.nextftc.extensions.pedro.PedroComponent.follower;

import com.bylazar.configurables.annotations.IgnoreConfigurable;
import com.bylazar.telemetry.JoinedTelemetry;
import com.bylazar.telemetry.PanelsTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.extensions.pedro.PedroComponent;
import dev.nextftc.extensions.pedro.PedroDriverControlled;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;
import dev.nextftc.hardware.driving.DriverControlledCommand;

@TeleOp(name = "NextFTC PedroPathing TeleOp DriverControlled", group = "Samples")
public class PedroPathingTeleopProgram extends NextFTCOpMode {
    @IgnoreConfigurable
    private final JoinedTelemetry telemetryM = new JoinedTelemetry(
            PanelsTelemetry.INSTANCE.getFtcTelemetry(), telemetry);

    public PedroPathingTeleopProgram() {
        addComponents(
                new PedroComponent(Constants::createFollower),
                BulkReadComponent.INSTANCE,
                BindingsComponent.INSTANCE
        );
    }

    @Override public void onInit() {

    }

    @Override
    public void onStartButtonPressed() {
        DriverControlledCommand driverControlled = new PedroDriverControlled(
                Gamepads.gamepad1().leftStickY().negate(),
                Gamepads.gamepad1().leftStickX().negate(),
                Gamepads.gamepad1().rightStickX().negate(),
                true
        );
        driverControlled.schedule();
    }

    @Override public void onUpdate() {
        // These loop the movements of the robot, these must be called continuously in order to work
        telemetryM.addData("x", follower().getPose().getX());
        telemetryM.addData("y", follower().getPose().getY());
        telemetryM.addData("heading", follower().getPose().getHeading());
        telemetryM.update();
    }
}

