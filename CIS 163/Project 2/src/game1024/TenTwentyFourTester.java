package game1024;

import org.junit.*;
import java.util.*;

import static org.junit.Assert.*;

public class TenTwentyFourTester {
    private final static int REPEAT_COUNT = 500;
    private final static int GAME_GOAL = 1024;
    private static NumberSlider gameLogic;
    private static Random gen;
    private static int NROWS, NCOLS;

    @BeforeClass
    public static void globalSetup()
    {
        System.out.println ("Setting up Unit Testing");
        gen = new Random();
        gameLogic = new NumberGame();
        for (String[] pat : MOVE_PATTERNS)
            assertEquals(pat[0].length(), pat[1].length());
    }

    @Before
    public void setUp() throws Exception {
        NROWS = gen.nextInt(8) + 3; /* 2-15 */
        NCOLS = gen.nextInt(8) + 3;
        gameLogic.resizeBoard(NROWS, NCOLS, GAME_GOAL);
    }

    @AfterClass
    public static void shutDown() throws Exception {
        System.out.println("Shutting down Unit Testing");
    }

    @Test
    public void resetShouldShowTwoValue()
    {
        gameLogic.reset();
        List<Cell> result = gameLogic.getNonEmptyTiles();
        assertTrue("reset() should initialize the board ", result.size() == 2);
        Cell c1 = result.get(0);
        Cell c2 = result.get(1);
        assertTrue (c1.row != c2.row || c1.column != c2.column);
        assertTrue (isPowerTwo(c1.value));
        assertTrue (isPowerTwo(c2.value));
    }

    @Test
    public void resetShouldInitializeGameStatus()
    {
        gameLogic.reset();
        assertNotNull("reset() should initialize game status ", gameLogic.getStatus());
        assertEquals("reset() should initialize game status", GameStatus.IN_PROGRESS,
                gameLogic.getStatus());
    }

    @Test(timeout = 5000)
    public void testSetValues()
    {
        final int[][] zeros = new int[NROWS][NCOLS];
        final int[][] one = new int[NROWS][NCOLS];
        one[gen.nextInt(NROWS)][gen.nextInt(NCOLS)] = 1;

        gameLogic.setValues(zeros);
        List<Cell> zeroList = (List<Cell>) gameLogic.getNonEmptyTiles().clone();
        assertNotNull("getNonEmptyTiles() should never return null", zeroList);
        gameLogic.setValues(one);
        List<Cell> oneList = gameLogic.getNonEmptyTiles();
        assertFalse("setValues() is not implemented correctly", zeroList.equals(oneList));
    }

    @Test(timeout = 5000)
    public void resizeShouldHandleBoardsOfAnySize()
    {
        try {
            int[][] data;
            for (int count = 0; count < REPEAT_COUNT; count++) {
                int rows = gen.nextInt(39) + 2;
                int cols = gen.nextInt(39) + 2;
                gameLogic.resizeBoard(rows, cols, GAME_GOAL);
                data = new int[rows][cols];
                for (int k = 0; k < rows; k++)
                    for (int m = 0; m < cols; m++)
                        data[k][m] = 1;
                gameLogic.setValues(data);
                assertEquals("Your game logic should be able to handle game board of any size", rows * cols,
                        gameLogic.getNonEmptyTiles().size());
            }
        }
        catch (Exception e)
        {
            fail ("Your game logic can't handle game board of any size");
        }
    }

    @Test
    public void randomValueShallBePlacedOnEmptySpot() {
        final int N = NROWS * NCOLS;
        System.out.println(gameLogic.getNonEmptyTiles().size() + " TOTAL CELLS OUT OF " + N);
        int i = 0;
        for (int k = 0; k < N - 1; k++) {
            int oldCount = gameLogic.getNonEmptyTiles().size();
            gameLogic.placeRandomValue();
            int newCount = gameLogic.getNonEmptyTiles().size();
            System.out.println("TOTAL CELLS PLACED: " + newCount);
            assertTrue (newCount == oldCount + 1);
        }
        gameLogic.placeRandomValue();
    }

