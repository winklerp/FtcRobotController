package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.robot.Robot;

import org.firstinspires.ftc.teamcode.commands.MyCommand;
import org.firstinspires.ftc.teamcode.commands.PositionsCommands;
import org.firstinspires.ftc.teamcode.components.MyComponent;
import org.firstinspires.ftc.teamcode.controlSystems.MyControlSystem;
import org.firstinspires.ftc.teamcode.subSystemGroups.MySubsystemGroup;
import org.firstinspires.ftc.teamcode.subSystems.Claw;
import org.firstinspires.ftc.teamcode.subSystems.Lift;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.CommandManager;
import dev.nextftc.core.commands.utility.LambdaCommand;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;
import dev.nextftc.hardware.driving.FieldCentric;
import dev.nextftc.hardware.driving.HolonomicMode;
import dev.nextftc.hardware.driving.MecanumDriverControlled;
import dev.nextftc.hardware.driving.RobotCentric;
import dev.nextftc.hardware.impl.Direction;
import dev.nextftc.hardware.impl.IMUEx;
import dev.nextftc.hardware.impl.MotorEx;

@TeleOp(name = "NextFTC TeleOp Program Java", group = "Bot")
public class TeleOpProgram extends NextFTCOpMode {
    public TeleOpProgram() {
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

    @Override public void onInit() { }
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
    @Override public void onUpdate() { }
    @Override public void onStop() { }

}