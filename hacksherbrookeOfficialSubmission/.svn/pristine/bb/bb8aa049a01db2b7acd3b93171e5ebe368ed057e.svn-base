package backend.dataReader;

import android.nfc.Tag;
import android.util.Log;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by Gregory on 2016-04-23.
 */
public class GetResponse {
    private static final String TAG = "";

    public class WebComputationFuture implements Future<String> {

        private volatile String result = null;
        private volatile boolean cancelled = false;
        private final CountDownLatch countDownLatch;

        public WebComputationFuture() {
            countDownLatch = new CountDownLatch(1);
        }

        @Override
        public boolean cancel(final boolean mayInterruptIfRunning) {
            if (isDone()) {
                return false;
            } else {
                countDownLatch.countDown();
                cancelled = true;
                return !isDone();
            }
        }

        @Override
        public String get() throws InterruptedException, ExecutionException {
            countDownLatch.await();
            return result;
        }

        @Override
        public String get(final long timeout, final TimeUnit unit)
                throws InterruptedException, ExecutionException, TimeoutException {
            countDownLatch.await(timeout, unit);
            return result;
        }

        @Override
        public boolean isCancelled() {
            return cancelled;
        }

        @Override
        public boolean isDone() {
            return countDownLatch.getCount() == 0;
        }

        public void onWebResult(final String result) {
            this.result = result;
            Log.d(TAG, "hereLast");
            countDownLatch.countDown();
        }

    }

    public WebComputationFuture asyncGet() {
        WebComputationFuture future = new WebComputationFuture();
        //WebServiceReader.setFuture(future);
        //WebServiceReader.getStringFromURL(null);
        return future;
    }

    public String asyncGetAndBlock() {
        try {
            //Log.d(TAG, "here1");
            return asyncGet().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            return null;
        }
    }
}
