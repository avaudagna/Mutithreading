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
    NumberConsumer(Queue<Integer> queue, AtomicInteger max){
        _max = max;
        _counter=0;
        _stop=false;
        _queue = queue;
    };

    @Override
    public void run() {
        //super.run();
        _counter=0;
        while(!_stop)
        {
            try
            {
                System.out.println(Integer.toString(_counter)+":"+Integer.toString(_queue.poll()));
                _max.decrementAndGet();
                _counter++;
            } catch (NullPointerException e)
            {
                continue;
            }
            //Esto seria un problema (race condition) si se quisieran imprimir
            //menos o mas elementos de los que tiene la queue
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
