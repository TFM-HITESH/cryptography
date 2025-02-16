package ModernCiphers;
import ModernCiphers.DES.DESEncryption;
import ModernCiphers.DES.DESKeygen;

public class Test {
    public static void main(String[] args) {

        String key = "AABB 0918 2736 CCDD";
        DESKeygen keys = new DESKeygen();
        keys.DESKeyGeneration(key);

        String plainText = "1234 56AB CD13 2536";
        DESEncryption encryption = new DESEncryption();
        encryption.DESEncryptionProcess(plainText, keys);
    }
}
