package com.etz.javac;
import java.util.Date;
import static java.lang.System.*;
public class HelloJavacPlugin {
    Object obj;
    public static void hello2(Object p) {
        out.println("hello");
    }
    public void hello(Object p) {
        out.println("hello");
    }

    public void world(String[] args,Date date) {
        com.etz.javac.Bonder bonder1;
        bonder1 = new com.etz.javac.Bonder();
        hello(1);
        new HelloJavacPlugin().hello(1);
        this.hello(1);
        hello2(1);
        obj.toString();
        currentTimeMillis();
    }
}
