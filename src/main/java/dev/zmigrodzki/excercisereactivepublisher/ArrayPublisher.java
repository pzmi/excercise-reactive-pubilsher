package dev.zmigrodzki.excercisereactivepublisher;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class ArrayPublisher implements Publisher<Long> {
    private final long[] longs;

    public ArrayPublisher(long[] longs) {
        this.longs = longs;
    }

    @Override
    public void subscribe(Subscriber<? super Long> subscriber) {
        if (longs == null) {
            subscriber.onSubscribe(new ArraySubscription(subscriber, new long[]{}));
            subscriber.onError(new IllegalStateException("Publisher initialized in invalid state"));
            return;
        }

        if (longs.length == 0) {
            ArraySubscription s = new ArraySubscription(subscriber, longs);
            s.cancel();
            subscriber.onSubscribe(s);
            subscriber.onComplete();
        } else {
            subscriber.onSubscribe(new ArraySubscription(subscriber, longs));
        }
    }

    private static class ArraySubscription implements Subscription {
        private final long[] longs;
        private final Subscriber<? super Long> subscriber;

        private int index;
        private boolean inProgress;
        private boolean canceled;
        private long demand;

        public ArraySubscription(Subscriber<? super Long> subscriber, long[] longs) {
            this.subscriber = subscriber;
            this.longs = longs;
        }

        @Override
        public void request(long n) {
            if (canceled) {
                return;
            }

            if (n <= 0) {
                var error = new IllegalArgumentException("Breaks 3.9 of reactive streams specification - non-positive subscription request");
                error(error);
                return;
            }

            if (demand + n < 0) {
                demand = Long.MAX_VALUE;
            } else {
                demand += n;
            }

            if (inProgress) {
                return;
            }

            inProgress = true;

            if (demand == Long.MAX_VALUE) {
                while(index < longs.length) {
                    subscriber.onNext(longs[index]);
                    index++;
                }
                complete();
                return;
            }

            while (demand > 0) {
                subscriber.onNext(longs[index]);
                demand--;
                index++;
                if (index == longs.length) {
                    complete();
                    return;
                }
            }

            inProgress = false;
        }

        private void error(IllegalArgumentException error) {
            subscriber.onError(error);
            cancel();
        }

        private void complete() {
            subscriber.onComplete();
            cancel();
        }

        @Override
        public void cancel() {
            canceled = true;
        }
    }
}
