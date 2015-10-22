import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by alan on 10/21/15.
 */
public class Application {

    /*
    Un main que cree un thread
    Ese thread a partir de una cola consume todos los valores que tenga y los imprime por pantalla
    después de mandar a ejecutar el thread va a llenar la cola con valores.
    El main termina la ejecucion una vez que el otro thread termino de mostar por pantalla todos los resultados
    La idea es que muestre en orden todos los valores de la cola

    El thread que consume tiene que saber que va a terminar cuando ya proceso 1000 elementos
     */
    public static void main(String args[])
    {

        Queue<Integer> queue = new ConcurrentLinkedQueue<>(); //Los generics no soportan tipos primitivos
                                                                     //ConcurrentLinkeDeque es una implementacion de Queue

        //NumberConsumer t = new NumberConsumer(queue);

        //Ahora hacerlo para N number consumers
        //Lo voy a hacer para 4
        ArrayList<NumberConsumer> threads = new ArrayList<>();

        AtomicInteger counter = new AtomicInteger(1000);

        for(int i =0; i<4; i++)
            threads.add(new NumberConsumer(queue, counter)); //instancio cada thread

        for(int i =0; i<4; i++)
            threads.get(i).start(); //arranca la ejecucion de cada thread

        for (int i=0; i<1000; i++)
        {
            queue.add(i); //agrego los items a la queue
            //el add si no tiene suficiente capacidad devuelve una excepcion (tiene implementado un throws)
            //el offer si no tiene suficiente capacidad devuelve un false
        }

        try {
            for(int i =0; i<4; i++)
                threads.get(i).join();
            //la logica de que muestre n cantidad de elementos la tengo que implementar ACA
            //No en la implementacion de los threads
        } catch(InterruptedException e){
            System.out.println(e.getMessage());
        }

    }
}
