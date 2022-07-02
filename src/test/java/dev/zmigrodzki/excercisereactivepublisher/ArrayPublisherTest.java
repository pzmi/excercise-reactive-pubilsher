package dev.zmigrodzki.excercisereactivepublisher;

import org.reactivestreams.Publisher;
import org.reactivestreams.tck.PublisherVerification;
import org.reactivestreams.tck.TestEnvironment;
import org.testng.annotations.Test;

import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;

public class ArrayPublisherTest extends PublisherVerification<Long> {

    public ArrayPublisherTest() {
        super(new TestEnvironment());
    }

    @Override
    public Publisher<Long> createPublisher(long elements) {
        long[] longs = LongStream.iterate(0, l -> l + 1)
                .limit(10000)
                .limit(elements)
                .toArray();
        return new ArrayPublisher(longs);
    }

    @Override
    public Publisher<Long> createFailedPublisher() {
        return new ArrayPublisher(null);
    }

    @Test
    public void nanananananaBatman() {
        assertThat(true).isTrue();
    }
}
