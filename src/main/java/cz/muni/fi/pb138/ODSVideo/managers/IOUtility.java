package cz.muni.fi.pb138.ODSVideo.managers;

import cz.muni.fi.pb138.ODSVideo.models.Category;
import org.odftoolkit.simple.SpreadsheetDocument;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 *
 */
public interface IOUtility {

    /**
     * Reads content of .ods file specified in path
     * @throws IOException if goes wrong with file (read, access...)
     * @throws IllegalArgumentException if any of parameters are null
     * @return content of file in form of SpreadsheetDocument
     */
    SpreadsheetDocument readFile(String path) throws IOException;


    /**
     * Reads content of .ods file specified in path
     * @throws IOException if goes wrong with file (read, access...)
     * @throws IllegalArgumentException if any of parameters are null
     * @return content of file in form of SpreadsheetDocument
     */
    SpreadsheetDocument readFile(File file) throws IOException;


    /**
     * Writes SpreadsheetDocument into the .ods file specified in path
     * @throws IOException if write operation fails
     * @throws IllegalArgumentException if any of parameters are null
     */
    void writeFile(String path, SpreadsheetDocument document) throws IOException;

    /**
     * Writes SpreadsheetDocument into the .ods file specified in path
     * @throws IOException if write operation fails
     * @throws IllegalArgumentException if any of parameters are null
     */
    void writeFile(File file, SpreadsheetDocument document) throws IOException;

    /**
     * Transforms .ods document into map of categories and its names as key
     * @param document document to transformed
     * @throws IllegalArgumentException if document is null
     * @return map of categories containing data from document
     */
    Map<String,Category> transformToMap(SpreadsheetDocument document);

    /**
     * Transforms map of categories to SpreadsheetDocument format
     * @param categoryMap map to be transformed
     * @throws IllegalArgumentException if map is null
     * @return SpreadsheetDocument containg data from map
     */
    SpreadsheetDocument transformToDocument(Map<String,Category> categoryMap);

}
