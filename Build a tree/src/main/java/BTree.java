
public class BTree {

    int[] array0 = new int[2];
    int[] array1 = new int[2];
    int[] array2 = new int[2];
    int[] array3 = new int[2];
    int[] array4 = new int[2];
    int[] array5 = new int[2];
    int[] array6 = new int[2];
    int count = 0;


    public void insert(int key){
        if(array0[0] == 0){
            array0[0] = key;
            count++;
        }
        else if (array0[1] == 0){
            array0[1] = key;
            orderArray(array0);
            count++;
        }
        else{
            if(key < array0[0]){
                insert1(key);
            }
            else if (key > array0[0] && key < array0[1]){
                insert1(array0[0]);
                array0[0] = key;
            }
            else{
                insert4(key);
            }
        }
    }

    public void insert1(int key){
        if(array1[0] == 0){
            array1[0] = key;
            count++;
        }
        else if (array1[1] == 0){
            array1[1] = key;
            orderArray(array1);
            count++;
        }
        else{
            if(key < array1[0]){
                insert2(key);
            }
            else if (key > array1[0] && key < array1[1]){
                insert2(array1[0]);
                array1[0] = key;
            }
            else{
                insert3(key);
            }
        }
    }

    public void insert2(int key){
        if(array2[0] == 0){
            array2[0] = key;
            count++;
        }
        else if (array2[1] == 0){
            array2[1] = key;
            orderArray(array2);
            count++;
        }
    }

    public void insert3(int key){
        if(array3[0] == 0){
            array3[0] = key;
            count++;
        }
        else if (array3[1] == 0){
            array3[1] = key;
            orderArray(array3);
            count++;
        }
    }
    public void insert4(int key){
        if(array4[0] == 0){
            array4[0] = key;
            count++;
        }
        else if (array4[1] == 0){
            array4[1] = key;
            orderArray(array4);
            count++;
        }
        else{
            if(key < array4[0]){
                insert5(key);
            }
            else if (key > array4[0] && key < array4[1]){
                insert5(array4[0]);
                array4[0] = key;
            }
            else{
                insert6(key);
            }
        }
    }
    public void insert5(int key){
        if(array5[0] == 0){
            array5[0] = key;
            count++;
        }
        else if (array5[1] == 0){
            array5[1] = key;
            orderArray(array5);
            count++;
        }
    }
    public void insert6(int key){
        if(array6[0] == 0){
            array6[0] = key;
            count++;
        }
        else if (array6[1] == 0){
            array6[1] = key;
            orderArray(array6);
            count++;
        }
    }


    public void orderArray(int[] array){
        //order from min to max
        if(array[0] >= array[1]){
            int tmp = array[0];
            array[0] = array[1];
            array[1] = tmp;
        }
    }

    public int[] preOrder(){
        int[] Barray = new int[14];
        Barray[0] = array0[0];
        Barray[1] = array0[1];
        Barray[2] = array1[0];
        Barray[3] = array1[1];
        Barray[4] = array2[0];
        Barray[5] = array2[1];
        Barray[6] = array3[0];
        Barray[7] = array3[1];
        Barray[8] = array4[0];
        Barray[9] = array4[1];
        Barray[10] = array5[0];
        Barray[11] = array5[1];
        Barray[12] = array6[0];
        Barray[13] = array6[1];

        return Barray;
    }

    public int countNodes(){
        return count;
    }
}
