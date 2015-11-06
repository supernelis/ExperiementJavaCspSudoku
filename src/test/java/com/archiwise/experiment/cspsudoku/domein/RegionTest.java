package com.archiwise.experiment.cspsudoku.domein;

import org.jcsp.lang.Channel;
import org.jcsp.lang.One2OneChannel;
import org.jcsp.util.Buffer;
import org.jcsp.util.InfiniteBuffer;
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
    private final ValueAtPos valueSend = new ValueAtPos(5, 2, 2);

    @Before
    public void setUp() {
        display = Channel.one2one(new InfiniteBuffer<ValueAtPos>());
        westIn = Channel.one2one(new InfiniteBuffer<ValueAtPos>());
        eastOut = Channel.one2one(new InfiniteBuffer<ValueAtPos>());
        eastIn = Channel.one2one(new InfiniteBuffer<ValueAtPos>());
        westOut = Channel.one2one(new InfiniteBuffer<ValueAtPos>());
        nordIn = Channel.one2one(new InfiniteBuffer<ValueAtPos>());
        nordOut = Channel.one2one(new InfiniteBuffer<ValueAtPos>());
        southIn = Channel.one2one(new InfiniteBuffer<ValueAtPos>());
        southOut = Channel.one2one(new InfiniteBuffer<ValueAtPos>());

        region = new Region(display.out());
    }

    @Test
    public void WhenPredefinedValueIsSet_ThenDisplayIsUpdated() {
        final int value = 1;
        final int row = 1;
        final int col = 1;

        ValueAtPos predefinedValue = new ValueAtPos(value, row, col);
        region.setPredefinedValue(predefinedValue);

        verifyChannelContainsValue(display, predefinedValue);
    }

    @Test
    public void WhenFivePredefinedValueAreSet_ThenAllValuesAreKnownOnDisplay() {

        ValueAtPos one = new ValueAtPos(1, 1, 1);
        ValueAtPos two = new ValueAtPos(2, 2, 1);
        ValueAtPos three = new ValueAtPos(3, 3, 1);
        ValueAtPos four = new ValueAtPos(4, 1, 2);
        ValueAtPos five = new ValueAtPos(5, 2, 2);
        ValueAtPos six = new ValueAtPos(6, 3, 2);

        region.setPredefinedValue(one);
        region.setPredefinedValue(two);
        region.setPredefinedValue(three);
        region.setPredefinedValue(four);
        region.setPredefinedValue(five);

        verifyChannelContainsValue(display, one,two,three,four,five,six);

    }

    @Test
    public void When4Col2RowValuesSet_ThenValueIsKnown() {

        // COL 1 VALUES
        ValueAtPos one = new ValueAtPos(1, 1, 1);
        ValueAtPos two = new ValueAtPos(2, 2, 1);
        ValueAtPos three = new ValueAtPos(3, 3, 1);

        // ROW VALUES
        ValueAtPos four = new ValueAtPos(4, 1, 1);
        ValueAtPos five = new ValueAtPos(5, 1, 2);

        connectAllChannelsToRegion();

        // When
        nordIn.out().write(one);
        nordIn.out().write(two);
        nordIn.out().write(three);

        eastIn.out().write(four);
        eastIn.out().write(five);

        region.run();

        // Then value is known
        verifyChannelContainsValue(display, new ValueAtPos(6,1,1));

    }


    @Test
    public void WhenPredefinedValueIsSet_ThenOutputChannelsAreUpdated() {

        //Given

        final int value = 1;
        final int row = 1;
        final int col = 1;

        ValueAtPos predefinedValue = new ValueAtPos(value, row, col);

        connectAllChannelsToRegion();

        // When
        region.setPredefinedValue(predefinedValue);

        // Then
        verifyChannelContainsValue(eastOut, predefinedValue);
        verifyChannelContainsValue(nordOut, predefinedValue);
        verifyChannelContainsValue(southOut, predefinedValue);
        verifyChannelContainsValue(westOut, predefinedValue);

    }

    private void connectAllChannelsToRegion() {
        region.setWestIn(westIn.in());
        region.setWestOut(westOut.out());
        region.setEastIn(eastIn.in());
        region.setEastOut(eastOut.out());
        region.setSouthIn(southIn.in());
        region.setSouthOut(southOut.out());
        region.setNordIn(nordIn.in());
        region.setNordOut(nordOut.out());
    }

    /**
     * Helper method to verify if a value is available on a channel
     *
     * The method asserts that there is a value available, reads the value and asserts that it equals the value it gets as parameter.
     */
    private void verifyChannelContainsValue(final One2OneChannel<ValueAtPos> channel, final ValueAtPos ... predefinedValues) {
        for (ValueAtPos predefinedValue : predefinedValues) {
            //There is a value on the channel
            assertTrue("Expecting value "+predefinedValue.getValue() + " pos (" + predefinedValue.getRow() + "," + predefinedValue.getCol() + ")",channel.in().pending());
            // Get the value
            ValueAtPos valueAtRight = channel.in().read();
            // Compare the value with the predefined value
            assertEquals(predefinedValue, valueAtRight);
        }
    }

    @Test
    public void WhenValueIsReceivedWest_ValueIshandedToEast() {

        connectAllChannelsToRegion();

        westIn.out().write(valueSend);

        region.run();

        verifyChannelContainsValue(eastOut, valueSend);
    }

    @Test
    public void WhenValueIsReceivedEast_ValueIshandedToWest() {

        connectAllChannelsToRegion();

        eastIn.out().write(valueSend);

        region.run();

        verifyChannelContainsValue(westOut, valueSend);
    }

    @Test
    public void WhenValueIsReceivedNord_ValueIshandedToSouth() {

        connectAllChannelsToRegion();

        nordIn.out().write(valueSend);

        region.run();

        verifyChannelContainsValue(southOut, valueSend);
    }

    @Test
    public void WhenValueIsReceivedSouth_ValueIshandedToNord() {

        connectAllChannelsToRegion();

        southIn.out().write(valueSend);

        region.run();

        verifyChannelContainsValue(nordOut, valueSend);
    }

    @Test
    public void WhenNoRightChannelIsSet_ThenNoException() {
        final ValueAtPos valueSend = new ValueAtPos(5, 2, 2);
        westIn.out().write(valueSend);
        region.setWestIn(westIn.in());

        region.run();
    }


}