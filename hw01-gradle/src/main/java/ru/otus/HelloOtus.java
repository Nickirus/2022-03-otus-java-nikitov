package ru.otus;

import com.google.common.base.Charsets;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

/** @noinspection UnstableApiUsage*/
public class HelloOtus {
    public static void main(String[] args) {
        printHashes("Nikitov");
    }

    private static void printHashes(String input) {
        System.out.println("Input value: " + input + "\n");
        printHash(input, Hashing.md5(), "MD5");
        printHash(input, Hashing.sha256(), "SHA256");
        printHash(input, Hashing.sha512(), "SHA512");
    }

    private static void printHash(String input, HashFunction hashType, String hashTypeName) {
        HashCode hashCode = hashType.newHasher()
                .putString(input, Charsets.UTF_8)
                .hash();
        System.out.println(hashTypeName + ": " + hashCode);
    }
}
