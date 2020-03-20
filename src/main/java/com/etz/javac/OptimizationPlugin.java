package com.etz.javac;

import com.sun.source.util.JavacTask;
import com.sun.source.util.Plugin;
import com.sun.source.util.TaskEvent;
import com.sun.source.util.TaskListener;

public class OptimizationPlugin implements Plugin {
    @Override
    public String getName() {
        return "OptimizationPlugin";
    }

    @Override
    public void init(JavacTask javacTask, String... strings) {
        javacTask.addTaskListener(new TaskListener() {
            @Override
            public void started(TaskEvent e) {

            }

            @Override
            public void finished(TaskEvent e) {
                if (e.getKind() == TaskEvent.Kind.ENTER ){
                    BonderGenerator bonderGenerator = new BonderGenerator(javacTask);
                    bonderGenerator.addTo(e.getCompilationUnit());

                }

            }
        });
    }
}
