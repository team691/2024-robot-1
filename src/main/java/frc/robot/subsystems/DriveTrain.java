package frc.robot.subsystems;

// BASIC MOTORS AND ENCODERS
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
//import edu.wpi.first.wpilibj.Joystick;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SerialPort;
//POTENTIAL COMMENTING OUT:
import com.revrobotics.RelativeEncoder;

// DIFFERENTIAL DRIVE
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.CommandBase;
// COMMANDS
import edu.wpi.first.wpilibj2.command.SubsystemBase;
// CONSTANTS
import frc.robot.Constants.DriveConstants;

// SMARTDASHBOARD
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard; 

//import frc.robot.Constants.AutoConstants;

public class DriveTrain extends SubsystemBase {

  private final RelativeEncoder m_frontLeftEncoder;
  private final RelativeEncoder m_rearLeftEncoder;
  private final RelativeEncoder m_frontRightEncoder;
  private final RelativeEncoder m_rearRightEncoder;


//Motor control group left
  private final CANSparkMax m_frontLeftMotor = new CANSparkMax(DriveConstants.kFrontLeftMotorID, MotorType.kBrushless);
  private final CANSparkMax m_rearLeftMotor = new CANSparkMax(DriveConstants.kRearLeftMotorID, MotorType.kBrushless);
  MotorControllerGroup m_left = new MotorControllerGroup(m_frontLeftMotor, m_rearLeftMotor);

//Motor control group right
  private final CANSparkMax m_frontRightMotor = new CANSparkMax(DriveConstants.kFrontRightMotorID, MotorType.kBrushless);
  private final CANSparkMax m_rearRightMotor = new CANSparkMax(DriveConstants.kRearRightMotorID, MotorType.kBrushless);
  MotorControllerGroup m_right = new MotorControllerGroup(m_frontRightMotor, m_rearRightMotor);

   // The robot's drive
   DifferentialDrive m_drive = new DifferentialDrive(m_left, m_right);
  
   private final AHRS navx = new AHRS(SerialPort.Port.kMXP);

   double angle;

   public DriveTrain() {
      m_frontLeftMotor.setSmartCurrentLimit(40, 38);
      m_frontRightMotor.setSmartCurrentLimit(40, 38);
      m_rearLeftMotor.setSmartCurrentLimit(40, 38);
      m_rearRightMotor.setSmartCurrentLimit(40, 38);
      // We need to invert one side of the drivetrain so that positive voltages
      // result in both sides moving forward. 
      m_right.setInverted(true);
      navx.reset();
      navx.calibrate();
      m_frontLeftEncoder = m_frontLeftMotor.getEncoder();
      m_frontRightEncoder = m_frontRightMotor.getEncoder();
      m_rearLeftEncoder = m_rearLeftMotor.getEncoder();
      m_rearRightEncoder = m_rearRightMotor.getEncoder();
      // Sets the distance per pulse for the encoders
      m_frontLeftEncoder.setPositionConversionFactor(DriveConstants.kEncoderDistancePerPulse);
      m_frontRightEncoder.setPositionConversionFactor(DriveConstants.kEncoderDistancePerPulse);
      m_rearLeftEncoder.setPositionConversionFactor(DriveConstants.kEncoderDistancePerPulse);
      m_rearRightEncoder.setPositionConversionFactor(DriveConstants.kEncoderDistancePerPulse);
   }
   
   /* DRIVE */
   
   // Controller-based drive
   public void drive (double xSpeed, double zRotation) {
      m_drive.arcadeDrive(-xSpeed, zRotation);
   }

   public void stopDrive() {
      m_drive.stopMotor();
   }

   /* SLOWER MODE OPTION THING */
   public void setMaxOutput(double maxOutput) {
      m_drive.setMaxOutput(maxOutput);
   }

   /* ENCODER COMMANDS */
   
   // Resets the drive encoders to currently read a position of 0.
   public void resetEncoders() {
      m_frontLeftEncoder.setPosition(0);
      m_frontRightEncoder.setPosition(0);
      m_rearLeftEncoder.setPosition(0);
      m_rearRightEncoder.setPosition(0);
   }

   public double getAverageLeftEncoderDistance() {
      return (m_frontLeftEncoder.getPosition() + m_rearLeftEncoder.getPosition()) / 2;
   }

   public double getAverageRightEncoderDistance() {
      return (m_frontRightEncoder.getPosition() + m_rearRightEncoder.getPosition()) / 2;
   }

   public double getAverageEncoderDistance() {
      return (getAverageLeftEncoderDistance() + getAverageRightEncoderDistance()) / 2;
   }

   //TEST

   public CommandBase printFrontLeftEncoderDistance() {
      return runOnce(
         () -> {
            System.out.println("Front Left Encoder:" + (m_frontLeftEncoder.getPosition()) / 2);
         });
      }
      
   public CommandBase printRearLeftEncoderDistance() {
      return runOnce(
         () -> {
            System.out.println("Rear Left Encoder:" + ((m_rearLeftEncoder.getPosition()) / 2));
         });
      
   }

   public CommandBase printFrontRightEncoderDistance() {
      return runOnce(
         () -> {
            System.out.println("Front Right Encoder:" + ((m_frontRightEncoder.getPosition()) / 2));
         });
      
   }

   public CommandBase printRearRightEncoderDistance() {
      return runOnce(
         () -> {
            System.out.println("Rear Right Encoder:" + ((m_rearRightEncoder.getPosition()) / 2));
         });
   }
}
