package frc.robot;

//import edu.wpi.cscore.UsbCamera;
//import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {

  /* DRIVETRAIN */
  /***********************************************************************************************************************************************/
  // INSTANTIATE LEFT MOTORS AND LEFT DRIVE
  public SpeedController leftBackDrive = new PWMVictorSPX(4);
  public SpeedController leftFrontDrive = new PWMVictorSPX(5);
  public SpeedControllerGroup leftDrive = new SpeedControllerGroup(leftBackDrive, leftFrontDrive);

  // INSTANTIATE RIGHT MOTORS AND RIGHT DRIVE
  public SpeedController rightBackDrive = new PWMVictorSPX(3);
  public SpeedController rightFrontDrive = new PWMVictorSPX(2);
  public SpeedControllerGroup rightDrive = new SpeedControllerGroup(rightBackDrive, rightFrontDrive);
  /***********************************************************************************************************************************************/
  
  /* INTAKE, STORAGE, FLYWHEEL, ARM, AND CLAW MOTORS */
  /***********************************************************************************************************************************************/
  // INSTANTIATE INTAKE MOTOR
  public PWMVictorSPX intakeMotor = new PWMVictorSPX(6);

  // INSTANTIATE STORAGE MOTOR
  //public PWMVictorSPX storageMotor = new PWMVictorSPX(9);
  public Spark storageMotor = new Spark(9);

  // INSTANTIATE FLYWHEEL MOTORS AND FLYWHEEL
  public SpeedController leftFlywheelMotor = new PWMVictorSPX(7);
  public SpeedController rightFlywheelMotor = new PWMVictorSPX(8);

  // INSTANTIATE CLAW AND ARM MOTORS
  public SpeedController armMotor = new PWMVictorSPX(1);
  public SpeedController clawMotor = new PWMVictorSPX(0);
  /***********************************************************************************************************************************************/ 

  /* PNEUMANTICS AND LIMELIGHT */
  /***********************************************************************************************************************************************/
  // INSTANTIATE COMPRESSOR
  public Compressor compressor = new Compressor();

  // INSTANTIATE INTAKE SOLENOID
  public DoubleSolenoid intakePiston = new DoubleSolenoid(6, 1);

  // INSTANTIATE STORAGE SOLENOID
  public DoubleSolenoid storagePiston = new DoubleSolenoid(3, 2);

  // INSTANTIATE LIMELIGHT TABLE
  public NetworkTable tableLimelight = NetworkTableInstance.getDefault().getTable("limelight");

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
  
  @Override
  public void robotInit() {
     // RESET PNEUMATICS
    storagePiston.set(Value.kReverse);
    intakePiston.set(Value.kReverse);
    compressor.clearAllPCMStickyFaults();
    compressor.start();

    //  // START CAMERA
    //  UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
    //  camera.setFPS(30);
    //  camera.setResolution(640, 480);
  }

  @Override
  public void robotPeriodic() {}

  @Override
  public void autonomousInit() {
    storageMotor.set(0);
    leftFlywheelMotor.set(0);
    rightFlywheelMotor.set(0);
    // ACTIVIATE LIMELIGHT
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(3);
    
    // DRIVE ROBOT FORWARD
    leftDrive.set(-.43);
    rightDrive.set(.4);
    edu.wpi.first.wpilibj.Timer.delay(1.6);

    long start = System.currentTimeMillis();
    long end = start + 2000;

    while (System.currentTimeMillis() < end) {
    // ACTIVIATE LIMELIGHT
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(3);
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(0);

     // UPDATE NETOWRKTABLES, SET VARIABLES
     NetworkTableEntry tx = tableLimelight.getEntry("tx");
     double x = tx.getDouble(0.0);
     double min_command = 0.05;
     double Kp = -0.05;
     double heading_error = -x*.3;
     double steering_adjust = 0;

     // MULIPLIED BY A CONSTANT, CACLULATE THE STEERING ADJUSTMEANT BY MULTIPLYING THE CONSTANT BY THE AMOUNT NEEDED TO ROTATE, IMPLEMENTING A SMALL REQUIRED MOVEMENT
     if (x > 1.0) {
         steering_adjust = Kp*heading_error + min_command;
       }
       else if (x < 1.0) {
         steering_adjust = Kp*heading_error - min_command;
       }
       // SET STEERING ADJUST TO DRIVE SIDES
       leftDrive.set(steering_adjust);
       rightDrive.set(steering_adjust);
      }
    // ACTIVATE SHOOTER MOTORS
    leftFlywheelMotor.setVoltage(-6.5);
    rightFlywheelMotor.setVoltage(-6);
    edu.wpi.first.wpilibj.Timer.delay(4.5);
    storageMotor.setVoltage(8);
    edu.wpi.first.wpilibj.Timer.delay(.1);
    storageMotor.setVoltage(0);
    edu.wpi.first.wpilibj.Timer.delay(2);
    storageMotor.setVoltage(8);
    edu.wpi.first.wpilibj.Timer.delay(.1);
    storageMotor.setVoltage(0);
    edu.wpi.first.wpilibj.Timer.delay(2);
    storageMotor.setVoltage(8);
    edu.wpi.first.wpilibj.Timer.delay(.2);
  }

  @Override
  public void autonomousPeriodic() {
    leftFlywheelMotor.setVoltage(0);
    rightFlywheelMotor.setVoltage(0);
    storageMotor.setVoltage(0);
  }

  @Override
  public void teleopInit() {
    //NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(1);
    leftFlywheelMotor.setVoltage(0);
    rightFlywheelMotor.setVoltage(0);
    intakeMotor.setVoltage(0);
    storageMotor.setVoltage(0);
    armMotor.setVoltage(0);
    clawMotor.setVoltage(0);
  }

  @Override
  public void teleopPeriodic() {
    /* CONTROLLER */
    /***********************************************************************************************************************************************/
    // MAPPING BUTTONS AND JOYSTICK INPUTS TO VARIABLES
    double leftY = leftJoystick.getRawAxis(leftStickY);
    double rightY = rightJoystick.getRawAxis(rightStickY);
    boolean right1 = r1Button.get();
    boolean right2 = r2Button.get();
    boolean right3 = r3Button.get();
    boolean right4 = r4Button.get();
    boolean left1 = l1Button.get();
    boolean left5 = l5Button.get();
    boolean left3 = l3Button.get();
    boolean left4 = l4Button.get();
    boolean left6 = l6Button.get();
    /***********************************************************************************************************************************************/
    
    /* DRIVETRAIN */
    /***********************************************************************************************************************************************/
    // SCALE DOWN JOYSTICK AXIS VALUES
    leftY = leftY*.8;
    rightY = rightY*.8;

    // READS BUTTON VALUES TO ADJUST SPEED OF DRIVETRAIN
    if (right3 == true) {
      leftY = leftY * .95;
      rightY = rightY * .95;
    } else {
      leftY = leftY * .75;
      rightY = rightY * .75;
    }

    // REMOVES CONTROLLER DRIFT
    if (leftY < .1) {
      if (leftY > -.1) {
        leftY = 0;
      }
    }
    if (rightY < .1) {
      if (rightY > -.1) {
        rightY = 0;
      }
    }

    // SETS DRIVE SIDES TO AXES
    leftDrive.set(-leftY);
    rightDrive.set(rightY);
    /***********************************************************************************************************************************************/
    
    /* INTAKE w/STORAGE */
    /***********************************************************************************************************************************************/
    // IF BUTTON PRESSED, ACTIVATE INTAKE MOTOR
    if(left1 == true) {
      intakeMotor.set(.5);
    } else {
      intakeMotor.set(0);
    }

    // IF BUTTON PRESSED, ACTIVATE OR DEACTIVATE INTAKE PISTON
    if(left3 == true) {
      intakePiston.set(Value.kReverse);
    }
    if (left5 == true) {
      intakePiston.set(Value.kForward);
    }
    /***********************************************************************************************************************************************/

    /* FLYWHEEL w/STORAGE */
    /***********************************************************************************************************************************************/
    // IF RIGHT TRIGGER PRESSED, ACTIVATE FLYWHEEL MOTORS
    if(right1 == true) {
      leftFlywheelMotor.setVoltage(-12);
      rightFlywheelMotor.setVoltage(-11);
    } else {
      leftFlywheelMotor.set(0);
      rightFlywheelMotor.set(0);
    }

    // IF BUTTON PRESSED, ACTIVATE STORAGE MOTOR
    if(right2 == true) {
      storageMotor.setVoltage(10);
    } else {
      storageMotor.set(0);
    }

    // IF BUTTON PRESSED, ACTIVATE OR DEACTIVATE STORAGE PISTON
    if (left4 == true) {
      storagePiston.set(Value.kForward);
    }
    if (left6 == true) {
      storagePiston.set(Value.kReverse);
    }
    /***********************************************************************************************************************************************/


    /* ARM AND CLAW */
    /***********************************************************************************************************************************************/
    int dPad = rightJoystick.getPOV();
    System.out.println(dPad);

    if (dPad == 0)
    {
      armMotor.set(0.3);
    }
    else if (dPad == 180)
    {
      armMotor.set(-0.2);
    }
    else
    {
      armMotor.set(.1);
    }

    if (dPad == 90)
    {
      clawMotor.set(-.28);
    }
    else if (dPad == 270)
    {
      clawMotor.set(.28);
    } else
    {
      clawMotor.set(0);
    }
    


    /***********************************************************************************************************************************************/

    /* LIMELIGHT */
    /***********************************************************************************************************************************************/
    // IF BUTTON PRESSED, RUN LIMELIGHT
    if (right4 == true) {
     // ACTIVIATE LIMELIGHT
     NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(3);
     NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(0);

      // UPDATE NETOWRKTABLES, SET VARIABLES
      NetworkTableEntry tx = tableLimelight.getEntry("tx");
      double x = tx.getDouble(0.0);
      double min_command = 0.05;
      double Kp = -0.05;
      double heading_error = -x*.3;
      double steering_adjust = 0;

      // MULIPLIED BY A CONSTANT, CACLULATE THE STEERING ADJUSTMEANT BY MULTIPLYING THE CONSTANT BY THE AMOUNT NEEDED TO ROTATE, IMPLEMENTING A SMALL REQUIRED MOVEMENT
      if (x > 1.0) {
          steering_adjust = Kp*heading_error + min_command;
        }
        else if (x < 1.0) {
          steering_adjust = Kp*heading_error - min_command;
        }
        // SET STEERING ADJUST TO DRIVE SIDES
        leftDrive.set(steering_adjust);
        rightDrive.set(steering_adjust);
    } else {
    // SHUT OFF LIMELIGHT AND USE AS NORMAL CAMERA
    //NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(1);
    //NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(1);
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