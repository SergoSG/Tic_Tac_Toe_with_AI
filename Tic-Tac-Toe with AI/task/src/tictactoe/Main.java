package tictactoe;

import java.util.*;

enum GameState {CONTINUE, DRAW, X_WIN, O_WIN, EXIT}

class TicTocToe {
    final int n = 3;
    final int m = 3;

    String[][] table = new String[n][m];
    GameState state;
    int row;
    int col;
    boolean isXStep;
    String player1 = "";
    String player2 = "";

    String row1;
    String row2;
    String row3;

    String col1;
    String col2;
    String col3;

    String diagonal1;
    String diagonal2;

    public TicTocToe(String initialString) {
        if (initialString.isEmpty()) {
            initialString = "_________";
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                this.table[i][j] = initialString.substring(i * n + j, i * n + j + 1);
            }
        }
        this.state = GameState.CONTINUE;
        this.row = -1;
        this.col = -1;
        this.isXStep = true;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }

    public void setEmptyTable() {
        String initialString = "_________";

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                this.table[i][j] = initialString.substring(i * n + j, i * n + j + 1);
            }
        }
        this.state = GameState.CONTINUE;
        this.row = -1;
        this.col = -1;
        this.isXStep = true;
    }

    public void exit() {
        this.state = GameState.EXIT;
    }

    public GameState getState() {
        return this.state;
    }

    public void show() {
        System.out.println("---------");
        for (String[] row : this.table) {
            System.out.print("| ");
            for (String cell : row) {
                System.out.print(("_".equals(cell) ? " " : cell) + " ");
            }
            System.out.println("|");
        }
        System.out.println("---------");
    }

    public boolean isNotDigit(String str) {
        if (str.length() == 0) {
            return true;
        }
        try {
            int num = Integer.parseInt(str);
            return false;
        } catch (NumberFormatException e) {
            return true;
        }
    }

    public boolean checkInput(String input) {
        String[] inputArray = input.split(" ");
        if (isNotDigit(inputArray[0]) || isNotDigit(inputArray[1])) {
            System.out.println("You should enter numbers!");
            this.row = -1;
            this.col = -1;
            return false;
        } else {
            this.row = Integer.parseInt(inputArray[0]);
            this.col = Integer.parseInt(inputArray[1]);
        }
        if (this.row < 1 || this.row > 3 || this.col < 1 || this.col > 3 ) {
            System.out.println("Coordinates should be from 1 to 3!");
            this.row = -1;
            this.col = -1;
            return false;
        } else {
            if ("X".equals(this.table[this.row - 1][this.col - 1]) ||
                    "O".equals(this.table[this.row - 1][this.col - 1])) {
                System.out.println("This cell is occupied! Choose another one!");
                this.row = -1;
                this.col = -1;
                return false;
            }
        }

        int cntX = 0;
        int cntO = 0;
        for (String[] row : this.table) {
            for (String cell : row) {
                if ("X".equals(cell)) {
                    cntX++;
                } else if ("O".equals(cell)) {
                    cntO++;
                }
            }
        }
        this.isXStep = cntX == cntO;

        return true;
    }

    public void nextPlayer() {
        if (this.row != -1 && this.col != -1) {
            this.table[row - 1][col - 1] = this.isXStep ? "X" : "O";
            this.row = -1;
            this.col = -1;
            this.isXStep = !this.isXStep;
            transformTableToStrings();
        }
    }

    public void nextCompEasy() {
        boolean isContinue = true;
        while (isContinue) {
            int randomI = (int) (Math.random() * 10 / 3.36);
            int randomJ = (int) (Math.random() * 10 / 3.36);
            if ("_".equals(this.table[randomI][randomJ])) {
                this.table[randomI][randomJ] = this.isXStep ? "X" : "O";
                this.row = -1;
                this.col = -1;
                this.isXStep = !this.isXStep;
                transformTableToStrings();
                isContinue = false;
            }
        }
    }

    public void nextCompMedium() {
        boolean isContinue = true;
        while (isContinue) {
            int randomI = (int) (Math.random() * 10 / 3.36);
            int randomJ = (int) (Math.random() * 10 / 3.36);

            int[] isMyWin = checkCanWin(this.isXStep ? "X" : "O");
            if (isMyWin[0] != 0 && isMyWin[1] != 0) {
                this.table[isMyWin[0] - 1][isMyWin[1] - 1] = this.isXStep ? "X" : "O";
                this.row = -1;
                this.col = -1;
                this.isXStep = !this.isXStep;
                transformTableToStrings();
                isContinue = false;
                continue;
            }

            int[] isEnemyWin = checkCanWin(this.isXStep ? "O" : "X");
            if (isEnemyWin[0] != 0 && isEnemyWin[1] != 0) {
                this.table[isEnemyWin[0] - 1][isEnemyWin[1] - 1] = this.isXStep ? "X" : "O";
                this.row = -1;
                this.col = -1;
                this.isXStep = !this.isXStep;
                transformTableToStrings();
                isContinue = false;
                continue;
            }

            if ("_".equals(this.table[randomI][randomJ])) {
                this.table[randomI][randomJ] = this.isXStep ? "X" : "O";
                this.row = -1;
                this.col = -1;
                this.isXStep = !this.isXStep;
                transformTableToStrings();
                isContinue = false;
            }
        }
    }

    public void nextCompHard() {
        int tmp = getMinMaxNextStep(transformTableToArray(this.table), this.isXStep ? "X" : "O")[0];
        this.table[tmp / this.n][tmp % this.m] = this.isXStep ? "X" : "O";
        this.row = -1;
        this.col = -1;
        this.isXStep = !this.isXStep;
        transformTableToStrings();
    }

    public String[] transformTableToArray(String[][] table) {
        String[] string = new String[this.n * this.m];
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.m; j++) {
                string[i * this.m + j] = "_".equals(table[i][j]) ? String.valueOf(i * this.m + j) : table[i][j];
            }
        }
        return string;
    }

    public String stringToState(String[] string) {
        String row1 = string[0] + string[1] + string[2];
        String row2 = string[3] + string[4] + string[5];
        String row3 = string[6] + string[7] + string[8];

        String col1 = string[0] + string[3] + string[6];
        String col2 = string[1] + string[4] + string[7];
        String col3 = string[2] + string[5] + string[8];

        String diagonal1 = string[0] + string[4] + string[8];
        String diagonal2 = string[2] + string[4] + string[6];

        if (col1.equals("XXX") || col2.equals("XXX") || col3.equals("XXX") ||
                row1.equals("XXX") || row2.equals("XXX") || row3.equals("XXX") ||
                diagonal1.equals("XXX") || diagonal2.equals("XXX")) {
            return "X";
        }

        if (col1.equals("OOO") || col2.equals("OOO") || col3.equals("OOO") ||
                row1.equals("OOO") || row2.equals("OOO") || row3.equals("OOO") ||
                diagonal1.equals("OOO") || diagonal2.equals("OOO")) {
            return "O";
        }

        for (String str : string) {
            if (!"X".equals(str) && !"O".equals(str)) {
                return "NONE";
            }
        }

        return "DRAW";
    }

    public int[] getMinMaxNextStep(String[] array, String player) {
        ArrayList<String> emptyCells = new ArrayList<>();
        ArrayList<Integer> scoreCells = new ArrayList<>();

        for (String cell : array) {
            if (!"X".equals(cell) && !"O".equals(cell)) {
                emptyCells.add(cell);
                scoreCells.add(0);
            }
        }

        if (emptyCells.isEmpty()) {
            return new int[]{0, 0};
        } else {
            for (int i = 0; i < emptyCells.size(); i++) {
                String[] arrayTmp = array.clone();
                arrayTmp[Integer.parseInt(emptyCells.get(i))] = player;
                if ((this.isXStep ? "X" : "O").equals(player) && player.equals(stringToState(arrayTmp))) {
                    scoreCells.set(i, 10);
                } else if (!(this.isXStep ? "X" : "O").equals(player) && player.equals(stringToState(arrayTmp))) {
                    scoreCells.set(i, -10);
                } else if ("DRAW".equals(stringToState(arrayTmp))) {
                    scoreCells.set(i, 0);
                } else {
                    scoreCells.set(i, getMinMaxNextStep(
                            arrayTmp,
                            ("X".equals(player) ? "O" : "X"))[1]);
                }
            }
        }
        int tmp = (this.isXStep ? "X" : "O").equals(player) ? Collections.max(scoreCells) : Collections.min(scoreCells);
        return new int[]{Integer.parseInt(emptyCells.get(scoreCells.indexOf(tmp))), tmp};
    }

    public int[] checkCanWin(String player) {
        int[] step = {0, 0};
        String variant1 = "_" + player + player;
        String variant2 = player + "_" + player;
        String variant3 = player + player + "_";

        if (variant1.equals(this.row1) || variant1.equals(this.col1) || variant1.equals(this.diagonal1)) {
            step[0] = 1;
            step[1] = 1;
        } else if (variant2.equals(this.row1) || variant1.equals(this.col2)) {
            step[0] = 1;
            step[1] = 2;
        } else if (variant3.equals(this.row1) || variant1.equals(this.col3) || variant1.equals(this.diagonal2)) {
            step[0] = 1;
            step[1] = 3;
        } else if (variant1.equals(this.row2) || variant2.equals(this.col1)) {
            step[0] = 2;
            step[1] = 1;
        } else if (variant2.equals(this.row2) || variant2.equals(this.col2) || variant2.equals(this.diagonal1) ||
                   variant2.equals(this.diagonal2)) {
            step[0] = 2;
            step[1] = 2;
        } else if (variant3.equals(this.row2) || variant2.equals(this.col3)) {
            step[0] = 2;
            step[1] = 3;
        } else if (variant1.equals(this.row3) || variant3.equals(this.col1) || variant3.equals(this.diagonal2)) {
            step[0] = 3;
            step[1] = 1;
        } else if (variant2.equals(this.row3) || variant3.equals(this.col2)) {
            step[0] = 3;
            step[1] = 2;
        } else if (variant3.equals(this.row3) || variant3.equals(this.col3) || variant3.equals(this.diagonal1)) {
            step[0] = 3;
            step[1] = 3;
        }

        return step;
    }

    public void transformTableToStrings() {
        this.row1 =
            new StringBuilder().append(this.table[0][0]).append(this.table[0][1]).append(this.table[0][2]).toString();
        this.row2 =
            new StringBuilder().append(this.table[1][0]).append(this.table[1][1]).append(this.table[1][2]).toString();
        this.row3 =
            new StringBuilder().append(this.table[2][0]).append(this.table[2][1]).append(this.table[2][2]).toString();

        this.col1 =
            new StringBuilder().append(this.table[0][0]).append(this.table[1][0]).append(this.table[2][0]).toString();
        this.col2 =
            new StringBuilder().append(this.table[0][1]).append(this.table[1][1]).append(this.table[2][1]).toString();
        this.col3 =
            new StringBuilder().append(this.table[0][2]).append(this.table[1][2]).append(this.table[2][2]).toString();

        this.diagonal1 =
            new StringBuilder().append(this.table[0][0]).append(this.table[1][1]).append(this.table[2][2]).toString();
        this.diagonal2 =
            new StringBuilder().append(this.table[0][2]).append(this.table[1][1]).append(this.table[2][0]).toString();
    }

    public void checkState() {
        if (this.col1.equals("XXX") || this.col2.equals("XXX") || this.col3.equals("XXX") ||
                this.row1.equals("XXX") || this.row2.equals("XXX") || this.row3.equals("XXX") ||
                this.diagonal1.equals("XXX") || this.diagonal2.equals("XXX")) {
            this.state = GameState.X_WIN;
            System.out.println("X wins");
            return;
        }

        if (this.col1.equals("OOO") || this.col2.equals("OOO") || this.col3.equals("OOO") ||
                this.row1.equals("OOO") || this.row2.equals("OOO") || this.row3.equals("OOO") ||
                this.diagonal1.equals("OOO") || this.diagonal2.equals("OOO")) {
            this.state = GameState.O_WIN;
            System.out.println("O wins");
            return;
        }

        for (String[] row : this.table) {
            for (String cell : row) {
                if ("_".equals(cell)) {
                    this.state = GameState.CONTINUE;
                    return;
                }
            }
        }

        this.state = GameState.DRAW;
        System.out.println("Draw");
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean isContinue = true;
        TicTocToe game = new TicTocToe("");

        do {
            System.out.print("Input command: ");
            if (checkParam(scanner.nextLine(), game)) {
                if (game.getState() == GameState.EXIT) {
                    isContinue = false;
                } else {
                    if ("user".equals(game.player1)) {
                        game.show();
                    }
                    while (game.state == GameState.CONTINUE) {
                        switch (game.player1) {
                            case "user":
                                boolean isCont = true;
                                do {
                                    System.out.print("Enter the coordinates: ");
                                    if (game.checkInput(scanner.nextLine())) {
                                        game.nextPlayer();
                                        game.show();
                                        game.checkState();
                                        isCont = false;
                                    }
                                } while (isCont);
                                break;
                            case "easy":
                                System.out.println("Making move level \"easy\"");
                                game.nextCompEasy();
                                game.show();
                                game.checkState();
                                break;
                            case "medium":
                                System.out.println("Making move level \"medium\"");
                                game.nextCompMedium();
                                game.show();
                                game.checkState();
                                break;
                            case "hard":
                                System.out.println("Making move level \"hard\"");
                                game.nextCompHard();
                                game.show();
                                game.checkState();
                                break;
                            default: break;
                        }

                        if (game.state != GameState.CONTINUE) {
                            continue;
                        }

                        switch (game.player2) {
                            case "user":
                                boolean isCont = true;
                                do {
                                    System.out.print("Enter the coordinates: ");
                                    if (game.checkInput(scanner.nextLine())) {
                                        game.nextPlayer();
                                        game.show();
                                        game.checkState();
                                        isCont = false;
                                    }
                                } while (isCont);
                                break;
                            case "easy":
                                System.out.println("Making move level \"easy\"");
                                game.nextCompEasy();
                                game.show();
                                game.checkState();
                                break;
                            case "medium":
                                System.out.println("Making move level \"medium\"");
                                game.nextCompMedium();
                                game.show();
                                game.checkState();
                                break;
                            case "hard":
                                System.out.println("Making move level \"hard\"");
                                game.nextCompHard();
                                game.show();
                                game.checkState();
                                break;
                            default: break;
                        }
                    }
                }
            } else {
                System.out.println("Bad parameters!");
            }
        } while (isContinue);
    }

    public static boolean checkParam(String enterString, TicTocToe game) {
        ArrayList<String> commandList = new ArrayList<>(Arrays.asList("start", "user", "easy", "medium", "hard"));
        String[] enterArray = enterString.split(" ");

        if (enterArray.length == 1 && "exit".equals(enterArray[0].toLowerCase(Locale.ROOT))) {
            game.exit();
            return true;
        }

        if (enterArray.length == 3) {
            for (String word : enterArray) {
                if (!commandList.contains(word)) {
                    return false;
                }
            }
            game.setPlayer1(enterArray[1]);
            game.setPlayer2(enterArray[2]);
            game.setEmptyTable();

            return true;
        }
        return false;
    }
}
