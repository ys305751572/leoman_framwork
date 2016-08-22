package com.leoman.resource;

/**
 * Created by Administrator on 2016/7/27 0027.
 */
import com.mchange.v1.cachedstore.CachedStore;

import javax.media.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlayerMusic implements ControllerListener {

    // ControllerListener
    // 控制事件
    private Player player;

    private boolean first, loop;

    private String path;
    private List mp3List;
    private int mp3NO = 0;

    public PlayerMusic(List mp3List) {
        this.mp3List = mp3List;
    }

    public void start() {
        try {
            player = Manager.createPlayer(new File((String) mp3List.get(mp3NO)).toURI().toURL());
            //player = Manager.createPlayer(new MediaLocator("file://" + mp3List.get(mp3NO)));
        } catch (NoPlayerException ex) {
            ex.printStackTrace();
            System.out.println("不能播放文件");
            return;
        } catch (IOException ex) {
            ex.printStackTrace();
            return;
        }
        if (player == null) {
            System.out.println("播放器为空");
            return;
        }

        first = false;
        player.addControllerListener(this);
        // 提取媒体内容
        player.prefetch();

    }

    public void controllerUpdate(ControllerEvent e) {
        // 当媒体播放结束时,循环播放
        if (e instanceof EndOfMediaEvent) {
            mp3NO++;
            if (mp3NO < this.mp3List.size()) {
                this.start();
            }
            return;
        }

        // 当预提取媒体的内容结束
        if (e instanceof PrefetchCompleteEvent) {
            player.start();
            return;
        }
        // 当实例化后
        if (e instanceof RealizeCompleteEvent) {
            // pack(); //执行pack()操作
            return;
        }

    }

    public static void main(String[] args) {
        List mp3List = new ArrayList();
        mp3List.add("F:\\music\\G.E.M. 邓紫棋 - 泡沫.mp3");
        PlayerMusic pm = new PlayerMusic(mp3List);
        pm.start();
    }
}

