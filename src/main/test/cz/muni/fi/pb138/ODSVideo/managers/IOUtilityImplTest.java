package cz.muni.fi.pb138.ODSVideo.managers;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.*;

class IOUtilityImplTest {
    private IOUtility io = new IOUtilityImpl();

    @Test
    void readFilePathNullParameter() {
        String nullStr = null;
        assertThatThrownBy(() -> io.readFile(nullStr))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void readFileNullParameter() {
        File nullFile = null;
        assertThatThrownBy(() -> io.readFile(nullFile))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void transformToMapNullParameter() {
        assertThatThrownBy(() -> io.transformToSet(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void transformToDocumentNullParameter() {
        assertThatThrownBy(() -> io.transformToDocument(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void transformInvalidFile() {
        assertThatThrownBy(() -> io.transformToSet(io.readFile("target/test-classes/invalid.ods"))).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void transformValidFile() throws IOException {
        assertThat(io.transformToSet(io.readFile("target/test-classes/sample_valid.ods")))
                .isNotNull();
    }
    @Test
    void readValidFile() throws IOException {
        assertThat(io.readFile("target/test-classes/sample_valid.ods"))
                .isNotNull();
    }
    @Test
    void readRandomFile() {
        assertThatThrownBy(() -> io.readFile("target/test-classes/random_file.ods"))
                .isInstanceOf(IOException.class);
    }

    @Test
    void writeFileNullParameter() {
        File nullFile = null;
        assertThatThrownBy(() -> io.writeFile(nullFile,null))
                .isInstanceOf(IllegalArgumentException.class);
    }
    @Test
    void writeFilePathNullParameter() {
        String nullStr = null;
        assertThatThrownBy(() -> io.writeFile(nullStr,null))
                .isInstanceOf(IllegalArgumentException.class);
    }
}