package com.archiwise.experiment.cspsudoku.domein;

import org.jcsp.lang.Channel;
import org.jcsp.lang.One2OneChannel;
import org.jcsp.util.Buffer;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by nelis on 02/11/15.
 */
public class RegionTest {

    private Region region;
    private One2OneChannel<ValueAtPos> display;
    private One2OneChannel<ValueAtPos> westIn;
    private One2OneChannel<ValueAtPos> eastOut;
    private One2OneChannel<ValueAtPos> eastIn;
    private One2OneChannel<ValueAtPos> nordIn;
    private One2OneChannel<ValueAtPos> nordOut;
    private One2OneChannel<ValueAtPos> southIn;
    private One2OneChannel<ValueAtPos> southOut;

    @Before
    public void setUp(){
        display = Channel.one2one(new Buffer<ValueAtPos>(1));
        westIn = Channel.one2one(new Buffer<ValueAtPos>(1));
        eastOut = Channel.one2one(new Buffer<ValueAtPos>(1));
        eastIn = Channel.one2one(new Buffer<ValueAtPos>(1));
        westIn = Channel.one2one(new Buffer<ValueAtPos>(1));
        nordIn = Channel.one2one(new Buffer<ValueAtPos>(1));
        nordOut = Channel.one2one(new Buffer<ValueAtPos>(1));
        southIn = Channel.one2one(new Buffer<ValueAtPos>(1));
        southOut = Channel.one2one(new Buffer<ValueAtPos>(1));

        region = new Region(display.out());
            }

    @Test
    public void WhenPredefinedValueIsSet_ThenDisplayIsUpdated(){
        final int value=1;
        final int row=1;
        final int col=1;

        ValueAtPos predefinedValue = new ValueAtPos(value, row, col);
        region.setPredefinedValue(predefinedValue);

        ValueAtPos valueAtPos = display.in().read();

        assertEquals(predefinedValue,valueAtPos);
    }

    @Test
    public void WhenPredefinedValueIsSet_ThenOutputChannelsAreUpdated(){
        final int value=1;
        final int row=1;
        final int col=1;

        ValueAtPos predefinedValue = new ValueAtPos(value, row, col);
        region.setPredefinedValue(predefinedValue);

        ValueAtPos valueAtRight = eastOut.in().read();
        assertEquals(predefinedValue,valueAtRight);

        //ValueAtPos valueAtLeft = leftOut.in().read();


    }

    @Test
    public void WhenValueIsReceivedLeft_ValueIshandedToRight(){
        final ValueAtPos valueSend = new ValueAtPos(5, 2, 2);
        region.setLeftIn(westIn);
        region.setRightOut(eastOut);

        westIn.out().write(valueSend);

        region.run();

        final ValueAtPos valueReceived = eastOut.in().read();

        assertEquals(valueSend,valueReceived);
    }

    @Test
    public void WhenNoRightChannelIsSet_ThenNoException(){
        final ValueAtPos valueSend = new ValueAtPos(5, 2, 2);
        westIn.out().write(valueSend);
        region.setLeftIn(westIn);

        region.run();
    }


}