package com.archiwise.experiment.cspsudoku.domein;

import org.jcsp.lang.CSProcess;
import org.jcsp.lang.ChannelOutput;
import org.jcsp.lang.One2OneChannel;

import java.util.Optional;


/**
 * Created by nelis on 02/11/15.
 */
public class Region implements CSProcess {

    private ChannelOutput<ValueAtPos> display;
    private Optional<One2OneChannel<ValueAtPos>> leftIn = Optional.empty();
    private Optional<One2OneChannel<ValueAtPos>> rightOut = Optional.empty();

    public Region(final ChannelOutput display) {
        this.display = display;
    }

    public void setPredefinedValue(final ValueAtPos valueAtPos){
        display.write(valueAtPos);
    }

    @Override
    public void run() {
        processLeft();
    }

    private void processLeft() {
        if(leftIn.isPresent() && leftIn.get().in().pending()){
            ValueAtPos in = leftIn.get().in().read();
            if(rightOut.isPresent())
                rightOut.get().out().write(in);
        }
    }

    public void setLeftIn(final One2OneChannel<ValueAtPos> leftIn) {
        this.leftIn = Optional.of(leftIn);
    }

    public void setRightOut(final One2OneChannel<ValueAtPos> rightOut) {
        this.rightOut = Optional.of(rightOut);
    }
}

