/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;

/// CTRE Talon import stuffs
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.RemoteSensorSource;
import com.ctre.phoenix.motorcontrol.SensorTerm;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FollowerType;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
/// End CTRE Talon imports



/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  
  // Controller JoySticks
  Joystick ControllerJoy = new Joystick(0);

  //Delare main Drive Motors
  private WPI_TalonSRX SRX_Master_R = new WPI_TalonSRX(MotorConnectionsConfig.DriveMasterRight);
  private WPI_TalonSRX SRX_Slave_R = new WPI_TalonSRX(MotorConnectionsConfig.DriveSlaveRight);

  private WPI_TalonSRX SRX_Master_L = new WPI_TalonSRX(MotorConnectionsConfig.DriveMasterLeft);
  private WPI_TalonSRX SRX_Slave_L = new WPI_TalonSRX(MotorConnectionsConfig.DriveSlaveLeft);

  DifferentialDrive DiffDrive = new DifferentialDrive(SRX_Master_L, SRX_Master_R);
  // End of Drive Decleration

  // Declare Pnuematic systems and Vars
 private HatchArmManipulator armManipulator = new HatchArmManipulator(MotorConnectionsConfig.ArmMotorPWMPort, PnuematicConnectionConstants.Arm_Solenoid_Forward, PnuematicConnectionConstants.Arm_Solenoid_Reverse, 0, 1);
 private Compressor  PM_Compressor = new Compressor();
 
 /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

    //Set slave Drive Controllers
    SRX_Slave_L.follow(SRX_Master_L);
    SRX_Slave_R.follow(SRX_Master_R);


    //Set Neutral Mode on Slaves and Masters
    SRX_Master_L.setNeutralMode(NeutralMode.Brake);
    SRX_Master_R.setNeutralMode(NeutralMode.Brake);

    SRX_Slave_R.setNeutralMode(NeutralMode.Brake);
    SRX_Slave_L.setNeutralMode(NeutralMode.Brake);

    //Enable Air Compressor
    PM_Compressor.setClosedLoopControl(true);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    DiffDrive.arcadeDrive(ControllerJoy.getY(),  ControllerJoy.getX());  
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
