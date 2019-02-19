package frc.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class RampController{

    private DoubleSolenoid _RampDeploy;
    
    public RampController(int forward, int retract){
        _RampDeploy = new DoubleSolenoid(forward, retract);
    }

    public void DropRamps(){
        _RampDeploy.set(Value.kForward);
    }

    public void RetractRamps(){
        _RampDeploy.set(Value.kReverse);
    }


}