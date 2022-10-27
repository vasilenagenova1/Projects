import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("My String is: ");
        String str = sc.nextLine();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(str);
        String reverse = String.valueOf(stringBuilder.reverse());

        System.out.printf("My reverse String is: %s", reverse);
    }
}
