package org.firstinspires.ftc.teamcode.opModes.teleOp;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AngularVelocity;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.teamcode.opModes.components.MyComponent;
import org.firstinspires.ftc.teamcode.opModes.subSystems.Claw;
import org.firstinspires.ftc.teamcode.opModes.subSystems.Lift;

import dev.nextftc.control.KineticState;
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
import dev.nextftc.extensions.fateweaver.FateComponent;

import dev.nextftc.hardware.impl.VoltageCompensatingMotor;
import gay.zharel.fateweaver.log.LogChannel;

@TeleOp(name = "NextFTC TeleOp Java", group = "Samples")
public class TeleOpProgram extends NextFTCOpMode {
    public TeleOpProgram() {
        addComponents(
                new SubsystemComponent(Lift.INSTANCE, Claw.INSTANCE),
                BulkReadComponent.INSTANCE,
                BindingsComponent.INSTANCE,
                FateComponent.INSTANCE,
                new MyComponent()
                //new SubsystemComponent(MySubsystemGroup.INSTANCE)
        );
    }

    // change the names and directions to suit your robot
    private final MotorEx frontLeftMotor = new MotorEx("front_left").brakeMode().reversed();
    private final MotorEx frontRightMotor = new MotorEx("front_right").brakeMode();
    private final MotorEx backLeftMotor = new MotorEx("back_left").brakeMode().reversed();
    private final MotorEx backRightMotor = new MotorEx("back_right").brakeMode();
    //private final VoltageCompensatingMotor outtakeL = new VoltageCompensatingMotor(backLeftMotor, 0.01, 12);
    //private final VoltageCompensatingMotor outtakeR = new VoltageCompensatingMotor(backRightMotor, 0.01, 12);
    private IMUEx imu = new IMUEx("imu", Direction.UP, Direction.FORWARD).zeroed();

    private GoBildaPinpointDriver odo; // Declare OpMode member for the Odometry Computer

    @Override public void onInit() {
        odo = hardwareMap.get(GoBildaPinpointDriver.class, "pinpoint");

        /*
        Before running the robot, recalibrate the IMU. This needs to happen when the robot is stationary
        The IMU will automatically calibrate when first powered on, but recalibrating before running
        the robot is a good idea to ensure that the calibration is "good".
        resetPosAndIMU will reset the position to 0,0,0 and also recalibrate the IMU.
        This is recommended before you run your autonomous, as a bad initial calibration can cause
        an incorrect starting value for x, y, and heading.

        On your Pinpoint, most commonly that amount of drift (especially intermittently) is caused by
        a bad zero offset calibration. Having a bad zero offset calibration happens any time the Pinpoint is asked
        to calibrate while the device is moving. It automatically calibrates when it first receives power, and any
        time you call resetPosAndIMU() or recalibrateIMU(). I'd recommend making sure that you're calling one of
        those functions when the robot is perfectly still before the match starts, and make sure you aren't
        calling them at any other time during your OpModes.
         */
        //odo.recalibrateIMU();
        odo.resetPosAndIMU();

        telemetry.addData("Status", "Initialized");
        telemetry.addData("X offset", odo.getXOffset(DistanceUnit.MM));
        telemetry.addData("Y offset", odo.getYOffset(DistanceUnit.MM));
        telemetry.addData("Device Version Number:", odo.getDeviceVersion());
        telemetry.addData("Heading Scalar", odo.getYawScalar());
        telemetry.update();
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

        LogChannel<KineticState> stateChannel = FateComponent.createChannel("LiftState",
                                                KineticState.class);

        FateComponent.registerPublisher(stateChannel, Lift.INSTANCE::getState);
        //FateComponent.registerPublisher("LiftState", KineticState.class, Lift.INSTANCE::getState);

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
        FateComponent.write("LiftState", Lift.INSTANCE.getState());

        // Retrieve Rotational Angles and Velocities
        YawPitchRollAngles orientation = imu.getImu().getRobotYawPitchRollAngles();
        AngularVelocity angularVelocity = imu.getImu().getRobotAngularVelocity(AngleUnit.DEGREES);

        telemetry.addData("Yaw (Z)", "%.2f Deg. (Heading)", orientation.getYaw(AngleUnit.DEGREES));
        telemetry.addData("Pitch (X)", "%.2f Deg.", orientation.getPitch(AngleUnit.DEGREES));
        telemetry.addData("Roll (Y)", "%.2f Deg.\n", orientation.getRoll(AngleUnit.DEGREES));
        telemetry.addData("Yaw (Z) velocity", "%.2f Deg/Sec", angularVelocity.zRotationRate);
        telemetry.addData("Pitch (X) velocity", "%.2f Deg/Sec", angularVelocity.xRotationRate);
        telemetry.addData("Roll (Y) velocity", "%.2f Deg/Sec", angularVelocity.yRotationRate);
        telemetry.update();
    }

    @Override public void onStop() {

    }
}