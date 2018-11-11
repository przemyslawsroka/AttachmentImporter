package com.kreditech.mambu.importer;

import com.kreditech.mambu.importer.service.MambuAttachmentService;
import com.kreditech.mambu.importer.configuration.LoanIdsListImporter;
import com.kreditech.mambu.importer.model.MambuAttachment;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Extracts documents from Mambu for clients that have loan based on the input list.
 *
 * Extracted documents have the following structure:
 * [Folder] LoanId (e.g. PLN13582R11)
 *  - aadhaar-card-back.JPG
 *  - aadhaar-card-front.JPG
 *  - pan-or-voter-id.JPG
 */
public class Main {

    private static String mambuUri;
    private static String login;
    private static String password;

    private static String baseDirectory;
    private static String loanIdentifiersFile;

    private static int attachmentLimitPerObject = Integer.MAX_VALUE;

    public static void main(String[] args) {

        if (args.length < 5)
        {
            System.out.println("Usage: java AttachmentImporter <mambu-url> <mambu-login> <mambu-password> <base-directory> <loan-identifiers-file-name> [limit]");
            System.exit(1);
        }
        mambuUri = args[0];
        login = args[1];
        password = args[2];
        baseDirectory = args[3];
        loanIdentifiersFile = args[4];

        if (args.length == 6)
        {
            attachmentLimitPerObject = Integer.parseInt(args[5]);
        }

        LoanIdsListImporter loanImporter = new LoanIdsListImporter();

        try {
            System.out.println("Getting list of loans.");
            List<String> loanIdentfiers = loanImporter.getLoanIdentfiers(baseDirectory + loanIdentifiersFile);
            System.out.println("Found: " + loanIdentfiers.size() + " loans.");

            MambuAttachmentService attachmentService = new MambuAttachmentService(mambuUri, login, password);

            int counter = 1;
            for (String loanIdentifier : loanIdentfiers) {
                System.out.print("Processing loan: " + loanIdentifier + " (" + counter++ + " out of " + loanIdentfiers.size() + ")");

                String clientKey = attachmentService.getClientKeyForLoan(loanIdentifier);
                System.out.println(" clientKey: " + clientKey);

                List<MambuAttachment> documents = attachmentService.getDocumentsForClient(clientKey, attachmentLimitPerObject);

                for (MambuAttachment document : documents) {
                    byte[] content = attachmentService.getDocumentContent(document.getId());
                    saveDocument(loanIdentifier, document, content);
                }
            }
            System.out.println("Processed all loans");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves document with given content on a disc. It uses loan identifier and document metadata in order to determine
     * location of document that is being saved.
     *
     * @param loanIdentifier - identifier of a loan which determines in which folder document should be saved
     * @param document - document metadata
     * @param documentContent - document binary content
     * @throws IOException - cannot access file
     */
    private static void saveDocument(String loanIdentifier, MambuAttachment document, byte[] documentContent) throws IOException {

        String directoryName = baseDirectory + "\\documents\\" + loanIdentifier;
        File directory = new File(String.valueOf(directoryName));

        if(!directory.exists()){
            boolean mkdir = directory.mkdir();
            if (!mkdir) {
                throw new RuntimeException("Cannot create directory: " + directory.getName());
            }
        }

        String fileName = directoryName + "\\" + document.getName();
        Path path = Paths.get(fileName);
        Files.write(path, documentContent);
    }
}
