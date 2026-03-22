package org.firstinspires.ftc.teamcode.opModes.subSystemGroups;

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
