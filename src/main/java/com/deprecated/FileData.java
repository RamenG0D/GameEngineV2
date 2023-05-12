package com.deprecated;

import java.util.HashMap;

public class FileData {
    private HashMap<String, Data<?>> map = new HashMap<>();
    private String name, extension, path;

    public FileData(String name, String ext, String path, String Data) {
        this.name = name;
        extension = ext;
        this.path = path;

        ParseRawData(Data);
    }

    public String getPath() {
        return path;
    }

    private void ParseRawData(String data) {
        data = data.replace("\n", ":");
        String[] split_data = data.split(":");

        // used to loop over file data and assign it values aswell as put it in the map for ease of use
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
    /** <T> here is the DataType of the thing you want to get back (edit: T is also automatically subbed for something)
     * ex: <code>int a = getData("key")</code> is automatically assumed to be int and will return one
     */
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
 *  which will always contain 2 data points no more, and certainly no less P.S. kinda like a tuple from C# not python (python->hissssssss)
 */
