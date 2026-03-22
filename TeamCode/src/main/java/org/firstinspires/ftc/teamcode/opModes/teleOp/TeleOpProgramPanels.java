package org.firstinspires.ftc.teamcode.opModes.teleOp;


import static java.lang.Math.PI;
import static java.lang.Math.sin;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.opModes.components.MyComponent;
import org.firstinspires.ftc.teamcode.opModes.subSystems.Claw;
import org.firstinspires.ftc.teamcode.opModes.subSystems.Lift;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;
import dev.nextftc.hardware.driving.MecanumDriverControlled;
import dev.nextftc.hardware.impl.Direction;
import dev.nextftc.hardware.impl.IMUEx;
import dev.nextftc.hardware.impl.MotorEx;

import com.bylazar.telemetry.JoinedTelemetry;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.lights.Headlight;
import com.bylazar.lights.PanelsLights;
import com.bylazar.lights.RGBIndicator;
import com.bylazar.lights.LightsManager;

@TeleOp(name = "NextFTC TeleOp Java Panels", group = "Samples")
public class TeleOpProgramPanels extends NextFTCOpMode {
    public TeleOpProgramPanels() {
        addComponents(
                new SubsystemComponent(Lift.INSTANCE, Claw.INSTANCE),
                BulkReadComponent.INSTANCE,
                BindingsComponent.INSTANCE,
                new MyComponent()
                //new SubsystemComponent(MySubsystemGroup.INSTANCE)
        );
    }

    // change the names and directions to suit your robot
    private final MotorEx frontLeftMotor = new MotorEx("front_left").brakeMode().reversed();
    private final MotorEx frontRightMotor = new MotorEx("front_right").brakeMode();
    private final MotorEx backLeftMotor = new MotorEx("back_left").brakeMode().reversed();
    private final MotorEx backRightMotor = new MotorEx("back_right").brakeMode();
    private IMUEx imu = new IMUEx("imu", Direction.UP, Direction.FORWARD).zeroed();

    private final JoinedTelemetry joinedTelemetry = new JoinedTelemetry(PanelsTelemetry.INSTANCE.getFtcTelemetry(), telemetry);
    private LightsManager lightManager = PanelsLights.INSTANCE.getLights();
    private ElapsedTime timer = new ElapsedTime();
    private RGBIndicator rgb;
    private RGBIndicator rgb2;
    private Headlight head;


    @Override public void onInit() {
        rgb = new RGBIndicator("Colored Light");
        rgb.withProvider(() -> (sin(timer.seconds() * PI) + 1.0) / 2.0);

        head = new Headlight("Simple Light");

        rgb2 = new RGBIndicator("Colored Light #2");

        rgb2.update(RGBIndicator.Companion.getBLUE());

        lightManager.initLights(
                rgb,
                head,
                rgb2
        );
        timer.reset();
    }

    @Override public void onWaitForStart() { }
    @Override
    public void onStartButtonPressed() {
        Command driverControlled = new MecanumDriverControlled(
                frontLeftMotor,
                frontRightMotor,
                backLeftMotor,
                backRightMotor,
                Gamepads.gamepad1().leftStickY().negate(),
                Gamepads.gamepad1().leftStickX(),
                Gamepads.gamepad1().rightStickX()
                //new FieldCentric(imu)
        );
        driverControlled.schedule();

        Gamepads.gamepad2().dpadUp()
                .whenBecomesTrue(Lift.INSTANCE.toHigh)
                .whenBecomesFalse(Claw.INSTANCE.open);

        Gamepads.gamepad2().rightTrigger().greaterThan(0.2)
                .whenBecomesTrue(
                        Claw.INSTANCE.close.then(Lift.INSTANCE.toHigh)
                );

        Gamepads.gamepad2().leftBumper().whenBecomesTrue(
                Claw.INSTANCE.open.and(Lift.INSTANCE.toLow)
        );

//        Command myLambdaCommand = new LambdaCommand()
//                .setStart(() -> {
//                    // Runs on start
//                })
//                .setUpdate(() -> {
//                    // Runs on update
//                })
//                .setStop(interrupted -> {
//                    // Runs on stop
//                })
//                .setIsDone(() -> true) // Returns if the command has finished
//                .requires(/* subsystems the command implements */)
//                .setInterruptible(true)
//                .named("My Command"); // sets the name of the command; optional

        //CommandManager.INSTANCE.scheduleCommand(myLambdaCommand);
        //myLambdaCommand.schedule();

        //Command myCommand = new MyCommand(); // Or a LambdaCommand
        //CommandManager.INSTANCE.scheduleCommand(myCommand);
        //myCommand.schedule();

        //PositionsCommands.runToPosition(new MyControlSystem().controlSystem, 10).schedule();
    }
    @Override public void onUpdate() {
        joinedTelemetry.addData("Key", "Value");
        joinedTelemetry.addData("Key2", 50);

        joinedTelemetry.update();

        double t = timer.seconds();

        boolean headOn = (((int)(t / 1.5)) % 2) == 0;

        rgb.update(0.0);
        head.update(headOn);

        lightManager.update();

        joinedTelemetry.addData("Time (s)", "%.2f", t);
        lightManager.addToTelemetry(joinedTelemetry);
        joinedTelemetry.update();

        lightManager.getLightsState().forEach( (it) -> {
            // panelTelemetry.addData("","${it.id} ${it.type} ${it.value}");
            joinedTelemetry.addData("","${it.id} ${it.type} ${it.value}");
        });

        joinedTelemetry.update();
    }

    @Override public void onStop() { }
}