import java.sql.ResultSet

class UserManager(
    private val databaseManager: DatabaseManager,
    private val aesEncryption: AESEncryption
) {

    // Inicjalizacja bazy danych
    fun initializeDatabase() {
        databaseManager.initializeDatabase()
    }

    // Funkcja logowania z zabezpieczeniami
    fun loginSecure(username: String, password: String): String? {
        val connection = databaseManager.connect()
        val sql = "SELECT * FROM users WHERE username = ? AND password = ?"
        val preparedStatement = connection.prepareStatement(sql)

        val encryptedPassword = aesEncryption.encrypt(password) // Hasło w formie zaszyfrowanej
        preparedStatement.setString(1, username)
        preparedStatement.setString(2, encryptedPassword)

        val resultSet: ResultSet = preparedStatement.executeQuery()

        if (resultSet.next()) {
            val role = resultSet.getString("role")
            val usernameResult = resultSet.getString("username")
            connection.close()
            return "Zalogowano jako $usernameResult ($role)"
        }

        connection.close()
        return null
    }

    // Funkcja logowania podatna na SQL Injection
    fun loginVulnerable(input: String): String? {
        val connection = databaseManager.connect()
        val sql = "SELECT * FROM users WHERE username='$input'" // Wartość w cudzysłowach
        println("Wykonywane zapytanie: $sql")

        try {
            val statement = connection.createStatement()
            val resultSet = statement.executeQuery(sql)
            val results = mutableListOf<String>()

            while (resultSet.next()) {
                val username = resultSet.getString("username")
                val role = resultSet.getString("role")
                val encryptedPassword = resultSet.getString("password")

                // Próba odszyfrowania hasła
                val decryptedPassword = try {
                    aesEncryption.decrypt(encryptedPassword)
                } catch (e: Exception) {
                    "[Błąd odszyfrowania]"
                }

                results.add("Użytkownik: $username, Rola: $role, Hasło: $decryptedPassword")
            }

            connection.close()

            return if (results.isNotEmpty()) {
                "Znalezione dane:\n" + results.joinToString("\n")
            } else {
                "❌ Nie znaleziono danych dla zapytania: $sql"
            }
        } catch (e: Exception) {
            connection.close()
            return "❌ Wystąpił błąd podczas wykonywania zapytania: ${e.message}"
        }
    }
}
