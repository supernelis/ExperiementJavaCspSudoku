package com.archiwise.experiment.counter;

import org.jcsp.lang.CSProcess;
import org.jcsp.lang.Channel;
import org.jcsp.lang.ChannelOutputInt;
import org.jcsp.lang.One2OneChannelInt;
import org.jcsp.net.NetChannelOutput;
import org.jcsp.net.Node;
import org.jcsp.net.cns.CNS;

import java.util.Random;

/**
 * Created by nelis on 02/11/15.
 */
public class NumberProducer implements CSProcess {

    private ChannelOutputInt output;

    public NumberProducer(final ChannelOutputInt output) {
        this.output = output;
    }

    @Override
    public void run() {
        Random m = new Random();

        while(true){
            int i = m.nextInt();
            System.out.println("Producing number" +  i);
            output.write(i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
