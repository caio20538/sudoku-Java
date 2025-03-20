package br.com.dio;

import br.com.dio.model.Board;
import br.com.dio.model.Space;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static br.com.dio.util.BoardTemplate.BOARD_TEMPLATE;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toMap;

public class Main {
    private final static Scanner scan = new Scanner(System.in);

    private static Board board;

    private final static int BOARD_LIMIT = 9;

    public static void main(String[] args) {
        //permite a manipulação dos argumentos inseridos no terminal de forma livre
        final var positions = Stream.of(args).
                collect(toMap(
                        k -> k.split(";")[0],
                        v -> v.split(";")[1]
                ));

        var option = -1;

        while (true){
            System.out.println("Selecione uma das opções a seguir");
            System.out.println("1 - Iniciar um novo jogo");
            System.out.println("2 - Colocar um novo número");
            System.out.println("3 - Remover um número");
            System.out.println("4 - Visualizar o jogo atual");
            System.out.println("5 - Verificar status do jogo");
            System.out.println("6 - Limpar Jogo");
            System.out.println("7 - Finalizar Jogo");
            System.out.println("8 - Sair");

            option = scan.nextInt();

            switch (option){
                case 1 -> startGame(positions);
                case 2 -> inputNumber();
                case 3 -> removeNumber();
                case 4 -> showCurrentGame();
                case 5 -> showGameStatus();
                case 6 -> clearGame();
                case 7 -> finishGame();
                case 8 -> System.exit(0);
                default -> System.err.println("Opção inválida, selecione uma das opções do menu");
            }
        }
    }

    private static void finishGame() {
        if (isGameNotStarted()) return;

        if (board.gameIsFinished()){
            System.out.println("Parabéns, você concluiu o jogo!!");
            showCurrentGame();
            board = null;
        } else if (board.hasErrors()) {
            System.out.println("Seu jogo contém erros");
        }else{
            System.out.println("Você ainda precisa preencher algum espaço");
        }
    }

    private static void clearGame() {
        if (isGameNotStarted()) return;

        System.out.println("Tem certeza que quer limpar o jogo e perder todo o seu progresso? (S/N)");
        var confirm = scan.next().toUpperCase();

        while (!confirm.equals("S") && !confirm.equals("N")){
            System.out.println("Informe S (sim) ou N (não)");
            confirm = scan.next().toUpperCase();
        }

        if (confirm.equals("S"))
            board.reset();

    }

    private static void showGameStatus() {
        if (isGameNotStarted()) return;

        System.out.printf("O jogo atual se encontra no status %s%n", board.getStatus().getLabel());

        if (board.hasErrors()){
            System.out.println("O jogo contém erros");
            return;
        }

        System.out.println("O jogo não contém erros");
    }

    private static void showCurrentGame() {
        if (isGameNotStarted()) return;

        //pode substituir por arrayList
        //limito até 81 pois é um 9 * 9
        var args = new Object[81];
        var argPosition = 0;

        //imprimo primeiro a linha até chegar no limite e depois desço uma coluna.
        for (int i = 0; i < BOARD_LIMIT; i++) {
            for (var col : board.getSpace()){
                args[argPosition ++] = " " + (isNull(col.get(i).getActual()) ? " " : col.get(i).getActual());
            }
        }

        System.out.println("Seu game atual: ");

        System.out.printf((BOARD_TEMPLATE) + "%n", args);
    }

    private static void removeNumber() {
        if (isGameNotStarted()) return;

        //inserir a posição definidos até 9, começando do 0 e terminando no 8
        System.out.println("Informe a coluna em que o número será inserido");
        var col = runUtilGetValidNumber(0,8);

        System.out.println("Informe a linha em que o número será inserido");
        var row = runUtilGetValidNumber(0,8);

        //inserir valores definidos até 9
        System.out.printf("Informe o número que vai entrar na posição [%s][%s]\n", col, row);
        var value = runUtilGetValidNumber(1, 9);

        if (!board.clearValue(col,row)){
            System.err.printf("A posição [%s][%s] tem um valor fixo\n", col, row);
        }
    }

    private static void inputNumber() {
        if (isGameNotStarted()) return;

        //inserir a posição definidos até 9, começando do 0 e terminando no 8
        System.out.println("Informe a coluna em que o número será inserido");
        var col = runUtilGetValidNumber(0,8);

        System.out.println("Informe a linha em que o número será inserido");
        var row = runUtilGetValidNumber(0,8);

        //inserir valores definidos até 9
        System.out.printf("Informe o número que vai entrar na posição [%s][%s]\n", col, row);
        var value = runUtilGetValidNumber(1, 9);

        //inserir
        if (!board.changeValue(col, row, value)){
            System.err.printf("A posição [%s][%s] tem um valor fixo\n", col, row);
        }
    }

    private static void startGame(Map<String, String> positions) {
        if (nonNull(board)){
            System.out.println("O jogo já foi iniciado");
            return;
        }

        List<List<Space>> spaces = new ArrayList<>();

        //para salvar a posição e colocará o número lá
        for (int i = 0; i < BOARD_LIMIT; i++) {
            spaces.add(new ArrayList<>());
            for (int j = 0; j < BOARD_LIMIT; j++) {
                var positionConfig = positions.get("%s,%s".formatted(i,j));
                var expected = Integer.parseInt(positionConfig.split(",")[0]);
                var fixed = Boolean.parseBoolean(positionConfig.split(",")[1]);
                var currentSpace = new Space(expected, fixed);
                spaces.get(i).add(currentSpace);
            }
        }

        board = new Board(spaces);
        System.out.println("O jogo está pronto para começar");

    }

    //métodos utilitário

    private static boolean isGameNotStarted() {
        if (isNull(board)){
            System.out.println("O jogo ainda não foi iniciado");
            return true;
        }
        return false;
    }

    private static int runUtilGetValidNumber(final int min, final int max){
        var current = scan.nextInt();
        while (current < min || current > max){
            System.out.printf("Informe um número nas posições [%s] [%s]\n", min, max);
            current = scan.nextInt();
        }
        return current;
    }
}
