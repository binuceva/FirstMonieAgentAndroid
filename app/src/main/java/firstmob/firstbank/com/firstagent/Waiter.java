package firstmob.firstbank.com.firstagent;

import android.util.Log;

/**
 * Created by deeru on 15-07-2015.
 */
public class Waiter extends  Thread {

    private static final String TAG=Waiter.class.getName();
    private long lastUsed;
    private long period;
    private boolean stop;

    public Waiter(long period)
    {
        this.period=period;
        stop=false;
    }

    public void run()
    {
        long idle=0;
        this.touch();
        do
        {
            idle=System.currentTimeMillis()-lastUsed;
            Log.d(TAG, "Application is idle for " + idle + " ms");
            try
            {
                Thread.sleep(5000); //check every 5 seconds
            }
            catch (InterruptedException e)
            {
                Log.d(TAG, "Waiter interrupted!");
            }
            if(idle > period)
            {
                idle=0;
                //do something here - e.g. call popup or so
             Log.d("Idle period","Idle period of 1 minute");
            }
        }
        while(!stop);
        Log.d(TAG, "Finishing Waiter thread");
    }

    public synchronized void touch()
    {
        lastUsed=System.currentTimeMillis();
    }

    public synchronized void forceInterrupt()
    {
        this.interrupt();
    }

    //soft stopping of thread
   /* @Override
    public synchronized void stop()
    {
        stop=true;
    }*/

    public synchronized void setPeriod(long period)
    {
        this.period=period;
    }
}