    private int nextTileValue()
    {
        int pow = gen.nextInt(7);  /* 1, 2, 4, 8, 16, 32, 64 */
        int num = 1;
        for (int n = 0; n < pow; n++)
            num *= 2;
        return num;
    }

    @Test(timeout = 5000)
    public void getNonEmptyTilesShallReturnNonZeroTiles()
    {
        int[][] vals = new int[NROWS][NCOLS];
        for (int count = 0; count < REPEAT_COUNT; count++) {
            for (int k = 0; k < vals.length; k++)
                for (int m = 0; m < vals[0].length; m++) {
                    if (gen.nextBoolean())
                        vals[k][m] = 0;
                    else {
                        vals[k][m] = nextTileValue();
                    }
                }
            swiper(gameLogic, null, null, vals, vals);
        }
    }

    private int countNonZero(int[][] arr)
    {
        int nz = 0;
        for (int[] row : arr)
            for (int v : row)
                if (v != 0) nz++;
        return nz;
    }

    private boolean isPowerTwo (int z)
    {
        int v = 1;
        while (v < z)
            v *= 2;
        return v == z;
    }

    private void swiper (NumberSlider cm, SlideDirection dir, String chPat, int[][] before, int[][] after)
    {
        gameLogic.setValues(before);
        String action = "";
        if (dir != null) {
            boolean b = gameLogic.slide(dir);
            assertTrue("move() should return TRUE when game board changes", b);
            action = "move " + dir.name() + ":";
        }
        List<Cell> out = gameLogic.getNonEmptyTiles();
        int[][] actual = new int[after.length][after[0].length];
        for (Cell c : out) {
            if (c.row < 0 || c.row >= after.length || c.column < 0 || c.column >= after[0].length)
                fail("game1024.Cell index (" + c.row + "," + c.column + " is out of bound");
            assertTrue("getNonEmptyTiles() should return only non-zero cells", c.value != 0);
            actual[c.row][c.column] = Math.abs(c.value);
        }

        StringBuilder afterSb = new StringBuilder();
        StringBuilder actualSb = new StringBuilder();
        for (int k = 0; k < after.length; k++) {
            for (int m = 0; m < after[k].length; m++)
            {
                afterSb.append(String.format("%3d ", after[k][m]));
                actualSb.append(String.format("%3d ", actual[k][m]));
            }
            afterSb.append("\n");
            actualSb.append("\n");
        }
        int misMatchCount = 0;
        int misMatchRow = -1, misMatchCol = -1;
        for (int k = 0; k < after.length; k++)
            for (int m = 0; m < after[0].length; m++) {
                if (after[k][m] != actual[k][m]) {
                    misMatchCount++;
                    misMatchRow = k;
                    misMatchCol = m;

                }
            }
        switch (misMatchCount)
        {
            case 0:
                if (dir != null)
                    fail("move() failed to generate a new random cell after board changes");
                break;
            case 1:
                assertTrue ("move(): the new random value must show up in an empty cell", after[misMatchRow][misMatchCol] == 0);
                assertTrue ("New generated value must be a power of 2", isPowerTwo (actual[misMatchRow][misMatchCol]));
                break;
            default:
                fail(action + " Mismatch entry at (" + misMatchRow + "," + misMatchCol +
                        ")\nExpected:\n" + afterSb + "\nYour answer:\n" + actualSb);

        }
    }

