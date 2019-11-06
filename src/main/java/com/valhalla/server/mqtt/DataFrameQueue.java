package com.valhalla.server.mqtt;

import com.valhalla.server.datapackage.DataFrame;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Valhalla
 * @description
 * @date 2019/11/4
 **/
public class DataFrameQueue {
    private Queue<DataFrame> quneue = new LinkedList<DataFrame>();

    public synchronized boolean offer(DataFrame dataFrame) {
        return quneue.offer(dataFrame);
    }

    public synchronized DataFrame poll() {
        return quneue.poll();
    }

    public synchronized int size() {
        return quneue.size();
    }
}
