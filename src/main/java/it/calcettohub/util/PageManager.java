package it.calcettohub.util;

import java.util.Stack;

public class PageManager {

    private static final Stack<Runnable> pageStack = new Stack<>();

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

    public static void clear() {
        pageStack.clear();
    }
}
