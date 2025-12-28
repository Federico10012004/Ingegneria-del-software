package it.calcettohub.utils;

import java.util.ArrayDeque;
import java.util.Deque;

public class PageManager {

    private static final Deque<Runnable> pageStack = new ArrayDeque<>();

    private PageManager() {}

    public static void push(Runnable page) {
        pageStack.push(page);
        page.run();
    }

    public static void pop() {
        if (!pageStack.isEmpty()) {
            pageStack.pop();
        }
        // Se lo stack non è vuoto esegui la pagina subito sotto la pop appena fatta
        if (!pageStack.isEmpty()) {
            pageStack.peek().run();
        }
    }

    public static void pushSilent(Runnable page) {
        pageStack.push(page);
    }

    public static void clear() {
        pageStack.clear();
    }
}
