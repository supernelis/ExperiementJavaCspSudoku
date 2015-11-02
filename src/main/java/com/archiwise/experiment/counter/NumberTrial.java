package com.archiwise.experiment.counter;

import org.jcsp.lang.CSProcess;
import org.jcsp.lang.Channel;
import org.jcsp.lang.One2OneChannelInt;
import org.jcsp.lang.Parallel;
import org.jcsp.net.Node;

/**
 * Created by nelis on 02/11/15.
 */
public class NumberTrial {

    public static void main(String[] args) {
        One2OneChannelInt out = Channel.one2oneInt();

        new Parallel(
              new CSProcess[]{

                    new NumberConsumer(out.in()),
                    new NumberProducer(out.out())}
        ).run();


    }
}
