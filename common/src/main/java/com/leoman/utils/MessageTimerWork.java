package com.leoman.utils;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2016/3/10.
 */
public class MessageTimerWork {

    private Timer timer = null;


    public MessageTimerWork(long time) {
        timer = new Timer();
        timer.schedule(new Mywork(), new Date(time));
    }

    class Mywork extends TimerTask {
        @Override
        public void run() {
            try {
                doExecute();
                timer.cancel();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void doExecute() {

    }
}
