package org.firstinspires.ftc.teamcode.pedroPathing;

import com.pedropathing.control.FilteredPIDFCoefficients;
import com.pedropathing.control.KalmanFilterParameters;
import com.pedropathing.control.PIDFCoefficients;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.ftc.FollowerBuilder;
import com.pedropathing.ftc.drivetrains.MecanumConstants;
import com.pedropathing.ftc.localization.constants.PinpointConstants;
import com.pedropathing.paths.PathConstraints;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Constants {

//  public static FollowerConstants followerConstants = new FollowerConstants();

    public static FollowerConstants followerConstants = new FollowerConstants()
            .mass(6.2)
            .forwardZeroPowerAcceleration(-32.551336016582326)
            .lateralZeroPowerAcceleration(-47.82231953548479)
            //.translationalPIDFCoefficients(new PIDFCoefficients(0.1, 0, 0.01, 0))
            //.secondaryTranslationalPIDFCoefficients(new PIDFCoefficients(0.1,0,0.01,0))
            //.headingPIDFCoefficients(new PIDFCoefficients(0.1, 0, 0.01, 0))
            //.secondaryHeadingPIDFCoefficients(new PIDFCoefficients(0.1,0,0.01,0))
            //.drivePIDFCoefficients(new FilteredPIDFCoefficients(0.1,0.0,0.01,0.6,0.0))
            //.secondaryDrivePIDFCoefficients(new FilteredPIDFCoefficients(0.1,0,0.01,0.6,0.01))
            //.centripetalScaling(0.005)
            .driveKalmanFilterModelCovariance(6)
            .driveKalmanFilterDataCovariance(1);

    public static PathConstraints pathConstraints = new PathConstraints(
            0.99, 100, 1, 1);

    public static MecanumConstants driveConstants = new MecanumConstants()
            .maxPower(1)
            .rightFrontMotorName("frontRightMotor")
            .rightRearMotorName("backRightMotor")
            .leftRearMotorName("backLeftMotor")
            .leftFrontMotorName("frontLeftMotor")
            .leftFrontMotorDirection(DcMotorSimple.Direction.REVERSE)
            .leftRearMotorDirection(DcMotorSimple.Direction.REVERSE)
            .rightFrontMotorDirection(DcMotorSimple.Direction.FORWARD)
            .rightRearMotorDirection(DcMotorSimple.Direction.FORWARD)
            .xVelocity(47.608142762672244)
            .yVelocity(41.910719984159684);

    public static PinpointConstants localizerConstants = new PinpointConstants()
            //.forwardPodY(-3.346)  // inches
            .forwardPodY(-85)
            //.strafePodX(-6.397)   // inches
            .strafePodX(-162.5)
            .distanceUnit(DistanceUnit.MM)
            .hardwareMapName("pinpoint")
            .encoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD)
            .forwardEncoderDirection(GoBildaPinpointDriver.EncoderDirection.FORWARD)
            .strafeEncoderDirection(GoBildaPinpointDriver.EncoderDirection.FORWARD);

//    public static Follower createFollower(HardwareMap hardwareMap) {
//        return new FollowerBuilder(followerConstants, hardwareMap)
//                .pathConstraints(pathConstraints)
//                .build();
//    }

    public static Follower createFollower(HardwareMap hardwareMap) {
        return new FollowerBuilder(followerConstants, hardwareMap)
                .pinpointLocalizer(localizerConstants)
                .pathConstraints(pathConstraints)
                .mecanumDrivetrain(driveConstants)
                //.useSecondaryTranslationalPIDF(true)
                //.useSecondaryHeadingPIDF(true)
                //.useSecondaryDrivePIDF(true)
                .build();
    }
}
