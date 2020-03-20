package com.etz.javac;

import com.sun.source.util.TaskEvent;
import com.sun.source.util.TaskListener;

public class OptimizationTaskListener implements TaskListener {
    @Override
    public void started(TaskEvent e) {

    }


    @Override
    public void finished(TaskEvent e) {
        if (e.getKind() == TaskEvent.Kind.PARSE){
            // TODO: optimize!
            System.out.println("Task event " + e + " has ended");

        }
    }
}
