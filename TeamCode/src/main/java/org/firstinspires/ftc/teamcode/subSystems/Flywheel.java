package org.firstinspires.ftc.teamcode.subSystems;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.MotorEx;

import dev.nextftc.control2.feedback.PIDController;
import dev.nextftc.control2.feedforward.SimpleFFCoefficients;
import dev.nextftc.control2.feedforward.SimpleFeedforward;

public class Flywheel implements Subsystem {
    public static final Flywheel INSTANCE = new Flywheel();
    private Flywheel() { }

    private final MotorEx motor = new MotorEx("flywheel_motor");

    private final PIDController pid = new PIDController(0.005, 0, 0);
    private final SimpleFeedforward ff = new SimpleFeedforward(
                            new SimpleFFCoefficients(0.02, 0.01, 0.02));

    private double targetVelocity = 0;

    public final Command off = instant("FlywheelOff", () -> targetVelocity = 0);
    public final Command on = instant("FlywheelOn", () -> targetVelocity = 6000);

    @Override
    public void periodic() {
        double currentVelocity = motor.getVelocity();
        motor.setPower(pid.calculate(targetVelocity - currentVelocity) +
                ff.calculate(targetVelocity, 0.0));
    }
}
