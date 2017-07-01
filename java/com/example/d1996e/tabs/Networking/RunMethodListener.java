package com.example.d1996e.tabs.Networking;

/**
 * Created by D1996e on 6/20/2017.
 */
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import java.io.IOException;

public abstract class RunMethodListener<T> extends Listener {

    private final Class<T> clazz;

    public RunMethodListener(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public void received(Connection c, Object o){
        if (clazz.isInstance(o)) {
            int counter = 0;
            try { task(c,clazz.cast(o)); }
            catch (IOException e)
            {
                while (counter <= 10)
                {
                    System.out.println("Connection Interrupted, trying to reconnect");
                    run(c, clazz.cast(o));
                    counter++;
                    break;
                }
            }

        }
    }

    private final void task(Connection c, T received) throws IOException
    {
        run(c, received);
    }

    public abstract void run(Connection c, T received);
}
