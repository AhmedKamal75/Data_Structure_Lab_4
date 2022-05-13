import double_space.QuadradicSpace;
import two_level.PrimaryHashTable;
import two_level.SecondaryHashTable;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        generateDictionary(1000,"dictionary.txt");
        ArrayList<Integer> list = getInput("dictionary.txt");
        System.out.println("First Method: ");
        QuadradicSpace quadradicSpace = new QuadradicSpace(list,(int) Math.pow(list.size(), 2));
        System.out.println("Collision Number = " + quadradicSpace.getCollisionNumber());
        System.out.println(Arrays.toString(quadradicSpace.getHashTable()));
        System.out.println("#######################");
        System.out.println("Second Method: ");
        PrimaryHashTable primaryHashTable = new PrimaryHashTable(list, list.size() / 4);
        for (SecondaryHashTable table : primaryHashTable.getHashTable()){
            System.out.println(Arrays.toString(table.getHashTable()));
        }
        System.out.println("Collision Number = " + primaryHashTable.getTotalCollisionNumber());
    }


    private static ArrayList<Integer> getInput(String filePath) {
        BufferedReader reader = null;
        ArrayList<Integer> list = new ArrayList<>();
        try {
            reader = new BufferedReader(new FileReader(filePath));
            String[] strings = reader.readLine().split(",");
            for (String string :
                    strings) {
                list.add(Integer.parseInt(string));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    private static void generateDictionary(int n, String filePath) {
        ArrayList<Integer> list = new ArrayList<>(n);
        ArrayList<Integer> tempList = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            tempList.add(i);
        }
        Random rand = new Random();
        while (!tempList.isEmpty()) {
            int random_index = rand.nextInt(n) % tempList.size();
            int number = tempList.remove(random_index);
            list.add(number);
        }
        String s = list.toString().replace("[", "").replace("]", "").replace(" ", "");
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            writer.write(s);
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}