    private void noMoveSwiper (NumberSlider cm, SlideDirection dir, int[][] mat)
    {
        gameLogic.setValues(mat);
        String action = "";
        if (dir != null) {
            boolean b = gameLogic.slide(dir);
            action = "move " + dir.name() + ":";
            assertFalse("move() should return FALSE when no cells can be moved", b);
        }
        List<Cell> out = gameLogic.getNonEmptyTiles();
        int[][] actual = new int[mat.length][mat[0].length];
        for (Cell c : out) {
            if (c.row < 0 || c.row >= mat.length || c.column < 0 || c.column >= mat[0].length)
                fail("game1024.Cell index (" + c.row + "," + c.column + " is out of bound");
            assertTrue("getNonEmptyTiles() should return only non-zero cells", c.value > 0);
//            assertTrue(action + " (" + c.row + "," + c.column + ") should be zero",
//                    after[c.row][c.column] != 0);
            actual[c.row][c.column] = c.value;
        }

        StringBuilder afterSb = new StringBuilder();
        StringBuilder actualSb = new StringBuilder();
        for (int k = 0; k < mat.length; k++) {
            for (int m = 0; m < mat[k].length; m++)
            {
                afterSb.append(String.format("%3d ", mat[k][m]));
                actualSb.append(String.format("%3d ", actual[k][m]));
            }
            afterSb.append("\n");
            actualSb.append("\n");
        }
        for (int k = 0; k < mat.length; k++)
            for (int m = 0; m < mat[0].length; m++) {
                if (mat[k][m] != actual[k][m]) {
                    fail(action + " Mismatch entry at (" + k + "," + m +
                            ")\nExpected:\n" + afterSb + "\nYour answer:\n" + actualSb);
                }
            }
    }

    private void resetValues (int[][] arr)
    {
        for (int k = 0; k < arr.length; k++)
            for (int m = 0; m < arr[k].length; m++)
                arr[k][m] = 0;
    }

    @Test(timeout = 5000)
    public void slidingOperationsThatMovesNoTiles() {
        int[][] vals = new int[NROWS][NCOLS];

        /* fill in the top row with non repeating values */
        vals[0][0] = 1;
        for (int k = 1; k < NCOLS; k++)
            vals[0][k] = 2 * vals[0][k - 1];
        noMoveSwiper(gameLogic, SlideDirection.LEFT, vals);
        noMoveSwiper(gameLogic, SlideDirection.RIGHT, vals);
        noMoveSwiper(gameLogic, SlideDirection.UP, vals);

        resetValues(vals);
        /* fill in the bottom row with non repeating values */
        vals[NROWS-1][0] = 1;
        for (int k = 1; k < NCOLS; k++)
            vals[NROWS-1][k] = 2 * vals[NROWS-1][k - 1];
        noMoveSwiper(gameLogic, SlideDirection.LEFT, vals);
        noMoveSwiper(gameLogic, SlideDirection.RIGHT, vals);
        noMoveSwiper(gameLogic, SlideDirection.DOWN, vals);

        resetValues(vals);
        /* fill in the left column with non repeating values */
        vals[0][0] = 1;
        for (int k = 1; k < NROWS; k++)
            vals[k][0] = 2 * vals[k - 1][0];
        noMoveSwiper(gameLogic, SlideDirection.LEFT, vals);
        noMoveSwiper(gameLogic, SlideDirection.UP, vals);
        noMoveSwiper(gameLogic, SlideDirection.DOWN, vals);

        resetValues(vals);
        /* fill in the right column with non repeating values */
        vals[0][NCOLS-1] = 1;
        for (int k = 1; k < NROWS; k++)
            vals[k][NCOLS-1] = 2 * vals[k-1][NCOLS-1];
        noMoveSwiper(gameLogic, SlideDirection.RIGHT, vals);
        noMoveSwiper(gameLogic, SlideDirection.UP, vals);
        noMoveSwiper(gameLogic, SlideDirection.DOWN, vals);
    }

