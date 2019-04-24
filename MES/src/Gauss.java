public class Gauss {

    private double[] tmpP;
    private int n;
    private double[][] tmp;

    public Gauss(double[][] matrixH, double[] vectorP, int n){
        this.tmpP = vectorP;
        this.n = n;
        tmp = new double[n][n+1];

        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                tmp[i][j] = matrixH[i][j];
            }
            tmp[i][n] = vectorP[i];
        }
    }

    public double[] gaussElimination(){
        for (int p = 0; p < n; p++) {

            int max = p;
            for (int i = p + 1; i < n; i++) {
                if (Math.abs(tmp[i][p]) > Math.abs(tmp[max][p])) {
                    max = i;
                }
            }
            double[] tmp1 = tmp[p];
            tmp[p] = tmp[max];
            tmp[max] = tmp1;

            double t =  tmpP[p];
            tmpP[p] =  tmpP[max];
            tmpP[max] = t;

            for (int i = p + 1; i < n; i++) {
                double alfa = tmp[i][p] / tmp[p][p];
                tmpP[i] -= alfa *  tmpP[p];
                for (int j = p; j < n; j++) {
                    tmp[i][j] -= alfa * tmp[p][j];
                }
            }
        }

        double[] x = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            double sum = 0.0;
            for (int j = i + 1; j < n; j++) {
                sum += tmp[i][j] * x[j];
            }
            x[i] = ( tmpP[i] - sum) / tmp[i][i];
        }
        return x;
    }
}