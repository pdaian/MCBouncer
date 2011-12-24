package com.mcbouncer.util.node;

import com.mcbouncer.exception.ConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.SafeConstructor;
import org.yaml.snakeyaml.reader.UnicodeReader;

/**
 * YAML file loader, as an extension of MapNode.
 * After passing a YAML file and load()ing it, the
 * file can be accessed with standard MapNode methods.
 * 
 * @author sk89q 
 */
public class YAMLFileNode extends MapNode {

    private Yaml yaml;
    private File file;
    private String header = null;

    public YAMLFileNode(File file) {
        super(new HashMap<String, Object>());

        DumperOptions options = new DumperOptions();

        options.setIndent(4);
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);

        yaml = new Yaml(new SafeConstructor(), new EmptyNullRepresenter(), options);

        this.file = file;
    }

    /**
     * Loads the configuration file. All errors are thrown away.
     */
    public void load() {
        FileInputStream stream = null;

        try {
            stream = new FileInputStream(file);
            read(yaml.load(new UnicodeReader(stream)));
        } catch (IOException e) {
            base = new HashMap<String, Object>();
        } catch (ConfigurationException e) {
            base = new HashMap<String, Object>();
        } finally {
            try {
                if (stream != null) {
                    stream.close();
                }
            } catch (IOException e) {
            }
        }
    }

    /**
     * Set the header for the file as a series of lines that are terminated
     * by a new line sequence.
     *
     * @param headerLines header lines to prepend
     */
    public void setHeader(String... headerLines) {
        StringBuilder headerTemp = new StringBuilder();

        for (String line : headerLines) {
            if (headerTemp.length() > 0) {
                headerTemp.append("\r\n");
            }
            headerTemp.append(line);
        }

        setHeader(headerTemp.toString());
    }

    /**
     * Set the header for the file. A header can be provided to prepend the
     * YAML data output on configuration save. The header is
     * printed raw and so must be manually commented if used. A new line will
     * be appended after the header, however, if a header is provided.
     *
     * @param header header to prepend
     */
    public void setHeader(String header) {
        this.header = header;
    }

    /**
     * Return the set header.
     *
     * @return
     */
    public String getHeader() {
        return header;
    }

    /**
     * Saves the configuration to disk. All errors are clobbered.
     *
     * @param header header to prepend
     * @return true if it was successful
     */
    public boolean save() {
        FileOutputStream stream = null;

        File parent = file.getParentFile();

        if (parent != null) {
            parent.mkdirs();
        }

        try {
            stream = new FileOutputStream(file);
            OutputStreamWriter writer = new OutputStreamWriter(stream, "UTF-8");
            if (header != null) {
                writer.append(header);
                writer.append("\r\n");
            }
            yaml.dump(base, writer);
            return true;
        } catch (IOException e) {
        } finally {
            try {
                if (stream != null) {
                    stream.close();
                }
            } catch (IOException e) {
            }
        }

        return false;
    }

    @SuppressWarnings("unchecked")
    private void read(Object input) throws ConfigurationException {
        try {
            if (null == input) {
                base = new HashMap<String, Object>();
            } else {
                base = (Map<String, Object>) input;
            }
        } catch (ClassCastException e) {
            throw new ConfigurationException("Root document must be an key-value structure");
        }
    }

    /**
     * This method returns an empty ConfigurationNode for using as a
     * default in methods that select a node from a node list.
     * @return
     */
    public static MapNode getEmptyNode() {
        return new MapNode(new HashMap<String, Object>());
    }
}
