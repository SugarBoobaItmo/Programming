import java.util.Arrays;
import java.util.stream.Collectors;

public class Lab1 {
  public static void main(String[] args) {
    long[] c = new long[20];
    for (int i = 15; i > 3; i = i - 1) {
      c[i] = (short) (i);
    }
    double[] x = new double[13];

    for (int i = 0; i < x.length; i++) {
      x[i] = (int) (Math.random() * 19 - 7);

    }
    double[][] a = new double[20][13];
    int[] test = { 6, 7, 9, 11, 12, 15 };
    for (int i = 0; i < 20; i++) {
      for (int j = 0; j < 13; j++) {
        if (c[i] == 8) {
          if (x[j] == 0) {
            a[i][j] = Math
                .tan(Math.pow(((Math.pow((2 * 1) / 1.0, 3)) / (Math.log(Math.abs(1)) - 1)) / 1.0, Math.tan(1)));

            continue;
          }
          a[i][j] = Math
              .tan(Math.pow(((Math.pow((2 * x[j]) / 1.0, 3)) / (Math.log(Math.abs(x[j])) - 1)) / 1.0, Math.tan(x[j])));

        }
        if (Arrays.stream(test)
            .boxed()
            .collect(Collectors.toSet())
            .contains((int) c[i])) {

          a[i][j] = Math.pow((Math.pow(Math.E, Math.tan(x[j]))) / 1.0,
              (3 / 4.0 * Math.pow((Math.pow((x[j] - 1), 2)) / 1.0, 1 / 3.0)));

        } else {
          a[i][j] = Math
              .cos(Math.pow(Math.pow(Math.atan(((x[j] + 2.5) / 17) / 1.0), (1 - 3 / (x[j] + 1)) / 1 / 3) / 2.0, 3));

        }

      }
    }
    for (int i = 0; i < 20; i++) {
      for (int j = 0; j < 13; j++) {
        System.out.printf("%.2f ", a[i][j]);
      }
      System.out.println("");
    }
  }
}