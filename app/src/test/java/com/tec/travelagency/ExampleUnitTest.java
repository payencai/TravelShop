package com.tec.travelagency;

import android.util.Log;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {

//        int[] arr = new int[]{12, 23, 2, 454, 234, 13, 4, 52, 34};
////        int count = selectionSort(arr);
//        int count = insertionSort2(arr);
//        for (int i : arr) {
//            System.out.printf(" " + i);
//        }
//
//        System.out.printf("\n" + count);


//        String s = "s,sdf,sdf,sf,";
//        String[] split = s.split(",");
//        System.out.println("%d:" + split.length);

        String s = dateToWeek("2018-9-1");
        System.out.printf(s);

    }

    public static int dayForWeek(String pTime) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        Date parse = format.parse(pTime);
        System.out.println(parse.toString());
        c.setTime(parse);
        int dayForWeek = 0;
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            dayForWeek = 7;
        } else {
            dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        }
        return dayForWeek;
    }


    public static String dateToWeek(String datetime) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
        Calendar cal = Calendar.getInstance(); // 获得一个日历
        Date datet = null;
        try {
            datet = f.parse(datetime);
            cal.setTime(datet);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    int selectionSort(int[] arr) {
        int len = arr.length;
        int count = 0;
        int minIndex,temp ;
        for (int i = 0; i < len - 1; i++) {
            minIndex=i;
            for (int j = i + 1; j < len; j++) {
                if (arr[i] > arr[j]) {
                   minIndex=j;
                }
                count++;
            }
            temp = arr[i];
            arr[i] = arr[minIndex];
            arr[minIndex] = temp;
        }

        return count;
    }

    int selectionSort2(int arr[]) {
        int len = arr.length;
        int count=0;
        int minIndex, temp;
        for (int i = 0; i < len - 1; i++) {
            minIndex = i;
            for (int j = i + 1; j < len; j++) {
                if (arr[j] < arr[minIndex]) {     // 寻找最小的数
                    minIndex = j;                 // 将最小数的索引保存
                }
            }
            temp = arr[i];
            arr[i] = arr[minIndex];
            arr[minIndex] = temp;
        }
        return count;
    }

    int insertionSort(int arr[]) {
        int len = arr.length;
        int count=0;
        int preIndex, current;
        for (int i = 1; i < len; i++) {
            preIndex = i - 1;
            current = arr[i];
            while (preIndex >= 0 && arr[preIndex] > current) {
                arr[preIndex + 1] = arr[preIndex];
                preIndex--;
                count++;
            }
            arr[preIndex + 1] = current;
        }
        return count;
    }

    int insertionSort2(int arr[]) {
        int count=0;

        int len = arr.length;
        int index,current;
        for (int i = 1; i < len; i++) {

            index=i-1;
            current = arr[i];
            while (index >= 0 && arr[index] > current) {
                arr[index + 1] = arr[index];
                index--;
                count++;
            }
            arr[index+1]=current;
        }

        return count;
    }
}
