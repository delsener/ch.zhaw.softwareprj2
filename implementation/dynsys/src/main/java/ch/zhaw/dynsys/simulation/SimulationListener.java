package ch.zhaw.dynsys.simulation;

import java.util.Collection;

public interface SimulationListener {
	public void started();
	public void stoped();
	public void evolved(Collection<Culture> cultures);
	public void clear();
}
