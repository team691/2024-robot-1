// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

public class DriveDistanceCommand extends CommandBase {
  private final DriveTrain m_drive;
  private final double m_distance;
  private final double m_speed;
  private final double m_rotation;

  /**
   * Creates a new DriveDistance.
   *
   * @param inches The number of inches the robot will drive
   * @param speed The speed at which the robot will drive
   * @param drive The drive subsystem on which this command will run
   */
  public DriveDistanceCommand(double inches, double speed, double zRotation, DriveTrain drive) {
    m_distance = inches;
    m_speed = speed;
    m_drive = drive;
    m_rotation = zRotation;
    addRequirements(m_drive);
  }

  @Override
  public void initialize() {
    m_drive.resetEncoders();
    //m_drive.drive(m_speed, m_rotation);
    System.out.println("DRIVE DISTANCE");
  }

  @Override
  public void execute() {
    m_drive.drive(m_speed, m_rotation);
    System.out.println("EXECUTE");
  }

  @Override
  public void end(boolean interrupted) {
    m_drive.stopDrive();
    System.out.println("FINISHED");
  }

  @Override
  public boolean isFinished() {
    return Math.abs(m_drive.getAverageEncoderDistance()) >= m_distance;
  }
}