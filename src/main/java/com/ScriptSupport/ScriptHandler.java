package com.ScriptSupport;

import java.util.ArrayList;

public class ScriptHandler {
    private ArrayList<Script> scripts = new ArrayList<>();
    private static ScriptHandler instance = null;

    public synchronized void runScripts() {
        for(Script script : scripts) {
            script.run();
        }
    }

    public static ScriptHandler getInstance() {
        if(instance == null) instance = new ScriptHandler();
        return instance;
    }

    private ScriptHandler() {}

    public synchronized void addScript(Script s) {
        scripts.add(s);
    }

    public synchronized void removeScript(int i) {
        scripts.remove(i);
    }
}
