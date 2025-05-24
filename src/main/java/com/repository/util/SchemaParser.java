package com.repository.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for parsing SQL DDL statements from a resource file on the classpath.
 */
public class SchemaParser {

    /**
     * Loads and parses SQL DDL statements from a resource file located on the classpath.
     *
     * @param resourcePath path to the .sql file in the classpath (e.g. "de/hshn/mi/pdbg/util/schema.sql")
     * @return list of complete SQL statements
     * @throws IOException if the file is not found or cannot be read
     */
    public static List<String> parseSchema(String resourcePath) throws IOException {
        InputStream in = SchemaParser.class.getClassLoader().getResourceAsStream(resourcePath);
        if (in == null) {
            throw new IOException("DDL file not found in classpath: " + resourcePath);
        }

        List<String> statements = new ArrayList<>();
        StringBuilder sb = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("--")) {
                    continue;
                }
                sb.append(line).append(" ");
                if (line.endsWith(";")) {
                    statements.add(sb.toString().trim());
                    sb.setLength(0);
                }
            }
        }

        return statements;
    }
}
