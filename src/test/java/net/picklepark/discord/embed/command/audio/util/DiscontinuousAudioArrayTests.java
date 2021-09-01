package net.picklepark.discord.embed.command.audio.util;

import net.picklepark.discord.command.audio.util.DiscontinuousAudioArray;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;

@RunWith(JUnit4.class)
public class DiscontinuousAudioArrayTests {

    private DiscontinuousAudioArray discontinuousAudioArray;
    private byte[] data;

    @Before
    public void setup() {
        discontinuousAudioArray = new DiscontinuousAudioArray();
        data = new byte[DiscontinuousAudioArray.PACKET_SIZE];
    }

    @Test
    public void storesAudio() {
        whenProvideAudio();
        thenCanRetrieveIt();
    }

    @Test
    public void canSetMaximumRecordingLength() throws InterruptedException {
        givenShortMaxRecordingLength();
        whenInterpolateLongAudio();
        thenResultDropsOldPackets();
    }

    private void givenShortMaxRecordingLength() {
        discontinuousAudioArray = new DiscontinuousAudioArray(40);
    }

    private void whenInterpolateLongAudio() throws InterruptedException {
        whenProvideAudio();
        Thread.sleep(41);
        whenProvideAudio();
    }

    private void whenProvideAudio() {
        discontinuousAudioArray.store(data);
    }

    private void thenResultDropsOldPackets() {
        Assert.assertEquals(DiscontinuousAudioArray.PACKET_SIZE, discontinuousAudioArray.retrieve().length);
    }

    private void thenCanRetrieveIt() {
        Assert.assertArrayEquals(data, discontinuousAudioArray.retrieve());
    }

}
