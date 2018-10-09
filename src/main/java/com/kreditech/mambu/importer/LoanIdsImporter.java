package com.kreditech.mambu.importer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class LoanIdsImporter {

    public List<String> getLoanIdentfiers(String fileName) throws IOException {

        Path filePath = Paths.get(fileName);
        List<String> loanIdentfiers = Files.readAllLines(filePath);

        return loanIdentfiers;
    }
}
