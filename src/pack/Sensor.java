package pack;

import lejos.robotics.SampleProvider;
import lejos.robotics.filter.AbstractFilter;

/**
 * Touch Sensor class, returns the state of the touch sensor
 * @author Oliver Palmer
 *
 */
public class Sensor extends AbstractFilter {

	float[] sample;
	
	public Sensor(SampleProvider source) {
		super(source);
		sample = new float[sampleSize];
	}
	
	/**
	 * Checks state of touch sensor
	 * @return Boolean - true if touch sensor is pressed
	 */
	public boolean pressed(){
		super.fetchSample(sample, 0);
		if (sample[0] == 0) {
			return false;
		} else {
			return true;
		}
	}
}
