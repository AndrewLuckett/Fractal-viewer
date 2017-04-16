package core;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import core.taskables.TileTaskable;
import main.Debuglog;

public class JobTasker {

    public Map<Point, Future<BufferedImage>> tasks = new HashMap<Point, Future<BufferedImage>>();

    ExecutorService pool;
    Class<? extends TileTaskable> task;

    public JobTasker(Class<? extends TileTaskable> task) {
        this.task = task;
        int cores = Runtime.getRuntime().availableProcessors();
        pool = Executors.newFixedThreadPool(Math.max(cores - 1, 1));
    }

    public void addJob(Point id, double pixelwidth) {
        tasks.put(id,
            pool.submit(new Callable<BufferedImage>() {

                @Override
                public BufferedImage call() throws Exception {
                    TileTaskable temp2 = task.newInstance();
                    Debuglog.log("Job started: " + id, 1);
                    return temp2.doTile(id, pixelwidth);
                }
            }));
    }

}
