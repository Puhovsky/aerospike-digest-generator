package com.puhovsky.utils;

import com.aerospike.client.Value;
import com.aerospike.client.util.Crypto;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Base64;

public class Main {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.err.println("Usage: java -jar aerospike-digest-generator.jar <input_file_path> <output_file_path> <set_name>");
            System.err.println("Input CSV file should contain rows with Primary Keys, each on new line, without header.");
            System.err.println("Currently the digests are generated for String key type only, so if you need to use other you have to modify the code.");
            System.err.println("Output CSV file will contain rows with Primary Key, Base64 Encoded Digest, Hex Encoded Digest on each row, separated by comma.");
            return;
        }

        String inputFile = args[0],
                outputFile = args[1],
                setName = args[2];

        try (
                BufferedReader br = new BufferedReader(new FileReader(inputFile));
                FileWriter writer = new FileWriter(outputFile);
        ) {
            String primaryKey;
            while ((primaryKey = br.readLine()) != null) {
                var digest = getDigest(setName, primaryKey);
                var eDigest = getBase64EncodedDigest(digest);
                var hexDigest = getHexEncodedDigest(digest);

                writer.write(primaryKey + "," + eDigest + "," + hexDigest + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static byte[] getDigest(String set, String key) {
        return Crypto.computeDigest(set, new Value.StringValue(key));
    }

    public static String getBase64EncodedDigest(byte[] digest) {
        return new String(Base64.getEncoder().encode(digest));
    }

    public static String getHexEncodedDigest(byte[] digest) {
        StringBuilder result = new StringBuilder();
        for (byte aByte : digest) {
            result.append(String.format("%02x", aByte));
        }
        return result.toString();
    }
}