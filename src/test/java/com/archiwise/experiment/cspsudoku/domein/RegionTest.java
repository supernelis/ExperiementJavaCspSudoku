package com.archiwise.experiment.cspsudoku.domein;

import org.jcsp.lang.Channel;
import org.jcsp.lang.ChannelOutput;
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
    private One2OneChannel<ValueAtPos> leftIn;
    private One2OneChannel<ValueAtPos> rightOut;

    @Before
    public void setUp(){
        display = Channel.one2one(new Buffer<ValueAtPos>(1));
        leftIn = Channel.one2one(new Buffer<ValueAtPos>(1));
        rightOut = Channel.one2one(new Buffer<ValueAtPos>(1));
        region = new Region(display.out());
            }

    @Test
    public void WhenPredefinedValueIsSet_ThenDisplayIsUpdated(){
        final int value=1;
        final int row=1;
        final int col=1;

        region.setPredefinedValue(new ValueAtPos(value, row, col));

        ValueAtPos valueAtPos = display.in().read();

        assertEquals(new ValueAtPos(value, row, col),valueAtPos);
    }

    @Test
    public void WhenValueIsReceivedLeft_ValueIshandedToRight(){
        final ValueAtPos valueSend = new ValueAtPos(5, 2, 2);
        region.setLeftIn(leftIn);
        region.setRightOut(rightOut);

        leftIn.out().write(valueSend);

        region.run();

        final ValueAtPos valueReceived = rightOut.in().read();

        assertEquals(valueSend,valueReceived);
    }

    @Test
    public void WhenNoRightChannelIsSet_ThenNoException(){
        final ValueAtPos valueSend = new ValueAtPos(5, 2, 2);
        leftIn.out().write(valueSend);
        region.setLeftIn(leftIn);

        region.run();
    }


}