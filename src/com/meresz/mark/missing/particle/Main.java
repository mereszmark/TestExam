package com.meresz.mark.missing.particle;

import java.util.*;

public class Main {

    /**
     * 1. Írj függvényt, ami kap egy egész számot ( nem szöveget ), és egy logikai értéket ad vissza, ami akkor igaz, ha
     * a számban előfordul a 6 és a 9 számjegy egymás mellett.
     */
    public static boolean checkNumber(Integer number) {
        int previousNumber = 0;
        boolean result = false;

        while (number > 0 && result == false) {
            if ((previousNumber == 6 && number % 10 == 9) || (previousNumber == 9 && number % 10 == 6))
                result = true;
            previousNumber = number % 10;
            number = number / 10;
        }
        return result;
    }

    /**
     * 2. Készíts egy boolean tamadas(int monsterHp) függvényt. A függvény akkor ad igazat, ha a hős legyőzi a monstert.
     * A harc körökre van osztva, a hős minden körben dob egy hat oldalú kockával, és pont ennyi életerőt sebez a
     * monsteren. A paraméterben megadott szám adja a monster kezdeti életerejét. A monster is dob minden körben, és ha
     * egy 7 oldalú kockával 7-est dob, akkor azonnal megöli a hőst. Használhatsz egy random(int max) függvényt, ami
     * visszaad egy p véletlen számot, amire: 0<=p< max
     */
    private static int random(int max) {
        return (int) (Math.random() * max) + 1;
    }

    /*Feltételeztem, hogy a hősünknek is van életereje és  monster ugyanúgy sebez a hirtelen ölés mellett, nem volt
    világos a feladatleírásból*/
    public static boolean tamadas(int monsterHp) {
        int heroeHp = 100;
        int monsterDice, heroeDice;
        boolean result = false;
        do {
            heroeDice = random(6);
            monsterHp = monsterHp - heroeDice;
            monsterDice = random(7);
            heroeHp = heroeHp - monsterDice;
            if (monsterDice == 7 || heroeHp <= 0)
                result = false;
            else if (monsterHp <= 0)
                result = true;
        } while (monsterDice != 7 && monsterHp > 0 && heroeHp > 0);
        return result;
    }

    /**
     * 3. Készíts függvényt, ami megállapítja hogy egy szöveg cukormentes-e. Egy szöveget akkor mondunk cukormentesnek,
     * ha nem olvasható ki belőle az a szó, hogy “cukor”. Nem kell, hogy a betűk egymás mellett álljanak, de a
     * sorrendnek helyesnek kell lennie. Az is számít, ha pl a szöveg huszadik karaktere a “C”, majd pár karakterrel
     * később jön az “U”, stb.
     */
    public static boolean cukormentes(String input) {
        boolean result = false;
        String sugar = "cukor";
        String text = input.toLowerCase();
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == sugar.charAt(0))
                sugar = sugar.substring(1);
        }
        return sugar.length() > 0 ? true : false;
    }

    /**
     * Készíts egy függvényt, ami kap egy egész számokból álló listát, és visszaadja, hogy hány elem esetében igaz az,
     * hogy az elem pontosan eggyel kisebb az utána álló elemnél.
     */
    public static int eggyelNagyobb(List<Integer> szamok) {
        int count = 0;
        for (int i = 0; i < szamok.size() - 1; i++) {
            if (szamok.get(i) == szamok.get(i + 1) - 1)
                count++;
        }
        return count;
    }

    /**
     * Egy kétdimenziós boolean tömbben kapsz egy térképet. Igaz esetén a mező fal, egyébként üres. Már csak E energiád
     * maradt, el tudsz-e jutni a startból célba? Csak vízszintesen vagy függőlegesen léphetsz, minden lépés egy
     * energiába kerül. Fogy az oxigéned, ezért most nem szempont, hogy a kód csodálatos legyen, és amúgy is brutálerős
     * gépen futtatod, de a hibás működés az életedbe kerülhet.
     */
    public static boolean megcsinalhato(boolean[ ][ ] terkep, int startX, int startY, int energia, int celX, int
            celY) {
        int result = BFS(terkep, startX, startY, celX, celY);
        if(result==-1 || result>energia)
            return false;
        else
            return true;
    }

    /**
     * Az ideális út megtalálásához szélésségi keresést futtatunk. Mely meghatározza A-ból B-be a legrövidebb utat.
     * Később ellenőrizzük hogy a legrövidebb út bejárható-e E energiával.
     */
    public static int BFS(boolean map[][],  int srcX, int srcY,int destX,int destY)
    {
        int rowNum[] = {-1, 0, 0, 1};
        int colNum[] = {0, -1, 1, 0};
        class Cell
        {
            int x,y,dist;

            public Cell(int x, int y, int dist) {
                this.x = x;
                this.y = y;
                this.dist = dist;
            }
        };

        boolean visited[][]=new boolean[map.length][map[0].length];
        visited[srcX][srcY] = true;
        Queue<Cell> queue=new LinkedList<>();
        Cell startCell =new Cell(srcX,srcY,0);
        queue.add(startCell);
        while (queue.size()!=0)
        {
            Cell currentCell = queue.peek();
            if (currentCell.x == destX && currentCell.y == destY)
                return currentCell.dist;
            queue.poll();
            for (int i = 0; i < 4; i++)
            {
                int row = currentCell.x + rowNum[i];
                int col = currentCell.y + colNum[i];
                if ((row >= 0) && (row < map.length) &&
                        (col >= 0) && (col < map[0].length) && map[row][col] &&
                        !visited[row][col])
                {
                    visited[row][col] = true;
                    Cell adjacentCell = new Cell(row, col,currentCell.dist + 1 );
                    queue.add(adjacentCell);
                }
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        System.out.println(checkNumber(69));
        System.out.println(checkNumber(0));
        System.out.println(checkNumber(999));
        System.out.println(checkNumber(666));
        System.out.println(checkNumber(9600));
        System.out.println(tamadas(100));
        System.out.println(cukormentes("cuko-------r"));
        System.out.println(cukormentes("cukor"));
        System.out.println(cukormentes("cuk-----or"));
        System.out.println(eggyelNagyobb(Arrays.asList(1)));
        boolean[][]terkep=new boolean[][]{
                {true,true,true,true,true},
                {true,false,true,true,true},
                {true,false,true,false,true},
                {true,false,true,false,true},
                {true,false,true,false,true}
        };

        System.out.println(megcsinalhato(terkep,0,0,10,4,4));
    }
}