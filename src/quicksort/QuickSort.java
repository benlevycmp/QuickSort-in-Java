package quicksort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class QuickSort {

    public static void sortInPlace(List<Integer> list) {
        int medianIndex = ninther(list);
        int length = list.size();
        Collections.swap(list, length / 2, medianIndex);
        medianIndex = length / 2;
        int median = list.get(medianIndex);

        int j = length - 1;
        for (int i = 0; i < medianIndex; ++i) {
            if (list.get(i) > median) {
                while (j > medianIndex && list.get(j) > median) {
                    j--;
                }
                if (j > medianIndex) {
                    Collections.swap(list, i, j);
                }else{
                    Collections.rotate(list.subList(i,medianIndex+1),-1);
                    medianIndex--;
                    i--;
                }
            }
        }
        while(j > medianIndex) {
            if(list.get(j) < median) {
                Collections.rotate(list.subList(medianIndex,j+1),1);
                medianIndex++;
                j++;
            }
            j--;
        }

        if(list.size() > 3){
            sortInPlace(list.subList(0,medianIndex));
            sortInPlace(list.subList(medianIndex,list.size()));
        }
    }

    public static List<Integer> copySort(List<Integer> list) {
        if (list.size()<2){
            return list;
        }
        boolean alternator = true;
        List<Integer> newList = new ArrayList<>(list.size());
        newList.addAll(list);
        var medianIndex = ninther(list);
        var median = list.get(medianIndex);
        var j = 0;
        var k = list.size()-1;
        for (var i: list){
            if(i<median){
                newList.set(j, i);
                j++;
            }else if(i>median){
                newList.set(k, i);
                k--;
            }else{
                if (alternator){
                    newList.set(j, i);
                    j++;
                    alternator = false;
                }else{
                    newList.set(k, i);
                    k--;
                    alternator = true;
                }
            }
        }
        if(list.size() > 2){
            return Stream.concat(copySort(newList.subList(0, j)).stream(), copySort(newList.subList(j, newList.size())).stream()).toList();
        }
        return newList;
    }

    private static int ninther(List<Integer> list) {
        if(list.size()<9){
            return mo3(list);
        }
        int size = list.size();
        var shortList = new int[3];
        shortList[0] = mo3(list.subList(0, size / 3));
        shortList[1] = mo3(list.subList(size / 3, (size / 3) * 2)) + size / 3;
        shortList[2] = mo3(list.subList((size / 3) * 2, size)) + size / 3 * 2;


        return shortList[mo3(Arrays.asList(list.get(shortList[0]), list.get(shortList[1]), list.get(shortList[2])))];
    }

    private static int mo3(List<Integer> list) {
        var first = list.getFirst();
        var middle = list.get(list.size() / 2);
        var last = list.getLast();

        if (middle > first) {
            if (middle < last) {
                return list.size() / 2;
            }
            if (first > last) {
                return 0;
            }
            return list.size() - 1;
        }
        if (first < last) {
            return 0;
        }
        if (middle > last) {
            return list.size() / 2;
        }
        return list.size() - 1;
    }
}
