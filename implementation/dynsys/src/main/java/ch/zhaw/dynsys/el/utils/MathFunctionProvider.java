package ch.zhaw.dynsys.el.utils;

import java.util.Random;

public class MathFunctionProvider {
	private Random random = new Random();

	public double cos(double x) {
        return Math.cos(x);
    } 
	
	public double sin(double x) {
        return Math.sin(x);
    }
	
	public double abs(double x) {
        return Math.abs(x);
    }
	
	public double log(double x) {
        return Math.log(x);
    }
	
	public double max(double a, double b) {
        return Math.max(a,b);
    }
	
	public double min(double a, double b) {
        return Math.min(a, b);
    }
	
	public double sgn(double x) {
		return Math.signum(x);
	}
	
	public double rand() {
        return random.nextDouble();
    }
	
}
