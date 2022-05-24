package multithreading;

import java.util.Arrays;

public class App {
    private static final int SIZE = 10_000_000;
    private static final int HALF = SIZE / 2;

    public static void main(String[] args) {
        long startSequenceCalculate = System.currentTimeMillis();
        float[] arrAll = initArr();

        sequenceArr(initArr());
        long finishSequenceCalculate = System.currentTimeMillis() - startSequenceCalculate;
        System.out.println("Seq-ce Arr: " + finishSequenceCalculate + " ms");

        long startMergeCalculate = System.currentTimeMillis();
        float[] leftArr = Arrays.copyOfRange(arrAll, 0, HALF);
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < leftArr.length; i++) {
                leftArr[i] = (float) (leftArr[i] * Math.sin(0.2f + (float) (i / 5)) * Math.cos(0.2f + (float) (i / 5)) * Math.cos(0.4f + (float) (i / 2)));
            }
        });

        float[] rightArr = Arrays.copyOfRange(arrAll, HALF, SIZE);
        Thread t2 = new Thread(() -> {
            int currentIndex = HALF;
            for (int i = 0; i < rightArr.length; i++) {
                rightArr[i] = (float) (rightArr[i] * Math.sin(0.2f + (float) (currentIndex / 5)) * Math.cos(0.2f + (float) (currentIndex / 5)) * Math.cos(0.4f + (float) (currentIndex / 2)));
                currentIndex++;
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        mergeArr(leftArr, rightArr, arrAll);

        long finishMergeCalculate = System.currentTimeMillis() - startMergeCalculate;
        System.out.println("Mer-ge Arr: " + finishMergeCalculate + " ms");
    }

    private static float[] initArr() {
        float[] arrAll = new float[SIZE];
        Arrays.fill(arrAll, 1.0f);
        return arrAll;
    }

    private static float[] sequenceArr(float[] sequenceArr) {
        for (int i = 0; i < sequenceArr.length; i++) {
            sequenceArr[i] = (float) (sequenceArr[i] * Math.sin(0.2f + (float) (i / 5)) * Math.cos(0.2f + (float) (i / 5)) * Math.cos(0.4f + (float) (i / 2)));
        }
        return sequenceArr;
    }

    private static float[] mergeArr(float[] leftArr, float[] rightArr, float[] arrAll) {
        System.arraycopy(leftArr, 0, arrAll, 0, HALF);
        System.arraycopy(rightArr, 0, arrAll, HALF, HALF);
        return arrAll;
    }
}
