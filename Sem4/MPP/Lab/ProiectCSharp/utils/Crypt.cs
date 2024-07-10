using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Cryptography;
using System.Text;
using System.Threading.Tasks;

namespace utils
{
    public class Crypt
    {
        private const string ALGORITHM = "AES";
        private const string TRANSFORMATION = "AES/ECB/PKCS5Padding";
        private const string SECRET_KEY = "Kds9GJksdi3fnaGR";

        public static string Encrypt(string input)
        {
            try
            {
                using (Aes aesAlg = Aes.Create())
                {
                    aesAlg.Key = Encoding.UTF8.GetBytes(SECRET_KEY);
                    aesAlg.Mode = CipherMode.ECB;
                    aesAlg.Padding = PaddingMode.PKCS7;

                    ICryptoTransform encryptor = aesAlg.CreateEncryptor(aesAlg.Key, aesAlg.IV);
                    byte[] encryptedBytes = encryptor.TransformFinalBlock(Encoding.UTF8.GetBytes(input), 0, input.Length);

                    return Convert.ToBase64String(encryptedBytes);
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
                return null;
            }
        }

        public static string Decrypt(string encryptedInput)
        {
            try
            {
                using (Aes aesAlg = Aes.Create())
                {
                    aesAlg.Key = Encoding.UTF8.GetBytes(SECRET_KEY);
                    aesAlg.Mode = CipherMode.ECB;
                    aesAlg.Padding = PaddingMode.PKCS7;

                    ICryptoTransform decryptor = aesAlg.CreateDecryptor(aesAlg.Key, aesAlg.IV);
                    byte[] decryptedBytes = decryptor.TransformFinalBlock(Convert.FromBase64String(encryptedInput), 0, encryptedInput.Length);

                    return Encoding.UTF8.GetString(decryptedBytes);
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
                return null;
            }
        }
    }
}