    @Test(timeout = 5000)
    public void slideShallReturnFalseWhenBoardUnchanged()
    {
        /* TODO: this test case does not work when getNonEmptyTiles returns
           a new ArrayList on each call
         */
        ArrayList<Cell> one, two;
        for (String pat : NOMOVE_PATTERNS)
        {
            gameLogic.resizeBoard(pat.length(), pat.length(), GAME_GOAL);

            gameLogic.setValues(fillColumns(pat, makeMap(pat)));
            one = (ArrayList<Cell>)gameLogic.getNonEmptyTiles().clone();
            assertFalse("move(UP) should return false when the board does not change",
                    gameLogic.slide(SlideDirection.UP));
            two = (ArrayList<Cell>) gameLogic.getNonEmptyTiles().clone();
            Set<Cell> s1 = new TreeSet<>(one);
            Set<Cell> s2 = new TreeSet<>(two);
            assertTrue("when board is unchanged by move(UP) getNonEmptyTiles() shall return the same list before and after move",
                    s1.equals(s2));

            gameLogic.setValues(fillColumns(reverse(pat), makeMap(pat)));
            one = gameLogic.getNonEmptyTiles();
            assertFalse("move(DOWN) should return false when the board does not change", gameLogic.slide(SlideDirection.DOWN));
            two = (ArrayList<Cell>) gameLogic.getNonEmptyTiles().clone();
            s1 = new TreeSet<>(one);
            s2 = new TreeSet<>(two);
            assertTrue("when board is unchanged by move(DOWN) getNonEmptyTiles() shall return the same list before and after move",
                    s1.equals(s2));

            gameLogic.setValues(fillRows(pat, makeMap(pat)));
            one = gameLogic.getNonEmptyTiles();
            assertFalse ("move(LEFT) should return false when the board does not change", gameLogic.slide(SlideDirection.LEFT));
            two = (ArrayList<Cell>) gameLogic.getNonEmptyTiles().clone();
            s1 = new TreeSet<>(one);
            s2 = new TreeSet<>(two);
            assertTrue("when board is unchanged by move(LEFT) getNonEmptyTiles() shall return the same list before and after move",
                    s1.equals(s2));

            gameLogic.setValues(fillRows(reverse(pat), makeMap(pat)));
            one = gameLogic.getNonEmptyTiles();
            assertFalse ("move(RIGHT) should return false when the board does not change", gameLogic.slide(SlideDirection.RIGHT));
            two = (ArrayList<Cell>) gameLogic.getNonEmptyTiles().clone();
            s1 = new TreeSet<>(one);
            s2 = new TreeSet<>(two);
            assertTrue("when board is unchanged by move(RIGHT) getNonEmptyTiles() shall return the same list before and after move",
                    s1.equals(s2));
        }
    }

    @Test(timeout = 5000)
    public void slidingThatMovesTilesToOppositeSide()
    {
        int[][] vals = new int[NROWS][NCOLS];
        int[][] valsAfter = new int[NROWS][NCOLS];

        /* fill in the top row  */
        for (int k = 0; k < NCOLS; k++) {
            vals[0][k] = nextTileValue();
            valsAfter[NROWS-1][k] = vals[0][k];
        }
        swiper(gameLogic, SlideDirection.DOWN, "pull down", vals, valsAfter);

        resetValues(vals);
        resetValues(valsAfter);
        /* fill in the bottom row  */
        for (int k = 0; k < NCOLS; k++) {
            vals[NROWS-1][k] = nextTileValue();
            valsAfter[0][k] = vals[NROWS-1][k];
        }
        swiper(gameLogic, SlideDirection.UP, "pull up", vals, valsAfter);

        resetValues(vals);
        resetValues(valsAfter);
        /* fill in the leftmost column  */
        for (int k = 0; k < NROWS; k++) {
            vals[k][0] = nextTileValue();
            valsAfter[k][NCOLS-1] = vals[k][0];
        }
        swiper(gameLogic, SlideDirection.RIGHT, "pull right", vals, valsAfter);

        resetValues(vals);
        resetValues(valsAfter);
        /* fill in the rightmost column  */
        for (int k = 0; k < NROWS; k++) {
            vals[k][NCOLS-1] = nextTileValue();
            valsAfter[k][0] = vals[k][NCOLS-1];
        }
        swiper(gameLogic, SlideDirection.LEFT, "pull left", vals, valsAfter);
    }

