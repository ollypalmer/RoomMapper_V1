package pack;

import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.chassis.WheeledChassis;
import lejos.robotics.navigation.MovePilot;
import lejos.utility.Delay;

/**
 * The controller class for the project. Contains the flow of logic for the program.
 * @author Oliver Palmer
 *
 */
public class V1Control {
	
	Sensor touch;
	
	public static void main(String[] args) {
		new V1Control();
	}

	public V1Control(){
		MovePilot pilot = getPilot();
		Brick brick = BrickFinder.getDefault();
		
		// Touch sensor config
		Port s1 = brick.getPort("S1");
		EV3TouchSensor tSensor = new EV3TouchSensor(s1);
		touch = new Sensor(tSensor);

		// Begins forward movement
		pilot.forward();
		while(true){
			// Delay to decrease strain on processor
			Delay.msDelay(2);
			// Reverses and turns the robot when the touch sensor is pressed
			if (touch.pressed()){
				pilot.stop();
				pilot.travel(-200);
				pilot.rotate(-90);
				pilot.forward();
			}
			// Exits program when escape button is pressed
			if (Button.ESCAPE.isDown()){
				pilot.stop();
				tSensor.close();
				System.exit(0);
			}
		}
	}
	
	/**
	 * Creates a Move Pilot object to control the movement of the EV3
	 * @return new MovePilot object
	 */
	public MovePilot getPilot(){
		Wheel wheelL = WheeledChassis.modelWheel(Motor.A, 43.2).offset(68.4);
		Wheel wheelR = WheeledChassis.modelWheel(Motor.B, 43.2).offset(-68.4);
		Chassis chassis = new WheeledChassis(new Wheel[]{wheelL, wheelR}, WheeledChassis.TYPE_DIFFERENTIAL);
		return pilotConfig(new MovePilot(chassis));
	}
	
	/**
	 * Configuration for the Move Pilot. Sets angular and linear speed and acceleration.
	 * @param pilot the Move Pilot to be configured
	 * @return The configured Move Pilot object
	 */
	public MovePilot pilotConfig(MovePilot pilot) {
		pilot.setLinearSpeed(100);
		pilot.setLinearAcceleration(100);
		pilot.setAngularSpeed(100);
		pilot.setAngularAcceleration(100);
		return pilot;
	}
}
