package ch.zhaw.dynsys.simulation;

public interface SimulationListener {
	public void started();
	public void stoped();
	public void evolved(double[] values);
	public void clear();
}
