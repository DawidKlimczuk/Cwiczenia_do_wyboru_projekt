import java.sql.Connection
import java.sql.DriverManager

class DatabaseManager(private val aesEncryption: AESEncryption) {

    // Metoda nawiązująca połączenie z bazą danych
    fun connect(): Connection {
        val url = "jdbc:sqlite:database.db" // SQLite: plik bazy danych
        return DriverManager.getConnection(url)
    }

    // Inicjalizacja bazy danych
    fun initializeDatabase() {
        val connection = connect()
        val statement = connection.createStatement()

        // Usunięcie tabeli, jeśli już istnieje (czyszczenie danych)
        statement.executeUpdate("DROP TABLE IF EXISTS users")

        // Tworzenie nowej tabeli 'users'
        statement.executeUpdate(
            """
            CREATE TABLE users (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                username TEXT NOT NULL,
                password TEXT NOT NULL,
                role TEXT NOT NULL
            )
            """
        )

        // Dodanie użytkowników do tabeli
        val users = listOf(
            Triple("admin", "admin123", "admin"),
            Triple("user1", "password1", "user"),
            Triple("user2", "password2", "user")
        )

        val preparedStatement = connection.prepareStatement(
            "INSERT INTO users (username, password, role) VALUES (?, ?, ?)"
        )

        for ((username, password, role) in users) {
            preparedStatement.setString(1, username)
            preparedStatement.setString(2, aesEncryption.encrypt(password))
            preparedStatement.setString(3, role)
            preparedStatement.executeUpdate()
        }

        connection.close()
        println("✅ Baza danych została zainicjalizowana.")
    }
}
