# mambu-attachment-importer

Extracts first 3 documents from Mambu Core Banking for clients that have loan based on the input list and stores them in local folder.

## Usage

To run tool, generate jar:

```bash
mvn package -pl mambu-attachment-importer 
```

and pass configuration files through command line arguments:

```bash
java -jar <generated_jar> <mambu-url> <mambu-login> <mambu-password> <base-directory> <loan-identifiers-file-name>
```

where
* mambu-url - link to mambu instance
* mambu-login - login of user that has API access to Mambu
* mambu-password - password of user
* base-directory - directory where files will be stored
* loan-identifiers-file-name - list of loans for which documents should be downloanded
