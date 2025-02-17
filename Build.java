import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Build {
    public static void main(String[] args) {
        // Check for command-line argument "-p" to show progress bar
        boolean showProgressBar = false;
        for (String arg : args) {
            if (arg.equals("-p")) {
                showProgressBar = true;
                break;
            }
        }

        String[] files = {
            "TraditionalCiphers/CaesarCipher.java",
            "TraditionalCiphers/VigenereCipher.java",
            "TraditionalCiphers/PlayfairCipher.java",
            "TraditionalCiphers/HillCipher.java",
            "ModernCiphers/CipherTools/CipherToolkit.java", 
            "ModernCiphers/DES/DESKeygen.java", 
            "ModernCiphers/DES/DESEncryption.java", 
            "ModernCiphers/RC4/KeyScheduler.java", 
            "ModernCiphers/RC4/PseudoRandomGenerator.java", 
            "ModernCiphers/RC4/RC4Encryption.java",
            "ModernCiphers/RC4/RC4Decryption.java",
            "ModernCiphers/AES/AESKeyExpansion.java",
            "ModernCiphers/AES/AESEncryption.java"
        };

        System.out.println("Starting Compilation...\n");

        int successCount = 0;
        int failureCount = 0;
        int totalFiles = files.length;
        int lastPrintedProgress = 0; // Tracks the last printed progress percentage

        for (int i = 0; i < totalFiles; i++) {
            String file = files[i];

            try {
                ProcessBuilder builder = new ProcessBuilder("javac", file);
                Process process = builder.start();

                BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));

                int exitCode = process.waitFor();
                if (exitCode == 0) {
                    System.out.println("[+] Compiled \"" + file + "\" successfully.");
                    successCount++;
                } else {
                    System.out.println("[-] Failed to compile \"" + file + "\" : Error Code " + exitCode);
                    String line;
                    while ((line = errorReader.readLine()) != null) {
                        System.out.println("    > " + line);
                    }
                    failureCount++;
                }
            } catch (IOException | InterruptedException e) {
                System.out.println("[-] Unexpected error while compiling \"" + file + "\": " + e.getMessage());
                failureCount++;
            }

            // Calculate progress and print every 10% if the progress bar flag is set
            if (showProgressBar) {
                int progress = (int) Math.round(((i + 1) * 100.0) / totalFiles);
                if (progress >= lastPrintedProgress + 10) {
                    lastPrintedProgress = progress;
                    printProgressBar(progress);
                }
            }
        }

        System.out.println("\nCompilation Summary:");
        System.out.println("✔ " + successCount + " files compiled successfully.");
        System.out.println("❌ " + failureCount + " files failed to compile.");
    }

    private static void printProgressBar(int progress) {
        int barLength = 30;
        int filled = (progress * barLength) / 100;
        System.out.print("\r[" + "=".repeat(filled) + " ".repeat(barLength - filled) + "] " + progress + "%\n");
    }
}
