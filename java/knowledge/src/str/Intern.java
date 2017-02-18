package str;

/**
 * Created by zzt on 2/5/17.
 * <p>
 * <h3></h3>
 */
public class Intern {

    public static void main(String[] args) {
        //        String s = "1" + "2";
        //        String s2 = "1" + 3;
        //        String s3 = "1" + new String("4");
        //        test(s);
        //        test(s2);
        //        test(s3);
        //        String s1 = new StringBuilder("aaa").append("a").toString();
        //        test(s1);
        //                String s4 = new String("s") + new String("1");
        //                test(s4);
        //                test(new String("s2"));
        //                test("1");
        //        test("" + new String("6"));
        //        String special = new String(new char[]{'a', 'z'});
        //        test(special);
        //        allTwoChar();
        blogCode();
    }

    private static void blogCode() {
        String s1 = new String("s1");
        // notice this is `==`, is to compare address
        System.out.println(s1.intern() == s1);
        String s2 = new String("s") + new String("2");
        // notice this is `==`, is to compare address
        System.out.println(s2.intern() == s2);
        String s3 = "s3";
        // notice this is `==`, is to compare address
        System.out.println(s3.intern() == s3);
    }

    private static void allTwoChar() {
        for (char i = 'a'; i <= 'z'; i++) {
            for (char j = 'a'; j <= 'z'; j++) {
                test(new String(new char[]{i, j}));
            }
        }
    }

    public static void test(String s) {
        System.out.println(s + ":" + (s.intern() == s));
    }


}
