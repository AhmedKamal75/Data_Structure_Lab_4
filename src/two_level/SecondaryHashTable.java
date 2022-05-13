package two_level;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.stream.IntStream;

public class SecondaryHashTable {
    private int p;
    private int a;
    private int b;
    private int collisionNumber = 0;
    private Integer[] hashTable;
    private ArrayList<Integer> tempKeys;

    public SecondaryHashTable() {
        tempKeys = new ArrayList<>();
    }

    public void addElement(Integer element) {
        this.tempKeys.add(element);
    }

    public void buildSecondaryTable(int temp_a) {
        int tableSize = (int) Math.pow(this.tempKeys.size(), 2);
        this.hashTable = new Integer[tableSize];
        this.p = nextPrime(this.hashTable.length);
        set_a_b_randomly();

        while (!this.buildTable()) {
            this.collisionNumber++;
            this.hashTable = new Integer[tableSize];
            set_a_b_randomly();
        }
        this.tempKeys = null;

    }

    private void set_a_b_randomly() {
        Random random = new Random();
        this.a = 1 + random.nextInt(this.p - 1);
        this.b = random.nextInt(this.p);
    }

    private boolean buildTable() {
        for (Integer key : this.tempKeys) {
            int index = hash(key);
            if (this.hashTable[index] != null && !Objects.equals(this.hashTable[index], key)) { // collision detected
                return false;
            } else {
                this.hashTable[index] = key;
            }
        }
        return true;
    }

    private int hash(int key) {
        return ((this.a * key + this.b) % this.p) % this.hashTable.length;
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


    public int getCollisionNumber() {
        return collisionNumber;
    }

    public Integer[] getHashTable() {
        return hashTable;
    }
}
