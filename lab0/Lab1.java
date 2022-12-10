public class Lab1 {
    public static void main(String[] args) {
        int[] c = new int[] { 3, 5, 7, 9, 11, 13, 15, 17, 19 };

        float[] x = new float[19];

        for (int i = 0; i < x.length; i++) {
            x[i] = (int) (Math.random() * 22 - 14);
        }
        double[][] a = new double[9][19];
        // int[] test = { 6, 7, 9, 11, 12, 15 };
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 19; j++) {
                if (c[i] == 7) {

                    a[i][j] = Math.cos(Math
                            .pow((Math.log(Math.abs(x[j])) * (1 - (Math.pow((1 / 3.0 / x[j]) / 1.0, 3)))) / 1.0, 3));

                }
                if (c[i] == 3 || c[i] == 5 || c[i] == 13 || c[i] == 19) {
                    a[i][j] = Math.pow((Math.log(Math.pow(Math.abs(x[j]), 0.5)) + Math.PI) / 1.0,
                            Math.sin(Math.pow(x[j], 2 * x[j])));

                } else {
                    a[i][j] = Math.tan(Math.pow(Math.E, Math.pow(Math.E, Math.tan(x[j]))));

                }

            }
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 19; j++) {
                System.out.printf("%.4f ", a[i][j]);
            }
            System.out.println("");
        }
    }
}
