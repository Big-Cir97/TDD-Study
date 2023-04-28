package junitdemo;

import java.util.List;

public class DemoUtils {

    private String[] alpabet = {"A", "B", "C"};

    public String[] getAlpabet() {
        return alpabet;
    }

    public List<String> strList = List.of("framework", "junit", "test");

    public List<String> getStrList() {
        return strList;
    }

    public int add(int a, int b) {
        return a + b;
    }

    public Object checkNull(Object obj) {
        if (obj != null) {
            return obj;
        }
        return null;
    }

    public void checkTimeout(long mills) throws InterruptedException {
        System.out.println("start Sleep");
        Thread.sleep(mills * 1000);
        System.out.println("Sleeping over");
    }

    public String notUserCover() {
        return "";
    }
}
