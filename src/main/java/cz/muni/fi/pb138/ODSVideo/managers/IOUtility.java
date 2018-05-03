package cz.muni.fi.pb138.ODSVideo.managers;

import cz.muni.fi.pb138.ODSVideo.models.Category;
import org.odftoolkit.simple.TextDocument;

import java.io.IOException;
import java.util.Map;

/**
 *
 */
public interface IOUtility {

    /**
     * Reads content of .ods file specified in path
     * @throws IOException if goes wrong with file (read, access...)
     * @return content of file in form of TextDocument
     */
    TextDocument readFile(String path) throws IOException;

    /**
     * Writes TextDocument into the .ods file specified in path
     * @throws IllegalArgumentException if any of parameters are null
     */
    void writeFile(String path, TextDocument document);

    /**
     * Transforms .ods document into map of categories and its names as key
     * @param document document to transformed
     * @throws IllegalArgumentException if document is null
     * @return map of categories containing data from document
     */
    Map<String,Category> transformToMap(TextDocument document);

    /**
     * Transforms map of categories to TextDocument format
     * @param categoryMap map to be transformed
     * @throws IllegalArgumentException if map is null
     * @return TextDocument containg data from map
     */
    TextDocument transformToDocument(Map<String,Category> categoryMap);

}
