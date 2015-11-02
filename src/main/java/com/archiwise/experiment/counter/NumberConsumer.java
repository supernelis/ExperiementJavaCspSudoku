package com.archiwise.experiment.counter;

import org.jcsp.lang.CSProcess;
import org.jcsp.lang.Channel;
import org.jcsp.lang.ChannelInputInt;
import org.jcsp.lang.One2OneChannelInt;
import org.jcsp.net.Node;

/**
 * Created by nelis on 02/11/15.
 */
public class NumberConsumer implements CSProcess {

    private ChannelInputInt input;

    public NumberConsumer(final ChannelInputInt input) {
        this.input = input;
    }

    @Override
    public void run() {
        while(true){
            System.out.println("Received number" + input.read());
        }
    }



}
