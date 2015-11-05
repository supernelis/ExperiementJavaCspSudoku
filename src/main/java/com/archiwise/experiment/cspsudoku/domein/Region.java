package com.archiwise.experiment.cspsudoku.domein;

import org.jcsp.lang.AltingChannelInput;
import org.jcsp.lang.CSProcess;
import org.jcsp.lang.ChannelInput;
import org.jcsp.lang.ChannelOutput;
import org.jcsp.lang.One2OneChannel;

import java.util.Optional;


/**
 * Created by nelis on 02/11/15.
 */
public class Region implements CSProcess {

    private ChannelOutput<ValueAtPos> display;
    private Optional<AltingChannelInput<ValueAtPos>> westIn = Optional.empty();
    private Optional<ChannelOutput<ValueAtPos>> eastOut = Optional.empty();
    private Optional<ChannelOutput<ValueAtPos>> westOut = Optional.empty();
    private Optional<AltingChannelInput<ValueAtPos>> eastIn = Optional.empty();
    private Optional<AltingChannelInput<ValueAtPos>> nordIn = Optional.empty();
    private Optional<ChannelOutput<ValueAtPos>> nordOut = Optional.empty();
    private Optional<ChannelOutput<ValueAtPos>> southOut = Optional.empty();
    private Optional<AltingChannelInput<ValueAtPos>> southIn = Optional.empty();



    public Region(final ChannelOutput display) {
        this.display = display;
    }

    public void setPredefinedValue(final ValueAtPos valueAtPos){
        display.write(valueAtPos);
        writeIfPresent(valueAtPos,eastOut);
        writeIfPresent(valueAtPos,nordOut);
        writeIfPresent(valueAtPos,southOut);
        writeIfPresent(valueAtPos,westOut);
    }

    private void writeIfPresent(final ValueAtPos valueAtPos, final Optional<ChannelOutput<ValueAtPos>> channel) {
        if(channel.isPresent()) channel.get().write(valueAtPos);
    }

    @Override
    public void run() {
        processNeighborMessage(westIn, eastOut);
        processNeighborMessage(eastIn, westOut);
        processNeighborMessage(nordIn, southOut);
        processNeighborMessage(southIn, nordOut);

    }

    public void setWestOut(final ChannelOutput<ValueAtPos> westOut) {
        this.westOut = Optional.of(westOut);
    }

    public void setEastIn(final AltingChannelInput<ValueAtPos> eastIn) {
        this.eastIn = Optional.of(eastIn);
    }

    public void setNordIn(final AltingChannelInput<ValueAtPos> nordIn) {
        this.nordIn = Optional.of(nordIn);
    }

    public void setNordOut(final ChannelOutput<ValueAtPos> nordOut) {
        this.nordOut = Optional.of(nordOut);
    }

    public void setSouthOut(final ChannelOutput<ValueAtPos> southOut) {
        this.southOut = Optional.of(southOut);
    }

    public void setSouthIn(final AltingChannelInput<ValueAtPos> southIn) {
        this.southIn = Optional.of(southIn);
    }

    private void processNeighborMessage(final Optional<AltingChannelInput<ValueAtPos>> channelIn, final Optional<ChannelOutput<ValueAtPos>> channelOut) {
        if(channelIn.isPresent() && channelIn.get().pending()){
            ValueAtPos valueIn = channelIn.get().read();

            writeIfPresent(valueIn,channelOut);

        }
    }


    public void setWestIn(final AltingChannelInput<ValueAtPos> westIn) {
        this.westIn = Optional.of(westIn);
    }

    public void setEastOut(final ChannelOutput<ValueAtPos> eastOut) {
        this.eastOut = Optional.of(eastOut);
    }
}

