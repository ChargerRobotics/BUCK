package frc.robot;

import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.PWMVictorSPX;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot 
{

  /* DRIVETRAIN */
  /***********************************************************************************************************************************************/
  // INSTANTIATE LEFT MOTORS AND LEFT DRIVE
  public MotorController leftBackDrive = new PWMVictorSPX(4);
  public MotorController leftFrontDrive = new PWMVictorSPX(5);
  public MotorControllerGroup leftDrive = new MotorControllerGroup(leftBackDrive, leftFrontDrive);

  // INSTANTIATE RIGHT MOTORS AND RIGHT DRIVE
  public MotorController rightBackDrive = new PWMVictorSPX(3);
  public MotorController rightFrontDrive = new PWMVictorSPX(2);
  public MotorControllerGroup rightDrive = new MotorControllerGroup(rightBackDrive, rightFrontDrive);
  /***********************************************************************************************************************************************/
  
  /* STORAGE AND FLYWHEEL*/
  /***********************************************************************************************************************************************/
  // INSTANTIATE STORAGE MOTOR
  public PWMVictorSPX storageMotor = new PWMVictorSPX(9);
  //public Spark storageMotor = new Spark(9);

  // INSTANTIATE FLYWHEEL MOTORS AND FLYWHEEL
  public MotorController leftFlywheelMotor = new PWMVictorSPX(7);
  public MotorController rightFlywheelMotor = new PWMVictorSPX(8);
  /***********************************************************************************************************************************************/ 

  /* CONTROLLER */
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
  public JoystickButton l1Button = new JoystickButton(leftJoystick, 1);
  public JoystickButton l2Button = new JoystickButton(leftJoystick, 2);
  public JoystickButton l3Button = new JoystickButton(leftJoystick, 3);
  public JoystickButton l4Button = new JoystickButton(leftJoystick, 4);
  public JoystickButton l5Button = new JoystickButton(leftJoystick, 5);
  public JoystickButton l6Button = new JoystickButton(leftJoystick, 6);
   
  // INSTANTIATE JOYSTICK BUTTONS
  public JoystickButton r1Button = new JoystickButton(rightJoystick, 1);
  public JoystickButton r2Button = new JoystickButton(rightJoystick, 2);
  public JoystickButton r3Button = new JoystickButton(rightJoystick, 3);
  public JoystickButton r4Button = new JoystickButton(rightJoystick, 4);
  public JoystickButton r5Button = new JoystickButton(rightJoystick, 5);
  public JoystickButton r6Button = new JoystickButton(rightJoystick, 6);
  /***********************************************************************************************************************************************/

  /** This function is called once whenever the robot starts a sequence. */
  @Override
  public void robotInit() 
  {
    // START CAMERA
    UsbCamera camera = CameraServer.startAutomaticCapture();
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
  public void autonomousPeriodic() {}

  /** This function is called once when the robot starts teleop. */
  @Override
  public void teleopInit() 
  {
    //NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(1);
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
    double leftSliderValue = leftJoystick.getRawAxis(leftSlider);
    leftSliderValue *= -1;
    System.out.println(leftSliderValue);
    if (leftSlider < 0)
    {
      leftSliderValue = 0.5;
    }

    boolean right1 = r1Button.get();
    boolean right2 = r2Button.get();
    boolean right5 = r5Button.get();
    /***********************************************************************************************************************************************/
    
    /* DRIVETRAIN */
    /***********************************************************************************************************************************************/
    // // SCALE DOWN JOYSTICK AXIS VALUES
    // leftY = leftY*.8;
    // rightY = rightY*.8;

    // // READS BUTTON VALUES TO ADJUST SPEED OF DRIVETRAIN
    // if (right3 == true) 
    // {
    //   leftY = leftY * .95;
    //   rightY = rightY * .95;
    // } 
    // else 
    // {
    //   leftY = leftY * .75;
    //   rightY = rightY * .75;
    // }

    // // REMOVES CONTROLLER DRIFT
    // if (leftY < .1) 
    // {
    //   if (leftY > -.1) 
    //   {
    //     leftY = 0;
    //   }
    // }
    // if (rightY < .1) 
    // {
    //   if (rightY > -.1) 
    //   {
    //     rightY = 0;
    //   }
    // }

    // // SETS DRIVE SIDES TO AXES
    // leftDrive.set(-leftY);
    // rightDrive.set(rightY);
    /***********************************************************************************************************************************************/
    
    /* FLYWHEEL AND STORAGE */
    /***********************************************************************************************************************************************/
    // IF RIGHT TRIGGER PRESSED, ACTIVATE FLYWHEEL MOTORS
    if(right1 == true) 
    {
      leftFlywheelMotor.setVoltage(-12 * leftSliderValue);
      rightFlywheelMotor.setVoltage(-11 * leftSliderValue);
    }
    else
    {
      leftFlywheelMotor.set(0);
      rightFlywheelMotor.set(0);
    }

    if(right5 == true)
    {
      leftFlywheelMotor.setVoltage(8);
      rightFlywheelMotor.setVoltage(8);
      storageMotor.setVoltage(-10);
    }

    // IF BUTTON PRESSED, ACTIVATE STORAGE MOTOR
    if(right2 == true) 
    {
      storageMotor.setVoltage(10);
    } else 
    {
      storageMotor.set(0);
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