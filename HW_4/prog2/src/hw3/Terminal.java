package hw3;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class TerminalServer {
    Map<Integer, Integer> accountMap = new HashMap<>();

    public TerminalServer() {
        // сгенерируем 3 карточных счёта
        accountMap.put(9110, 1000);
        accountMap.put(4321, 127_345);
        accountMap.put(2204, 27);
    }

    public boolean CheckAccess(final int pin) {
        return accountMap.get(pin) != null;
    }

    public int GetAmount(final int pin) throws AccountNotFound {
        Integer ammount = accountMap.get(pin);
        if (ammount == null) {
            throw new AccountNotFound(pin);
        } else {
            return ammount;
        }
    }

    public boolean ChangeAmount(final int pin, int summa) throws AccountNotFound {
        Integer oldValue = accountMap.get(pin);
        if (oldValue == null) {
            throw new AccountNotFound(pin);
        }
        if (summa % 100 != 0) {
            return false;
        }
        int newValue = oldValue + summa;
        if (newValue < 0) {
            return false;
        }
        accountMap.put(pin, newValue);
        return true;
    }

    class AccountNotFound extends Exception {
        int pin;

        AccountNotFound(int pin) {
            this.pin = pin;
        }

        @Override
        public String toString() {
            return "Error! account " + pin + " not found";
        }
    }
}

class PinValidator {
    final int BLOCK_TIME_MS = 10 * 1000;

    long blockStartTime = System.currentTimeMillis() - BLOCK_TIME_MS - 1;
    int tryCount = 0;
    Scanner scanner = new Scanner(System.in);

    boolean isBlocked() {
        return System.currentTimeMillis() - blockStartTime <= BLOCK_TIME_MS;
    }

    int EnterPin() throws AccountIsLockedException, KeyboardFailure {
        char pinChar;
        String pinString = "";
        if (isBlocked()) {
            long remaindTime = BLOCK_TIME_MS - (System.currentTimeMillis() - blockStartTime);
            throw new AccountIsLockedException(remaindTime);
        }
        InputStreamReader streamReaderr = new InputStreamReader(System.in);
        Display("Введите PIN код:");
        do {

            try {
                pinChar = (char) streamReaderr.read();
            } catch (IOException exc) {
                throw new KeyboardFailure();
            }
            if (Character.isDigit(pinChar)) {
                System.out.print("*");
                pinString += pinChar;
            } else {
                Display("\"Допускаются только цифры (0 - 9)");
                String mask = "";
                for (int i = 0; i < pinString.length(); i++) {
                    mask += "*";
                }
                Display(mask);
            }
        } while (pinString.length() < 4);
        return Integer.parseInt(pinString);
    }

    int SelectOperation() {
        int opCod = 0;
        Display("\n1. Показать остаток на счёте\n2. Внести наличные\n3. Получить наличные\n4. Завершить работу");
        do {
            Display("Выберите операцию (1-4):");
            opCod = scanner.nextInt() - 1;
        } while (opCod < 0 || opCod > 3);
        return opCod;
    }

    int EnterSumm() {
        Display("Введите сумму:");
        return scanner.nextInt();
    }

    public void TryStayBlocked() {
        if (!isBlocked() && tryCount++ == 2) {
            blockStartTime = System.currentTimeMillis();
            tryCount = 0;
        }
    }

    public void Display(String s) {
        System.out.println(s);
    }

    class AccountIsLockedException extends Exception {
        long blockRemaind;

        AccountIsLockedException(long blockRemaind) {
            this.blockRemaind = blockRemaind;
        }

        @Override
        public String toString() {
            return "Блокировка будет снята через " + blockRemaind / 1000 + " сек";
        }
    }

    class KeyboardFailure extends Exception {
        @Override
        public String toString() {
            return "Терминал не исправен";
        }
    }
}

public class Terminal {
    final int SHOW_ACCOUNT_REST = 0;
    final int DEPOSIT_AMOUNT = 1;
    final int GET_AMOUNT = 2;
    final int TERMINATE_OPERATION = 3;

    final TerminalServer terminalServer = new TerminalServer();
    final PinValidator pinValidator = new PinValidator();

    public Terminal() {
    }

    void run() {
        int pin = 0;
        while (true) {
            // ввод pin-кода
            do {
                pinValidator.TryStayBlocked();
                try {
                    pin = pinValidator.EnterPin();
                } catch (PinValidator.KeyboardFailure | PinValidator.AccountIsLockedException exc) {
                    pinValidator.Display(exc.toString());
                }
            } while (!terminalServer.CheckAccess(pin));
            // выполнение операций
            while (true) {
                int operation = pinValidator.SelectOperation();
                try {
                    if (operation == TERMINATE_OPERATION) {
                        pinValidator.Display("Сеанс завершён");
                        break;
                    }
                    if (operation == SHOW_ACCOUNT_REST) {
                        pinValidator.Display(" На счету:" + terminalServer.GetAmount(pin));
                    }
                    if (operation == DEPOSIT_AMOUNT) {
                        pinValidator.Display("Внесение наличных");
                        if (terminalServer.ChangeAmount(pin, pinValidator.EnterSumm())) {
                            pinValidator.Display("Операция успешно выполнена");
                        } else {
                            pinValidator.Display("Операция НЕ выполнена");
                        }
                    }
                    if (operation == GET_AMOUNT) {
                        pinValidator.Display("Внесение наличных");
                        if (terminalServer.ChangeAmount(pin, pinValidator.EnterSumm() * (-1))) {
                            pinValidator.Display("Операция успешно выполнена");
                        } else {
                            pinValidator.Display("Операция НЕ выполнена");
                        }
                    }
                } catch (TerminalServer.AccountNotFound exc) {
                    pinValidator.Display(exc.toString());
                    break;
                }
            }
        }
    }
}

