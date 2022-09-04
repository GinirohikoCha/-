package control;

/**
 * @author GinirohikoCha
 * @since 2022/9/4
 */
public class BreakOuterLoop {

    public static void main(String[] args) {
        iLoop:
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                System.out.println(i+":"+j);
                if(j == 9) {
                    break iLoop;
                }
            }
        }
    }

}
