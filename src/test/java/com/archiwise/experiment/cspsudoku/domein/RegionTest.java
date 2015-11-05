package com.archiwise.experiment.cspsudoku.domein;

import org.jcsp.lang.Channel;
import org.jcsp.lang.One2OneChannel;
import org.jcsp.util.Buffer;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
    private One2OneChannel<ValueAtPos> westOut;

    @Before
    public void setUp(){
        display = Channel.one2one(new Buffer<ValueAtPos>(1));
        westIn = Channel.one2one(new Buffer<ValueAtPos>(1));
        eastOut = Channel.one2one(new Buffer<ValueAtPos>(1));
        eastIn = Channel.one2one(new Buffer<ValueAtPos>(1));
        westOut = Channel.one2one(new Buffer<ValueAtPos>(1));
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

        verifyChannelContainsValue(display, predefinedValue);
    }

    @Test
    public void WhenPredefinedValueIsSet_ThenOutputChannelsAreUpdated(){

        //Given

        final int value=1;
        final int row=1;
        final int col=1;

        ValueAtPos predefinedValue = new ValueAtPos(value, row, col);

        region.setWestIn(westIn.in());
        region.setWestOut(westOut.out());
        region.setEastIn(eastIn.in());
        region.setEastOut(eastOut.out());
        region.setSouthIn(southIn.in());
        region.setSouthOut(southOut.out());
        region.setNordIn(nordIn.in());
        region.setNordOut(nordOut.out());

        // When
        region.setPredefinedValue(predefinedValue);

        // Then
        verifyChannelContainsValue(eastOut, predefinedValue);
        verifyChannelContainsValue(nordOut, predefinedValue);
        verifyChannelContainsValue(southOut, predefinedValue);
        verifyChannelContainsValue(westOut, predefinedValue);

    }

    /**
     * Helper method to verify if a value is available on a channel
     *
     * The method asserts that there is a value available, reads the value and asserts that it equals the value it gets as parameter.
     *
     * @param channel
     * @param predefinedValue
     */
    private void verifyChannelContainsValue(final One2OneChannel<ValueAtPos> channel, final ValueAtPos predefinedValue) {
        //There is a value on the channel
        assertTrue(channel.in().pending());
        // Get the value
        ValueAtPos valueAtRight = channel.in().read();
        // Compare the value with the predefined value
        assertEquals(predefinedValue,valueAtRight);
    }

    @Test
    public void WhenValueIsReceivedLeft_ValueIshandedToRight(){
        final ValueAtPos valueSend = new ValueAtPos(5, 2, 2);

        region.setWestIn(westIn.in());
        region.setEastOut(eastOut.out());

        westIn.out().write(valueSend);

        region.run();

        verifyChannelContainsValue(eastOut, valueSend);
    }

    @Test
    public void WhenNoRightChannelIsSet_ThenNoException(){
        final ValueAtPos valueSend = new ValueAtPos(5, 2, 2);
        westIn.out().write(valueSend);
        region.setWestIn(westIn.in());

        region.run();
    }


}