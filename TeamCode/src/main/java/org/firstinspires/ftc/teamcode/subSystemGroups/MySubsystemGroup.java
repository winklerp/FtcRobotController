package org.firstinspires.ftc.teamcode.subSystemGroups;

import org.firstinspires.ftc.teamcode.subSystems.Claw;
import org.firstinspires.ftc.teamcode.subSystems.Lift;

import dev.nextftc.core.subsystems.SubsystemGroup;

public class MySubsystemGroup extends SubsystemGroup {
    public static final MySubsystemGroup INSTANCE = new MySubsystemGroup();

    private MySubsystemGroup() {
        super(
                //Lift.INSTANCE,
                //Claw.INSTANCE
        );
    }
}
