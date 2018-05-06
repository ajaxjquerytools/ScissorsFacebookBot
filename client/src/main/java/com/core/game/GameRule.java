package com.core.game;

/**
 * Created by VolodymyrD on 9/12/17.
 */
public class GameRule {

    /**
     * **
     * — камень побеждает ножницы (камень затупляет ножницы)
     * — ножницы побеждают бумагу (ножницы разрезают бумагу)
     * — бумага побеждает камень (бумага заворачивает камень)
     * — ничья, если у всех игроков одновременно показан одинаковый знак
     **/

    public static GameResult getWinner(GameTurn user, GameTurn opponent) {
        if (user == opponent) {
            return GameResult.DRAW;
        }

        if (user == GameTurn.ROCK && opponent == GameTurn.SCISSORS) {
            return GameResult.WIN;
        }

        if (user == GameTurn.SCISSORS && opponent == GameTurn.PAPER) {
            return GameResult.WIN;
        }

        if (user == GameTurn.PAPER && opponent == GameTurn.ROCK) {
            return GameResult.WIN;
        }

        return GameResult.LOST;
    }
}
