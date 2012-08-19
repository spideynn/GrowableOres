package me.Todkommt.GrowableOres;

import java.io.Serializable;

public class GrowTimer implements Runnable, Serializable {

	private static final long serialVersionUID = 1406104898463223228L;
	long timeToGrow;
	OrePlant plant;
	public long timeElapsed;
	public boolean running = true;
	
	public GrowTimer(float timeToGrow, OrePlant plant)
	{
		this.timeToGrow = (long) (timeToGrow*60f);
		this.plant = plant;
	}
	
	public GrowTimer(float timeToGrow, OrePlant plant, long timeElapsed)
	{
		this.timeToGrow = (long) (timeToGrow*60f);
		this.plant = plant;
		this.timeElapsed = timeElapsed;
	}
	
	@Override
	public void run() {
		//GrowableOres.instance.log.info("starting timer");
		while(running)
		{
			//GrowableOres.instance.log.info("pre-sleep");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			timeElapsed++;
			plant.timeElapsed = timeElapsed;
			//GrowableOres.instance.log.info("elapsedTime = " + timeElapsed + " timeToGrow = " + timeToGrow);
			if(timeElapsed == timeToGrow)
				plant.grow(this);
		}
	}

}
