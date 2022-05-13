package two_level;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class PrimaryHashTable {
    private final int p;
    private int a;
    private int b;

    private int collisionNumber = 0;
    private SecondaryHashTable[] hashTable;

    public PrimaryHashTable(List<Integer> list, int mapIntoSize) {
        int setSize = list.size();
        this.p = nextPrime(setSize);
        set_a_b_randomly();
        this.hashTable = new SecondaryHashTable[mapIntoSize];
        buildPrimaryTable(list);
        int i = 0;
        for (SecondaryHashTable table : this.hashTable) {
            table.buildSecondaryTable(i);
            i++;
        }
    }
    private void buildPrimaryTable(List<Integer> list) {
        for (int i = 0; i < this.hashTable.length; i++) {
            this.hashTable[i] = new SecondaryHashTable();
        }
        for (int key :
                list) {
            int index = hash(key);
            this.hashTable[index].addElement(key);
        }
    }

    private int hash(int key) {
        return ((this.a * key + this.b) % this.p) % this.hashTable.length;
    }

    private void set_a_b_randomly() {
        Random random = new Random();
        this.a = 1 + random.nextInt(this.p - 1);
        this.b = random.nextInt(this.p);
    }

    private int nextPrime(int number) {
        while (true) {
            if (isPrime(number)) {
                return number;
            }
            number++;
        }
    }

    private boolean isPrime(int number) {
        return IntStream.rangeClosed(2, (int) (Math.sqrt(number)))
                .allMatch(n -> number % n != 0);
    }

    public int getTotalCollisionNumber() {
        int sum = 0;
        for (SecondaryHashTable table: this.hashTable){
            sum += table.getCollisionNumber();
        }
        return sum;
    }

    public SecondaryHashTable[] getHashTable() {
        return hashTable;
    }
}
