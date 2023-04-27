package com.Resources;

import java.util.HashMap;

public class FileData {
    private HashMap<String, Data<?>> map = new HashMap<>();
    private String name, extension;

    public FileData(String name, String ext, String Data) {
        this.name = name;
        extension = ext;

        ParseRawData(Data);
    }

    private void ParseRawData(String data) {
        data = data.replace("\n", ":");
        String[] split_data = data.split(":");

        for(int i = 0; i < split_data.length; i++) {
            assignIntOrString(i, split_data);
        }
    }

    private void assignIntOrString(int i, String[] split) {
        if(i % 2 == 0) {
            Data<?> d;
            try {
                d = new Data<Integer>(Integer.parseInt(split[i+1]));
            } catch(NumberFormatException e) {
                d = new Data<String>(split[i+1]);
            }
            map.put(split[i], d);
        } else return;
    }

    public String getName() {
        return name;
    }

    public String getExtension() {
        return extension;
    }

    @SuppressWarnings("unchecked")
    /** <T> here is the DataType of the thing you want to get back */
    public <T> T getData(String key) {
        return (T) map.get(key).data;
    }

    public class Data<T> {
        protected T data;
        //
        public Data(T dataToStore) {
            data = dataToStore;
        }
        //
    }
}

/*
 * Data keys & their meaning
 * 
 *  "{}" this indicates the values inbetween are related and are to be stored in a 
 *   list or something then the list is added to the map
 * 
 *  "()" this indicates a small list of only 2 dataPoints 
 *  (TODO make a custom exception for if only one data point is found)
 *  which will always contain 2 data points no more, and certainly no less
 */
