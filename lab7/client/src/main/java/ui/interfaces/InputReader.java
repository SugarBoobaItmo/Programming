package ui.interfaces;

import java.util.Queue;

import cli.interfaces.LineReader;

public class InputReader implements LineReader{
    Queue<String> queue;

    public InputReader(Queue<String> queue) {
        this.queue = queue;
    }

    @Override
    public String readLine() {
        return queue.poll();

    }
    
}
