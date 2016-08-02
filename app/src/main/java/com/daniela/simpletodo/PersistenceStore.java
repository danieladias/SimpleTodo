package com.daniela.simpletodo;

import android.content.Context;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Persistence Store handles the storage and retrieval of data
 */
public class PersistenceStore {

    private String FILENAME = "todo.txt";

    /**
     * Reads from the application's internal storage the list of items
     * @param context the context
     * @return items the list of items previously stored; empty if none
     */
    public ArrayList<String>readItems(Context context) {

        File filesDirectory = context.getFilesDir();
        File todoFile = new File(filesDirectory, FILENAME);

        ArrayList<String> items = new ArrayList<>();

        try {
            items = new ArrayList<>(FileUtils.readLines(todoFile));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return items;
    }

    /**
     * Writes a list of items to the application's internal storage
     * @param context the context
     * @param items the list of items to store; full list to write
     */
    public void writeItems(Context context, ArrayList<String> items) {

        File filesDirectory = context.getFilesDir();
        File todoFile = new File(filesDirectory, FILENAME);

        try {
            FileUtils.writeLines(todoFile, items);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
