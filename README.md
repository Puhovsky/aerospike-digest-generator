# Aerospike Digest Generator

This tool accepts a CSV with Primary Keys and writes a CSV with Primary Keys, and it's corresponding Digest back. 

## Usage

```
java -jar aerospike-digest-generator.jar <input_file_path> <output_file_path> <set_name>
```

Input CSV file should contain rows with Primary Keys, each on new line, without header.

Output CSV file will contain rows with Primary Key, Base64 Encoded Digest, Hex Encoded Digest on each row, separated by comma and without header.

## Important notes

- Currently, the Digests are generated for String key type only, so if you need to use other you have to modify the code.
