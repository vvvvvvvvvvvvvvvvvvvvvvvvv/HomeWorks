package com.company.lesson5;

public class HomeWork {
    static final int size = 10000000;
    static final int h = size / 2;
    static float[] arr = new float[size];
    public static void main(String[] args) {
        System.out.println(calculate());
        System.out.println(calculate2());
    }

    private static long calculate(){
        var a = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5)
                    * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        return System.currentTimeMillis() - a;

    }
    private static long calculate2(){
        var a = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            arr[i] = 1;
        }
        float[] firstArray = new float[h];
        float[] secondArray = new float[h];
        System.arraycopy(arr, 0, firstArray, 0, h);
        System.arraycopy(arr, h, secondArray, 0, h);
        Thread thread1 = new Thread(() ->{ for (int i = 0; i < h; i++) {
            firstArray[i] = (float)(arr[i] * Math.sin(0.2f + i / 5)
                    * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }});
        thread1.start();
        Thread thread2 = new Thread(() ->{ for (int i = 0; i < h; i++) {
            secondArray[i] = (float)(arr[i] * Math.sin(0.2f + i / 5)
                    * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }});
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.arraycopy(firstArray, 0, arr, 0, h);
        System.arraycopy(secondArray, 0, arr, h, h);
        return System.currentTimeMillis() - a;
    }

}
