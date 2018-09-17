package it.unisa.dia.gas.plaf.jpbc.util.concurrent.accumultor;

import it.unisa.dia.gas.plaf.jpbc.util.concurrent.Pool;

import java.util.concurrent.Callable;

/**
 * @author Angelo De Caro (jpbclib@gmail.com)
 * @since 2.0.0
 */
public interface Accumulator<T> extends Pool<T> {

    Accumulator<T> accumulate(Callable<T> callable);

    Accumulator<T> awaitTermination();

    T awaitResult();

    T getResult();

}