    private int[][] fillColumns (String template, Map<Character,Integer> valMap)
    {
        final int N = template.length();
        int[][] mat = new int[N][N];
        for (int c = 0; c < N; c++)
            for (int r = 0; r < N; r++) {
                char ch = template.charAt(r);
                int val;
                if (Character.isUpperCase(ch))
                    mat[r][c] = 2 * valMap.get(Character.toLowerCase(ch));
                else
                    mat[r][c] = valMap.get(ch);
            }
        return mat;
    }

    private int[][] fillRows (String template, Map<Character,Integer> valMap)
    {
        final int N = template.length();
        int[][] mat = new int[N][N];
        for (int r = 0; r < N; r++)
            for (int c = 0; c < N; c++) {
                char ch = template.charAt(c);
                int val;
                if (Character.isUpperCase(ch))
                    mat[r][c] = 2 * valMap.get(Character.toLowerCase(ch));
                else
                    mat[r][c] = valMap.get(ch);
            }
        return mat;
    }

    private String reverse (String s)
    {
        StringBuilder t = new StringBuilder();
        for (int k = s.length() - 1; k >= 0; k--)
        {
            t.append(s.charAt(k));
        }
        return t.toString();
    }

    private Map<Character,Integer> makeMap (String pattern)
    {
        Set<Character> chars = new TreeSet<Character>();
        Set<Integer> values = new TreeSet<Integer>();
        Map<Character,Integer> numMap = new TreeMap<Character, Integer>();
        for (Character c : pattern.toCharArray())
        {
            if (c != '.')
                chars.add(c);
        }
        while (values.size() != chars.size())
            values.add(nextTileValue());
        Iterator<Character> chIter = chars.iterator();
        Iterator<Integer> valIter = values.iterator();
        while (chIter.hasNext()) {
            numMap.put(chIter.next(), valIter.next());
        }
        numMap.put('.', 0);
        return numMap;
    }

    private void swipeByPattern (SlideDirection dir, String before, String after)
    {
        final int N = before.length();
        gameLogic.resizeBoard(N, N, GAME_GOAL);
        int[][] beforeMat, afterMat;
        Map<Character,Integer> charToVal = makeMap(before);
        if (dir.equals(SlideDirection.UP) || dir.equals(SlideDirection.DOWN)) {
            beforeMat = fillColumns(before, charToVal);
            afterMat = fillColumns(after, charToVal);
        }
        else {
            beforeMat = fillRows(before, charToVal);
            afterMat = fillRows(after, charToVal);
        }
        swiper(gameLogic, dir, before + " ==> " + after, beforeMat, afterMat);

    }

    @Test(timeout = 5000)
    public void testSwipeUp()
    {
        for (String[] pat : MOVE_PATTERNS) {
            swipeByPattern(SlideDirection.UP, pat[0], pat[1]);
        }
    }

    @Test(timeout = 5000)
    public void testSwipeDown()
    {
        for (String[] pat : MOVE_PATTERNS) {
            swipeByPattern(SlideDirection.DOWN, reverse(pat[0]), reverse(pat[1]));
        }
    }

    @Test(timeout = 5000)
    public void testSwipeLeft()
    {
        for (String[] pat : MOVE_PATTERNS) {
            swipeByPattern(SlideDirection.LEFT, pat[0], pat[1]);
        }
    }

    @Test(timeout = 5000)
    public void testSwipeRight()
    {
        for (String[] pat : MOVE_PATTERNS) {
            swipeByPattern(SlideDirection.RIGHT, reverse(pat[0]), reverse(pat[1]));
        }
    }

