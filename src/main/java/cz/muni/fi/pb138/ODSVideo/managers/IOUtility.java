package cz.muni.fi.pb138.ODSVideo.managers;

import cz.muni.fi.pb138.ODSVideo.models.Category;
import java.util.Map;

/**
 *
 */
public interface IOManager {

    atribut TextDocument

    /**
     * Reads content of .ods file specified in contructor
     *
     */
    void readFile();

    /**
     * Writes into the .ods file
     */
    void writeFile();

    /**
     * Transforms .ods document into map of categories and its names
     * @return Map of categories
     */
    Map<String,Category> transformToMap();

    TextD
}
