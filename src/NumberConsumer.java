import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by alan on 10/21/15.
 */
public class NumberConsumer extends Thread {

    private int _counter;
    private Queue<Integer> _queue;
    private boolean _stop;
    private AtomicInteger _max;
    NumberConsumer(Queue<Integer> queue, AtomicInteger counter){
        _max = counter;
        _counter=0;
        _stop=false;
        _queue = queue;
    };

    @Override
    public void run() {
        //super.run();
        _counter=0;
        while(!_stop)
        {   _counter++;
            //_max.decrementAndGet();


            try
            {
                synchronized (this){
                System.out.println(Integer.toString(_counter)+":"+Integer.toString(_queue.poll()));}
                //Ahora tengo que syncronizarlo con el add tambien
                _max.set(_max.decrementAndGet());
            } catch (NullPointerException e)
            {
                continue;
            }
            if(_max.get()<=0) {
                dispose();
                break;
            }
        }
    }

    public void dispose() {
        _stop=true;
    }
}
