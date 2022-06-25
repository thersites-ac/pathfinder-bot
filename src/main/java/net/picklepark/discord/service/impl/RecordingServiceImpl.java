package net.picklepark.discord.service.impl;

import net.dv8tion.jda.api.audio.CombinedAudio;
import net.dv8tion.jda.api.audio.UserAudio;
import net.picklepark.discord.audio.DiscontinuousAudioArray;
import net.picklepark.discord.exception.NotRecordingException;
import net.picklepark.discord.service.RecordingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

// fixme (important): TDD to ensure that this distinguishes channels
@Singleton
public class RecordingServiceImpl implements RecordingService {

    public static final int PACKETS_PER_SECOND = 50;

    public final int clipDuration;
    public final int packetsPerClip;

    private LinkedList<byte[]> combined;
    private ConcurrentHashMap<Long, DiscontinuousAudioArray> userRecordings;

    private boolean recording = false;
    private final Logger logger = LoggerFactory.getLogger(RecordingServiceImpl.class);

    @Inject
    public RecordingServiceImpl(@Named("recording.clip.duration") int clipDuration) {
        this.clipDuration = clipDuration;
        this.packetsPerClip = PACKETS_PER_SECOND * clipDuration;
    }

    @Override
    public void beginRecording() {
        recording = true;
        combined = new LinkedList<>();
        userRecordings = new ConcurrentHashMap<>();
    }

    @Override
    public byte[] getUser(long user) throws NotRecordingException {
        if (!recording)
            throw new NotRecordingException();
        if (userRecordings.containsKey(user))
            return userRecordings.get(user).retrieve();
        else
            return new byte[0];
    }

    // fixme: unwrap UserAudio in the handler that talks to this
    @Override
    public void receive(UserAudio userAudio) throws NotRecordingException {
        if (!recording)
            throw new NotRecordingException();
        long id = userAudio.getUser().getIdLong();
        userRecordings.computeIfAbsent(id, meh -> new DiscontinuousAudioArray());
        userRecordings.get(id).store(userAudio.getAudioData(1));
    }

    // fixme: unwrap CombinedAudio as above
    @Override
    public void receive(CombinedAudio audio) throws NotRecordingException {
        if (!recording)
            throw new NotRecordingException();
        byte[] audioData = audio.getAudioData(1);
        combined.addLast(audioData);
        if (combined.size() > packetsPerClip)
            combined.removeFirst();
    }

    @Override
    public boolean isRecording() {
        return recording;
    }

    @Override
    public void stopRecording() {
        recording = true;
        userRecordings = null;
        combined = null;
    }

    @Override
    public byte[] getCombined() throws NotRecordingException {
        if (!recording)
            throw new NotRecordingException();
        int len = 0;
        for (byte[] data: combined)
            len += data.length;
        byte[] all = new byte[len];
        int ptr = 0;
        for (byte[] data: combined) {
            System.arraycopy(data, 0, all, ptr, data.length);
            ptr += data.length;
        }
        return all;
    }

}
