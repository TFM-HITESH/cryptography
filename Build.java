import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.*;
import java.util.List;
import java.util.ArrayList;

public class Build {
    public static void main(String[] args) {
        boolean showProgressBar = false;
        for (String arg : args) {
            if (arg.equals("-p")) {
                showProgressBar = true;
                break;
            }
        }

        String[] files = {
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
        int lastPrintedProgress = 0;

        // Use ExecutorService to compile files in parallel
        ExecutorService executorService = Executors.newFixedThreadPool(4); // Use 4 threads (can adjust based on system)
        List<Future<Boolean>> results = new ArrayList<>();

        for (String file : files) {
            // Submit compilation task to ExecutorService
            results.add(executorService.submit(() -> compileFile(file)));
        }

        // Wait for all tasks to complete
        for (int i = 0; i < results.size(); i++) {
            try {
                boolean success = results.get(i).get();  // Get the result (blocking until done)
                String file = files[i];

                if (success) {
                    System.out.println("[+] Compiled \"" + file + "\" successfully.");
                    successCount++;
                } else {
                    System.out.println("[-] Failed to compile \"" + file + "\".");
                    failureCount++;
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                failureCount++;
            }

            // Update Progress Bar if flag is set
            if (showProgressBar) {
                int progress = (int) Math.round(((i + 1) * 100.0) / totalFiles);
                if (progress >= lastPrintedProgress + 10) {
                    lastPrintedProgress = progress;
                    printProgressBar(progress);
                }
            }
        }

        executorService.shutdown(); // Shutdown the ExecutorService

        System.out.println("\nCompilation Summary:");
        System.out.println("✔ " + successCount + " files compiled successfully.");
        System.out.println("❌ " + failureCount + " files failed to compile.");
    }

    private static boolean compileFile(String file) {
        try {
            ProcessBuilder builder = new ProcessBuilder("javac", file);
            Process process = builder.start();
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                return true;
            } else {
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                String line;
                while ((line = errorReader.readLine()) != null) {
                    System.out.println("    > " + line);
                }
                return false;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static void printProgressBar(int progress) {
        int barLength = 30;
        int filled = (progress * barLength) / 100;
        System.out.print("\r[" + "=".repeat(filled) + " ".repeat(barLength - filled) + "] " + progress + "%\n");
    }
}
