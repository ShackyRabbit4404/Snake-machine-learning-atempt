public class StatFrameThread implements Runnable {
    StatisticsGraph sg;
    int fps = 25;
    public StatFrameThread(StatisticsGraph sgraph) {
        sg=sgraph;
    }
    public void run() {
        while(true) {
            try {
                Thread.sleep(1000/fps);
            } catch (Exception e) {
                e.printStackTrace();
            }
            sg.redraw();
        }
    }
}