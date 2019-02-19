package frc.robot;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;

public class HatchArmManipulator {
    private Talon _ArmMotor; // Motor controller attached to Arm Mech
    private int _TargetPosition; // Target Positional value of Arm

    private Encoder _armEncoder; // Encoder that takes motor position readings

    private DoubleSolenoid _HatchEject; // Solenoid that controls ejector pins

    private double kP = 0.002f; // Proportional controller constant
    private double kI = 0.0f; // Integral controller constant

    public HatchArmManipulator(int motorPort, int pmRetract, int pmEject, int encA, int encB){
        _ArmMotor = new Talon(motorPort);
        _HatchEject = new DoubleSolenoid(pmRetract, pmEject);
        _armEncoder = new Encoder(encA, encB, true, Encoder.EncodingType.k4X);
        _armEncoder.setDistancePerPulse(7);
    }

    public void periodicexec(){
        //double targetval = Dmap(m_joystick.getY(), -1.0f, 1.0f, -2000.0f, 2000.0f);
        double multiplier = (_TargetPosition - _armEncoder.getDistance()) * 0.002f ;
        System.out.println(_armEncoder.getDistance());
       // _ArmMotor.set(multiplier);
        //System.out.println("mul:"+multiplier);
    }

    public void setTargetPosition(ArmPositions pos){ // Method for setting our desired Position of the Arm
        _TargetPosition = pos.getValue();
    }

    public void hatchEject(){
        _HatchEject.set(Value.kForward);
    }

    public void hatchRetract(){
        _HatchEject.set(Value.kReverse);
    }

    public void hatchToggle(){
        if (_HatchEject.get() == Value.kForward){
            hatchRetract();
        } else {
            hatchEject();
        }
    }

    enum ArmPositions { // Enum containing all of the possible Arm Postions we may want to move to
        FULLYRETRACTED(0),
        UPRIGHT(0), // TODO: Set to good value
        HATCHPICKUP(0); // TODO: Set to good value

        private final int id;
        ArmPositions(int id) { this.id = id; }
        public int getValue() { return id; }
    }

    double Dmap(double x, double in_min, double in_max, double out_min, double out_max) // Map function for Porportional controller
    {
      return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }
}