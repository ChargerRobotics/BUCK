package frc.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class Robot extends TimedRobot 
{
  /* DRIVETRAIN */
  /***********************************************************************************************************************************************/

  // INSTANTIATE LEFT MOTORS AND LEFT DRIVE
  public SpeedController leftBackDrive = new PWMVictorSPX(2);
  public SpeedController leftFrontDrive = new PWMVictorSPX(1);
  public SpeedControllerGroup leftDrive = new SpeedControllerGroup(leftBackDrive, leftFrontDrive);

  // INSTANTIATE RIGHT MOTORS AND RIGHT DRIVE
  public SpeedController rightBackDrive = new PWMVictorSPX(4);
  public SpeedController rightFrontDrive = new PWMVictorSPX(3);
  public SpeedControllerGroup rightDrive = new SpeedControllerGroup(rightBackDrive, rightFrontDrive);

  /***********************************************************************************************************************************************/
  
  /* FLYWHEEL AND STORAGE */
  /***********************************************************************************************************************************************/

  // INSTANTIATE FLYWHEEL MOTORS AND FLYWHEEL
  public SpeedController leftFlywheelMotor = new PWMVictorSPX(5);
  public SpeedController rightFlywheelMotor = new PWMVictorSPX(6);

  // INSTANTIATE STORAGE MOTORS
  public SpeedController storageMotor = new PWMVictorSPX(7);

  /***********************************************************************************************************************************************/ 

  /* CONTROLLER AND CAMERA */
  /***********************************************************************************************************************************************/

  // INSTANTIATE JOYSTICKS
  public Joystick leftJoystick = new Joystick(0);
  public Joystick rightJoystick = new Joystick(1);

  // INSTANTIATE RIGHT JOYSTICK AXES VARIABLES
  public static int rightStickX = 0;
  public static int rightStickY = 1;

  // INSTANTIATE LEFT JOYSTICK AXES VARIABLES
   public static int leftStickX = 0;
   public static int leftStickY = 1;
   public static int leftSlider = 2;

  // INSTANTIATE JOYSTICK BUTTONS
  public JoystickButton r1Button = new JoystickButton(rightJoystick, 1);
  public JoystickButton r2Button = new JoystickButton(rightJoystick, 2);
  public JoystickButton r3Button = new JoystickButton(rightJoystick, 3);
  public JoystickButton r4Button = new JoystickButton(rightJoystick, 4);
  public JoystickButton r5Button = new JoystickButton(rightJoystick, 5);


  /***********************************************************************************************************************************************/
  
  /** This function is called once whenever the robot starts a sequence. */
  @Override
  public void robotInit() 
  {
    leftFlywheelMotor.setVoltage(0);
    rightFlywheelMotor.setVoltage(0);

    UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
    camera.setFPS(30);
    camera.setResolution(640, 480);
  }

  /** This function is called periodically when the robot is in a sequence. */
  @Override
  public void robotPeriodic() {}

  /** This function is called once when the robot starts auto. */
  @Override
  public void autonomousInit() {}

  /** This function is called periodically when the robot is in auto. */
  @Override
  public void autonomousPeriodic(){}

  /** This function is called once when the robot starts teleop. */
  @Override
  public void teleopInit() 
  {
    leftFlywheelMotor.setVoltage(0);
    rightFlywheelMotor.setVoltage(0);
    storageMotor.setVoltage(0);
  }

  /** This function is called periodically when the robot is in teleop. */
  @Override
  public void teleopPeriodic() 
  {
    /* CONTROLLER */
    /***********************************************************************************************************************************************/
    
    // MAPPING BUTTONS AND JOYSTICK INPUTS TO VARIABLES
    double leftY = leftJoystick.getRawAxis(leftStickY);
    double rightY = rightJoystick.getRawAxis(rightStickY);

    boolean right1 = r1Button.get();
    boolean right2 = r2Button.get();
    boolean right3 = r3Button.get();
    boolean right4 = r4Button.get();
    boolean right5 = r5Button.get();

    /***********************************************************************************************************************************************/
    
    /* DRIVETRAIN */
    /***********************************************************************************************************************************************/
    
    // SCALE DOWN JOYSTICK AXIS VALUES
    leftY = leftY*.8;
    rightY = rightY*.8;

    // READS BUTTON VALUES TO ADJUST SPEED OF DRIVETRAIN
    if (right3 == true) 
    {
      leftY = leftY * .95;
      rightY = rightY * .95;
    } 
    else 
    {
      leftY = leftY * .75;
      rightY = rightY * .75;
    }

    // REMOVES CONTROLLER DRIFT
    if (leftY < .1) 
    {
      if (leftY > -.1) 
      {
        leftY = 0;
      }
    }
    if (rightY < .1) 
    {
      if (rightY > -.1) 
      {
        rightY = 0;
      }
    }

    // SETS DRIVE SIDES TO AXES
    leftDrive.set(-leftY);
    rightDrive.set(rightY);

    /***********************************************************************************************************************************************/
    

    /* FLYWHEEL w/STORAGE */
    /***********************************************************************************************************************************************/
    
    // IF RIGHT TRIGGER PRESSED, ACTIVATE FLYWHEEL MOTORS
    if(right1 == true) 
    {
      leftFlywheelMotor.setVoltage(-12);
      rightFlywheelMotor.setVoltage(-11);
    }
    else
    {
      leftFlywheelMotor.set(0);
      rightFlywheelMotor.set(0);
    }

    // IF BUTTON PRESSED, ACTIVATE STORAGE MOTOR
    if(right2 == true) 
    {
      storageMotor.setVoltage(10);
    } else 
    {
      storageMotor.set(0);
    }

    // IF BUTTON PRESSED, REVERSE FLYWHEEL AND STORAGE
    if(right5 == true)
    {
      leftFlywheelMotor.setVoltage(8);
      rightFlywheelMotor.setVoltage(8);
      storageMotor.setVoltage(-10);
    }

    /***********************************************************************************************************************************************/
  }

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