// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  /* DRIVETRAIN */
  /***********************************************************************************************************************************************/
  // INSTANTIATE LEFT MOTORS AND LEFT DRIVE
  public  SpeedController leftBackDrive = new PWMVictorSPX(0);
  public SpeedController leftFrontDrive = new PWMVictorSPX(0);
  public SpeedControllerGroup leftDrive = new SpeedControllerGroup(leftBackDrive, leftFrontDrive);

  // INSTANTIATE RIGHT MOTORS AND RIGHT DRIVE
  public SpeedController rightBackDrive = new PWMVictorSPX(0);
  public SpeedController rightFrontDrive = new PWMVictorSPX(0);
  public SpeedControllerGroup rightDrive = new SpeedControllerGroup(rightBackDrive, rightFrontDrive);
  /***********************************************************************************************************************************************/
  
  /* INTAKE, STORAGE, AND FLYWHEEL MOTORS */
  /***********************************************************************************************************************************************/
  // INSTANTIATE INTAKE MOTOR
  public PWMVictorSPX intakeMotor = new PWMVictorSPX(0);

  // INSTANTIATE STORAGE MOTOR
  public PWMVictorSPX storageMotor = new PWMVictorSPX(0);

  // INSTANTIATE FLYWHEEL MOTORS AND FLYWHEEL
  public SpeedController leftFlywheelMotor = new PWMVictorSPX(0);
  public SpeedController rightFlywheelMotor = new PWMVictorSPX(0);
  public SpeedControllerGroup flywheelDrive = new SpeedControllerGroup(rightBackDrive, rightFrontDrive);
  /***********************************************************************************************************************************************/ 

  /* PNEUMANTICS AND LIMELIGHT */
  /***********************************************************************************************************************************************/
  // INSTANTIATE COMPRESSOR
  public Compressor compressor = new Compressor();

  // INSTANTIATE INTAKE SOLENOID
  public DoubleSolenoid intakeSolenoid = new DoubleSolenoid(1, 0);

  // INSTANTIATE STORAGE SOLENOID
  public DoubleSolenoid storageSolenoid = new DoubleSolenoid(2, 3);

  // INSTANTIATE LIMELIGHT TABLE
  public NetworkTable tableLimelight = NetworkTableInstance.getDefault().getTable("limelight");

  /***********************************************************************************************************************************************/

  /* CONTROLLER */
  /***********************************************************************************************************************************************/
  // INSTANTIATE XBOX CONTROLLER
  public XboxController controller = new XboxController(0);

  // INSTANTIATE JOYSTICK AXES VARIABLES
  public static int leftStickY = 1;
  public static int rightStickY = 5;
  public static int leftTrigger = 2;
  public static int rightTrigger = 3;

  // INSTANTIATE JOYSTICK BUTTONS
  public JoystickButton aButton = new JoystickButton(controller, 1);
  public JoystickButton bButton = new JoystickButton(controller, 2);
  public JoystickButton xButton = new JoystickButton(controller, 3);
  public JoystickButton yButton = new JoystickButton(controller, 4);
  public JoystickButton leftBumper = new JoystickButton(controller, 5);
  public JoystickButton rightBumper = new JoystickButton(controller, 6);
  /***********************************************************************************************************************************************/
  
  /* CHOOSER */
  /***********************************************************************************************************************************************/
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  /***********************************************************************************************************************************************/

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {}

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {
  // RUN CHOOSER
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);

  }

  /** This function is called periodically during autonomous. */
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

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {}

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {}

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {}

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}
}
