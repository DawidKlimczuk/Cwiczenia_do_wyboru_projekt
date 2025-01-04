#Opis projektu
Aplikacja demonstruje zastosowanie szyfrowania AES do ochrony plików oraz przedstawia podatność na atak SQL Injection z pokazaniem sposobu zabezpieczenia przed nim. Główne funkcje programu to logowanie, szyfrowanie oraz odszyfrowywanie plików. Program jest interaktywny i pozwala użytkownikowi na wybór odpowiedniej akcji z menu.

Jak działa aplikacja?
Logowanie do systemu

Użytkownik może zalogować się za pomocą wstępnie zdefiniowanych kont:
Administrator: login admin, hasło admin123
Użytkownicy: user1 (hasło: password1), user2 (hasło: password2)
Istnieje możliwość demonstracji podatności na SQL Injection, np. przez wpisanie loginu: admin'--.
Po zalogowaniu wyświetlany jest odpowiedni komunikat.
--- LOGOWANIE ---
Podaj login: admin'--
Podaj hasło: sqlinjeciton
Wykonywane zapytanie: SELECT * FROM users WHERE username='admin'--'
✅ Znalezione dane:
Użytkownik: admin, Rola: admin, Hasło: admin123

Szyfrowanie plików
Użytkownik podaje ścieżkę do pliku wejściowego, np. D:\test.txt.
Program szyfruje plik i zapisuje wynik z nazwą zakończoną _zaszyfrowany.dat w tej samej lokalizacji.

Podaj pełną ścieżkę do pliku wejściowego (np. D:\test.txt): D:\test.txt
✅ Plik został zaszyfrowany: D:\test_zaszyfrowany.dat

Odszyfrowywanie plików
Użytkownik podaje ścieżkę do pliku zaszyfrowanego, np. D:\test_zaszyfrowany.dat.
Program odszyfrowuje plik i zapisuje wynik z nazwą zakończoną _odszyfrowany.txt.

Podaj pełną ścieżkę do pliku zaszyfrowanego (np. D:\test_zaszyfrowany.dat): D:\test_zaszyfrowany.dat
✅ Plik został odszyfrowany: D:\test_odszyfrowany.txt

Menu aplikacji
Program jest prosty w obsłudze, a każda funkcjonalność jest dostępna w głównym menu:
Logowanie do systemu.
Szyfrowanie pliku.
Odszyfrowywanie pliku.
Zakończenie programu.
Wykorzystane technologie
Szyfrowanie AES

Używany algorytm symetrycznego szyfrowania zapewnia bezpieczeństwo plików.
Klucz o długości 256 bitów jest generowany automatycznie.
Zabezpieczenie przed SQL Injection

W funkcji bezpiecznego logowania zastosowano przygotowane zapytania SQL (Prepared Statements), które uniemożliwiają wstrzyknięcie złośliwego kodu.

SQL Injection
Pokazano, jak podatność na SQL Injection może pozwolić na obejście procesu logowania.
Przykładowy atak: login admin'--.

Problemy napotkane podczas tworzenia
Brak automatycznego generowania nazw plików – rozwiązano przez automatyczne dodawanie _zaszyfrowany i _odszyfrowany.
Problemy z obsługą błędów – poprawiono komunikaty, aby były bardziej intuicyjne dla użytkownika.
Obsługa plików w szyfrowaniu – dodano funkcje do szyfrowania i odszyfrowywania plików wraz z obsługą ścieżek.
