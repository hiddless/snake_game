import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Game.drawSnakeOnBoard();
        Game.drawFrogsOnBoard();

        Scanner sc = new Scanner(System.in);
        while (true) {
            Board.print(Game.getReverseCount(), Game.getRemoveCount(), Game.getEaten());
            if(Game.isWin()){
                System.out.println("u win");
                break;
            } else if (Game.isLose()) {
                System.out.println("u lose");
                break;

            }
            System.out.print("> ");
            String cmd = sc.nextLine();
            if (cmd.length() == 1 && "wasd".contains(cmd)) {
                Snake.move(cmd);
                Snake.moveMirrorOpposite(cmd);
                Frogs.tick();
                Game.checkWinLose();
            } else if (cmd.equals("r")) {
                // reverse snake
                Snake.reverse();
                Snake.reverseMirror();
            } else if (cmd.equals("e")) {
                // snake sheds it's skin
                Game.toggleSkin();
            } else if (cmd.equals("x")) {
                break;
            }
        }
        sc.close();
    }

}
