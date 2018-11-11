package com.kreditech.mambu.importer.configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class LoanIdsListImporter {

    public List<String> getLoanIdentfiers(String fileName) throws IOException {

        Path filePath = Paths.get(fileName);

        return Files.readAllLines(filePath);
    }
}
