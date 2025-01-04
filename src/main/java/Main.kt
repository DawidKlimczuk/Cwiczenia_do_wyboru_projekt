import java.io.File

fun main() {
    val aesEncryption = AESEncryption()
    val databaseManager = DatabaseManager(aesEncryption)
    val userManager = UserManager(databaseManager, aesEncryption)

    println("===========================================")
    println("      Witamy w systemie szyfrowania AES     ")
    println("===========================================")

    while (true) {
        println("\nWybierz opcję:")
        println("1. Logowanie do systemu")
        println("2. Szyfrowanie pliku")
        println("3. Deszyfrowanie pliku")
        println("4. Wyjście")

        print("Twój wybór: ")
        when (readLine()?.trim()) {
            "1" -> {
                userManager.initializeDatabase()
                println("--- LOGOWANIE ---")

                print("Podaj login: ")
                val username = readLine()?.trim() ?: continue

                print("Podaj hasło: ")
                val password = readLine()?.trim() ?: ""

                val result = if (username.contains("'--")) {
                    userManager.loginVulnerable(username)
                } else {
                    userManager.loginSecure(username, password)
                }

                if (!result.isNullOrEmpty()) {
                    println("✅ $result")
                } else {
                    println("❌ Nie udało się zalogować. Sprawdź dane.")
                }
            }




            "2" -> {
                print("Podaj pełną ścieżkę do pliku wejściowego (np. D:\\test.txt): ")
                val inputFile = readLine()?.trim() ?: continue

                try {
                    val input = File(inputFile)
                    if (!input.exists()) {
                        println("❌ Plik wejściowy nie istnieje.")
                        continue
                    }

                    // Tworzenie nazwy pliku zaszyfrowanego
                    val encryptedFileName = input.nameWithoutExtension + "_zaszyfrowany.dat"
                    val encryptedFile = File(input.parent, encryptedFileName)

                    aesEncryption.encryptFile(input.path, encryptedFile.path)
                    println("✅ Plik został zaszyfrowany: ${encryptedFile.path}")
                } catch (e: Exception) {
                    println("❌ Wystąpił błąd podczas szyfrowania pliku: ${e.message}")
                }
            }


            "3" -> {
                print("Podaj pełną ścieżkę do pliku zaszyfrowanego (np. D:\\test_zaszyfrowany.dat): ")
                val encryptedFile = readLine()?.trim() ?: continue

                try {
                    val input = File(encryptedFile)
                    if (!input.exists()) {
                        println("❌ Plik zaszyfrowany nie istnieje.")
                        continue
                    }

                    // Tworzenie nazwy pliku odszyfrowanego
                    val decryptedFileName = if (input.nameWithoutExtension.endsWith("_zaszyfrowany")) {
                        input.nameWithoutExtension.removeSuffix("_zaszyfrowany") + "_odszyfrowany.txt"
                    } else {
                        input.nameWithoutExtension + "_odszyfrowany.txt"
                    }

                    val decryptedFile = File(input.parent, decryptedFileName)

                    aesEncryption.decryptFile(input.path, decryptedFile.path)
                    println("✅ Plik został odszyfrowany: ${decryptedFile.path}")
                } catch (e: Exception) {
                    println("❌ Wystąpił błąd podczas deszyfrowania pliku: ${e.message}")
                }
            }



            "4" -> {
                println("Zakończenie programu.")
                break
            }

            else -> println("❌ Niepoprawny wybór. Spróbuj ponownie.")
        }
    }
}
