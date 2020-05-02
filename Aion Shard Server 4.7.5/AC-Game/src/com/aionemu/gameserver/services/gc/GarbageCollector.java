package com.aionemu.gameserver.services.gc;

import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aionemu.gameserver.configs.main.GSConfig;


/**
 * @author GiGatR00n v4.7.5.x
 */
public class GarbageCollector extends Thread {

	private static final Logger log = LoggerFactory.getLogger(GarbageCollector.class);

	private static long g_Period = (30 * 60 * 1000); // 30 minutes
	
	public GarbageCollector() {
		g_Period = (GSConfig.GC_OPTIMIZATION_TIME < 1) ? 30 : GSConfig.GC_OPTIMIZATION_TIME;
		g_Period = g_Period * 60 * 1000;
	}
	
    /**
     * instantiate class 
     */
    private static class SingletonHolder {
        protected static final GarbageCollector instance = new GarbageCollector();
    }

    public static GarbageCollector getInstance() {
        return SingletonHolder.instance;
    }
	
	@Override
	public void run()
	{
		if (GSConfig.ENABLE_MEMORY_GC) {
			log.info("Garbage Collector is scheduled at duration: " + String.valueOf(g_Period) + " in milliseconds.");
			StartMemoryOptimization();
		} else {
			log.info("Garbage Collector is turned off by administrator.");
		}
	}
    
    private void StartMemoryOptimization() {
    	
    	Timer t = new Timer();
    	t.schedule(new TimerTask() {
    		
			@Override
			public void run() {
		    	try
		    	{
		    		// When we reload configs, it need to initialized again.
		    		g_Period = (GSConfig.GC_OPTIMIZATION_TIME < 1) ? 30 : GSConfig.GC_OPTIMIZATION_TIME;
		    		g_Period = g_Period * 60 * 1000;
		    		
		    		if (GSConfig.ENABLE_MEMORY_GC) {
			    		log.info("Garbage Collector is optimizing memory to free unused heap memory.");
			    		System.gc();
			    		System.runFinalization();
			    		log.info("Garbage Collector has finished optimizing memory.");		    			
		    		}
		    	} catch (Exception e) {
		    	}				
			}
    	}, g_Period);
    }
}