    @Test(timeout = 5000)
    public void testIsGameOverAfterReset() throws Exception {
        assertTrue("After initialization isGameOver() should be false",
                gameLogic.getStatus() == GameStatus.IN_PROGRESS);
    }

    @Test(timeout = 5000)
    public void testIsGameBoardNotFull() throws Exception {
        assertTrue("Non-full board: isGameOver() should be false",
                gameLogic.getStatus() == GameStatus.IN_PROGRESS);
    }

    @Test(timeout = 5000)
    public void testIsGameBoardFullMovePossible() throws Exception {
        for (String pat : FULL_NOT_OVER) {
            gameLogic.resizeBoard(pat.length(), pat.length(), GAME_GOAL);
            Map<Character,Integer> valMap = makeMap(pat);
            gameLogic.setValues(fillColumns(pat, valMap));
            assertEquals("Non-full board: movePossible() should be true",
                    GameStatus.IN_PROGRESS,
                    gameLogic.getStatus());
            gameLogic.setValues(fillRows(pat, valMap));
            assertEquals("Non-full board: isGameOver() should be true",
                    GameStatus.IN_PROGRESS,
                    gameLogic.getStatus());
        }
    }

    @Test(timeout = 5000)
    public void testIsGameBoardFullNoMoreMoves() throws Exception {
        StringBuilder sb = new StringBuilder();
        for (int count = 0; count < 10; count++) {
            int N = 2 * gen.nextInt(3) + 2; /* force even size */
            int[][] mat = new int[N][N];
            gameLogic.resizeBoard(N, N, GAME_GOAL);
            sb.setLength(0);
            for (int k = 0; k < N; k++)
                sb.append((char)('a' + k));
            Map<Character, Integer> valmap = makeMap(sb.toString());
            for (int r = 0; r < N/2; r++) {
                for (int c = 0; c < N; c++) {
                    /* alternate the sequence within the two adjacent rows */
                    mat[2*r    ][c    ] = valmap.get((char) ('a' + c));
                    mat[2*r + 1][N-c-1] = valmap.get((char) ('a' + c));
                }
            }
            gameLogic.setValues(mat);
            assertEquals("When no more moves are possible the game should be over",
                    GameStatus.USER_LOST,
                    gameLogic.getStatus());
        }

    }

    @Test(timeout = 1000)
    public void testWinningValue()
    {
        final int N = 10;
        gameLogic.resizeBoard(N, N, GAME_GOAL);
        int[][] mat = new int[N][N];
        for (int k = 0; k < mat.length; k++) {
            for (int m = 0; m < mat[k].length; m++)
                mat[k][m] = GAME_GOAL / 2;
        }
        gameLogic.setValues(mat);
        gameLogic.slide(SlideDirection.DOWN);
        assertEquals(GameStatus.USER_WON, gameLogic.getStatus());
    }

    /* use DOT (.) for empty cells, be sure to limit the letters to a-f
     * because the highest power of two use in the test is 32 */

    private final static String[] NOMOVE_PATTERNS = {
            ".......", "abcde", "a....", "ab...."
    };

    private final static String[][] MOVE_PATTERNS = {
            /* shift only, no merges */
            {".a.b.", "ab..."},
            {"a....b", "ab...."},
            {"a.bc...d", "abcd...."},
            {"..a.b..a..b", "abab......."},

            /* merge and sum, use uppercase letter to indicate doubling */
            {"abcdee","abcdE."},
            {"abbcdde", "aBcDe.."},   /* UNTESTED !!!!! */
            {"a.b.bbc.ccd..", "aBbCcd......."},
            {"abb..", "aB..."},
            {"ab..b", "aB..."},
            {"aa..bb..c", "ABc......"},
            {"..a.a.aa..b", "AAb........"},
            {"..a.b.cc..d", "abCd......."}
    };

    private final static String[] FULL_NOT_OVER = {
            "aabcde", "abbcde", "abccde", "abcdde", "abcdee"
    };
}



