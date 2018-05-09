package cz.muni.fi.pb138.ODSVideo.managers;

import cz.muni.fi.pb138.ODSVideo.models.Category;
import org.odftoolkit.simple.SpreadsheetDocument;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class IOUtilityImpl implements IOUtility{
    @Override
    public SpreadsheetDocument readFile(String path) throws IOException {
        if (path == null) {
            throw new IllegalArgumentException("arguments cannot be null");
        }
        return readFile(new File(path));
    }

    @Override
    public SpreadsheetDocument readFile(File file) throws IOException {
        if (file == null) {
            throw new IllegalArgumentException("arguments cannot be null");
        }

        SpreadsheetDocument document;
        try {
            document = SpreadsheetDocument.loadDocument(file);
        } catch (Exception e) {
            throw new IOException("failed to open document",e);
        }
        return document;
    }

    @Override
    public void writeFile(String path, SpreadsheetDocument document) throws IOException{
        if (path == null || document == null) {
            throw new IllegalArgumentException("arguments cannot be null");
        }
        writeFile(new File(path), document);
    }

    @Override
    public void writeFile(File file, SpreadsheetDocument document) throws IOException{
        if (file == null || document == null) {
            throw new IllegalArgumentException("arguments cannot be null");
        }

        try {
            document.save(file);
        } catch (Exception e) {
            throw new IOException("failed to write", e);
        }
    }

    @Override
    public Map<String, Category> transformToMap(SpreadsheetDocument document) {
        if (document == null) {
            throw new IllegalArgumentException("argument cannot be null");
        }

        return null;
    }

    @Override
    public SpreadsheetDocument transformToDocument(Map<String, Category> categoryMap) {
        return null;
    }
}
