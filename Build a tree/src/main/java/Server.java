

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

public class Server implements Observer{
    ServerSocket ss;
    Socket socket;  
    DataInputStream dinput;
    DataOutputStream doutput;
    int jugadores;
    int tiempo = 0;
    boolean running = true;
    boolean active_challenge = false;
    boolean avl_challenge = false;
    boolean bst_challenge = false;
    boolean btree_challenge = false;
    boolean splay_challenge = false;
    int challenge_starter;
    boolean challenge_cooldown = false;
    long challengecooldownstartTime;
    long challengecooldownEndTime;
    Random rand = new Random();
    int token_generator;
    Random rand_token = new Random();
    Random rand_token_value = new Random();
    Token token = new Token();
    boolean token_cooldown = false;
    long tokencooldownstartTime;
    long tokencooldownEndTime;
    AVL avl1;
    AVL avl2;
    AVL avl3;
    AVL avl4;
    BST bst1;
    BST bst2;
    BST bst3;
    BST bst4;
    BTree btree1; 
    BTree btree2;
    BTree btree3;
    BTree btree4;
    Splay splay1;
    Splay splay2;
    Splay splay3;
    Splay splay4;
    long challengestartTime;
    boolean startsending = false;
    boolean winnersent = false;
    
    //lista de nodos que están activos (si existen y ´tienen valor)
    public void start() {
        
        /*
        bst1 = new BST();
        bst1.insert(10);

        bst1.preorder();
        for (int i = 0; i < 15; i++){
            System.out.println(bst1.array[i]);}

        bst1.insert(11);
        bst1.preorder();
        for (int i = 0; i < 15; i++){
            System.out.println(bst1.array[i]);}
        */
        /*
        btree1 = new BTree();
        btree1.insert(10);
        btree1.insert(15);
        btree1.insert(2);
        btree1.insert(12);
        btree1.insert(30);
        btree1.insert(8);

        JsonNode node = Json.toJson(btree1.preOrder());
        try {
            String nodos = Json.generateString(node, false);
            System.out.println(nodos);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        */

        
        try{
            ss = new ServerSocket(5000);            
            System.out.println("Connection accepted" + "\n");
            socket = ss.accept();
            dinput = new DataInputStream(socket.getInputStream());
            doutput = new DataOutputStream(socket.getOutputStream());
            HiloServer hiloserver = new HiloServer(dinput,socket);
            hiloserver.addObserver(this);
            Thread t = new Thread(hiloserver);
            t.start();
            
        }
        catch (Exception e){
            //Exceptions
        }
        while(!startsending){
            System.out.println("not sending");
        }
        while (true){
            //while loop para challenges
            while (active_challenge != true){
                if (challenge_cooldown != true){
                    //cada loop, challenge starter se redefine como un número aleatorio entre 0 y 10000
                    challenge_starter = rand.nextInt(10001);
                    //cuando el número aleatorio es igual a 10000, se inicia un challenge de un árbol aleatorio
                    if (challenge_starter == 10000){
                        //create_challenge(rand.nextInt(4));
                        create_challenge(1);
                        active_challenge = true;
                        challengestartTime = System.nanoTime();
                        challenge_cooldown = true;
                        System.out.print("Challenge started" + "\n");

                    }    
                }
                else {
                    challengecooldownEndTime = System.nanoTime(); 
                    if (((challengecooldownEndTime - challengecooldownstartTime)/1000000000) > 3){
                        challenge_cooldown = false;     
                    }
                }
            }

            //while loop para terminar el challenge cuando haya pasado 1 minuto, y generar tokens aleatoriamente
            while (active_challenge == true){
                long challengeEndTime = System.nanoTime(); 
                //System.out.print("seconds passed:" + test );
                if (((challengeEndTime - challengestartTime)/1000000000) > 62){
                    System.out.print("Challenge ended" + "\n");
                    active_challenge = false;
                    challengecooldownstartTime = System.nanoTime();
                    if (bst_challenge == true){
                        bst_challenge = false;
                        if (winnersent != true){
                            if (bst1.countNodes() >= bst2.countNodes()){
                                if (bst1.countNodes() >= bst3.countNodes()){
                                    if (bst1.countNodes() >= bst4.countNodes()){
                                        enviar_mensaje("Ganador"+"#"+"Jugador1"+"#"+"-1",socket);
                                        winnersent = true;
                                    }
                                }
                            }
                        }

                        if (winnersent != true){
                            if (bst2.countNodes() >= bst1.countNodes()){
                                if (bst2.countNodes() >= bst3.countNodes()){
                                    if (bst2.countNodes() >= bst4.countNodes()){
                                        enviar_mensaje("Ganador"+"#"+"Jugador2"+"#"+"-1",socket);
                                        winnersent = true;
                                    }
                                }
                            }
                        }

                        if (winnersent != true){
                            if (bst3.countNodes() >= bst1.countNodes()){
                                if (bst3.countNodes() >= bst2.countNodes()){
                                    if (bst3.countNodes() >= bst4.countNodes()){
                                        enviar_mensaje("Ganador"+"#"+"Jugador3"+"#"+"-1",socket);
                                        winnersent = true;
                                    }
                                }
                            }
                        }

                        if (winnersent != true){
                            if (bst4.countNodes() >= bst1.countNodes()){
                                if (bst4.countNodes() >= bst2.countNodes()){
                                    if (bst4.countNodes() >= bst3.countNodes()){
                                        enviar_mensaje("Ganador"+"#"+"Jugador4"+"#"+"-1",socket);
                                        winnersent = true;
                                    }
                                }
                            }
                        }

                        reset_BST(bst1);
                        reset_BST(bst2);
                        reset_BST(bst3);
                        reset_BST(bst4);
                        winnersent = false;
                    }                    
                    else{ 
                        if (avl_challenge == true){
                            avl_challenge = false;
                            if (winnersent != true){
                                if (avl1.countNodes() >= avl2.countNodes()){
                                    if (avl1.countNodes() >= avl3.countNodes()){
                                        if (avl1.countNodes() >= avl4.countNodes()){
                                            enviar_mensaje("Ganador"+"#"+"Jugador1"+"#"+"-1",socket);
                                            winnersent = true;
                                        }
                                    }
                                }
                            }

                            if (winnersent != true){
                                if (avl2.countNodes() >= avl1.countNodes()){
                                    if (bst2.countNodes() >= avl3.countNodes()){
                                        if (avl2.countNodes() >= avl4.countNodes()){
                                            enviar_mensaje("Ganador"+"#"+"Jugador2"+"#"+"-1",socket);
                                            winnersent = true;
                                        }
                                    }
                                }
                            }

                            if (winnersent != true){
                                if (avl3.countNodes() >= avl1.countNodes()){
                                    if (avl3.countNodes() >= avl2.countNodes()){
                                        if (avl3.countNodes() >= avl4.countNodes()){
                                            enviar_mensaje("Ganador"+"#"+"Jugador3"+"#"+"-1",socket);
                                            winnersent = true;
                                        }
                                    }
                                }
                            }

                            if (winnersent != true){
                                if (avl4.countNodes() >= avl1.countNodes()){
                                    if (avl4.countNodes() >= avl2.countNodes()){
                                        if (avl4.countNodes() >= avl3.countNodes()){
                                            enviar_mensaje("Ganador"+"#"+"Jugador4"+"#"+"-1",socket);
                                            winnersent = true;
                                        }
                                    }
                                }
                            }

                            reset_AVL(avl1);
                            reset_AVL(avl2);
                            reset_AVL(avl3);
                            reset_AVL(avl4);
                            winnersent = false;
                        }            
                        else{
                            if (btree_challenge == true){
                                btree_challenge = false;
                                if (winnersent != true){
                                    if (btree1.countNodes() >= btree2.countNodes()){
                                        if (btree1.countNodes() >= btree3.countNodes()){
                                            if (btree1.countNodes() >= btree4.countNodes()){
                                                enviar_mensaje("Ganador"+"#"+"Jugador1"+"#"+"-1",socket);
                                                winnersent = true;
                                            }
                                        }
                                    }
                                }

                                if (winnersent != true){
                                    if (btree2.countNodes() >= btree1.countNodes()){
                                        if (btree2.countNodes() >= btree3.countNodes()){
                                            if (btree2.countNodes() >= btree4.countNodes()){
                                                enviar_mensaje("Ganador"+"#"+"Jugador2"+"#"+"-1",socket);
                                                winnersent = true;
                                            }
                                        }
                                    }
                                }

                                if (winnersent != true){
                                    if (btree3.countNodes() >= btree1.countNodes()){
                                        if (btree3.countNodes() >= btree2.countNodes()){
                                            if (btree3.countNodes() >= btree4.countNodes()){
                                                enviar_mensaje("Ganador"+"#"+"Jugador3"+"#"+"-1",socket);
                                                winnersent = true;
                                            }
                                        }
                                    }
                                }

                                if (winnersent != true){
                                    if (btree4.countNodes() >= btree1.countNodes()){
                                        if (btree4.countNodes() >= btree2.countNodes()){
                                            if (btree4.countNodes() >= btree3.countNodes()){
                                                enviar_mensaje("Ganador"+"#"+"Jugador4"+"#"+"-1",socket);
                                                winnersent = true;
                                            }
                                        }
                                    }
                                }

                                reset_BTree(btree1);
                                reset_BTree(btree2);
                                reset_BTree(btree3);
                                reset_BTree(btree4);
                                winnersent = false;
                            }
                            else{
                                splay_challenge = false;
                                if (winnersent != true){
                                    if (splay1.countNodes() >= splay2.countNodes()){
                                        if (splay1.countNodes() >= splay3.countNodes()){
                                            if (splay1.countNodes() >= splay4.countNodes()){
                                                enviar_mensaje("Ganador"+"#"+"Jugador1"+"#"+"-1",socket);
                                                winnersent = true;
                                            }
                                        }
                                    }
                                }

                                if (winnersent != true){
                                    if (splay2.countNodes() >= splay1.countNodes()){
                                        if (splay2.countNodes() >= splay3.countNodes()){
                                            if (splay2.countNodes() >= splay4.countNodes()){
                                                enviar_mensaje("Ganador"+"#"+"Jugador2"+"#"+"-1",socket);
                                                winnersent = true;
                                            }
                                        }
                                    }
                                }

                                if (winnersent != true){
                                    if (splay3.countNodes() >= splay1.countNodes()){
                                        if (splay3.countNodes() >= splay2.countNodes()){
                                            if (splay3.countNodes() >= splay4.countNodes()){
                                                enviar_mensaje("Ganador"+"#"+"Jugador3"+"#"+"-1",socket);
                                                winnersent = true;
                                            }
                                        }
                                    }
                                }

                                if (winnersent != true){
                                    if (splay4.countNodes() >= splay1.countNodes()){
                                        if (splay4.countNodes() >= splay2.countNodes()){
                                            if (splay4.countNodes() >= splay3.countNodes()){
                                                enviar_mensaje("Ganador"+"#"+"Jugador4"+"#"+"-1",socket);
                                                winnersent = true;
                                            }
                                        }
                                    }
                                }

                                reset_Splay(splay1);
                                reset_Splay(splay2);
                                reset_Splay(splay3);
                                reset_Splay(splay4);
                                winnersent = false;
                            }
                        }
                    }                                     
                }
                else{
                    if (token_cooldown != true){
                        token_generator = rand_token.nextInt(20000001);
                        if (token_generator == 20000000){
                            System.out.print("token generated" + "\n");
                            token_cooldown = true;
                            tokencooldownstartTime = System.nanoTime();
                            make_token(rand_token.nextInt(8),rand_token_value.nextInt(101));


                        }
                    } else {                        
                            tokencooldownEndTime = System.nanoTime(); 
                            if (((tokencooldownEndTime - tokencooldownstartTime)/1000000000) > 0.01){
                                token_cooldown = false;  
                            }                                                                      
                    }
                }
            }
        }
       
    }    
    
    public void modify_tree(Token token, int player){
        switch(token.get_tipo()){
            case "BST":
                if (bst_challenge == true){
                    switch(player){
                        case 1:
                            System.out.println("BST");
                            bst1.insert(token.get_valor());
                            bst1.preorder();

                            try {
                                JsonNode bstnode1 = Json.toJson(bst1.array);
                                System.out.println("Dentro del try");
                                String bstnodos1 = Json.generateString(bstnode1, false);
                                enviar_mensaje("Arbol" + "#Jugador1#" + bstnodos1,socket);
                                System.out.println(bstnodos1);
                            } catch (JsonProcessingException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 2:
                            bst2.insert(token.get_valor());
                            bst2.preorder();
                            try {
                                JsonNode bstnode2 = Json.toJson(bst2.array);
                                String bstnodos2 = Json.generateString(bstnode2, false);
                                enviar_mensaje("Arbol" + "#Jugador2#" + bstnodos2,socket);
                                System.out.println(bstnodos2);
                            } catch (JsonProcessingException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 3:
                            bst3.insert(token.get_valor());
                            bst3.preorder();
                            try {
                                JsonNode bstnode3 = Json.toJson(bst3.array);
                                String bstnodos3 = Json.generateString(bstnode3, false);
                                enviar_mensaje("Arbol" + "#Jugador3#" + bstnodos3,socket);
                                System.out.println(bstnodos3);
                            } catch (JsonProcessingException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 4:
                            bst4.insert(token.get_valor());
                            bst4.preorder();
                            try {
                                JsonNode bstnode4 = Json.toJson(bst4.array);
                                String bstnodos4 = Json.generateString(bstnode4, false);
                                enviar_mensaje("Arbol" + "#Jugador4#" + bstnodos4,socket);
                                System.out.println(bstnodos4);
                            } catch (JsonProcessingException e) {
                                e.printStackTrace();
                            }
                            break;
                    }
                    break;
                }
                else { if (avl_challenge == true){
                    switch(player){
                        case 1:
                            reset_AVL(avl1);
                            avl1 = new AVL();
                            avl1.preorder();
                            try {
                                JsonNode avlnode1 = Json.toJson(avl1.array);
                                String avlnodos1 = Json.generateString(avlnode1, false);
                                enviar_mensaje("Arbol" + "#Jugador1#" + avlnodos1,socket);
                                System.out.println(avlnodos1);
                            } catch (JsonProcessingException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 2:
                            reset_AVL(avl2);
                            avl2 = new AVL();
                            avl2.preorder();
                            try {
                                JsonNode avlnode2 = Json.toJson(avl2.array);
                                String avlnodos2 = Json.generateString(avlnode2, false);
                                enviar_mensaje("Arbol" + "#Jugador2#" + avlnodos2,socket);
                                System.out.println(avlnodos2);
                            } catch (JsonProcessingException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 3:
                            reset_AVL(avl3);
                            avl3 = new AVL();
                            avl3.preorder();
                            try {
                                JsonNode avlnode3 = Json.toJson(avl3.array);
                                String avlnodos3 = Json.generateString(avlnode3, false);
                                enviar_mensaje("Arbol" + "#Jugador3#" + avlnodos3,socket);
                                System.out.println(avlnodos3);
                            } catch (JsonProcessingException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 4:
                            reset_AVL(avl4);
                            avl4 = new AVL();
                            avl4.preorder();
                            try {
                                JsonNode avlnode4 = Json.toJson(avl4.array);
                                String avlnodos4 = Json.generateString(avlnode4, false);
                                enviar_mensaje("Arbol" + "#Jugador4#" + avlnodos4,socket);
                                System.out.println(avlnodos4);
                            } catch (JsonProcessingException e) {
                                e.printStackTrace();
                            }
                            break;
                    }
                    break;
                } else { if (btree_challenge == true){
                    switch(player){
                        case 1:
                            reset_BTree(btree1);
                            btree1 = new BTree();
                            try {
                                JsonNode btreenode1 = Json.toJson(btree1.preOrder());
                                String btreenodos1 = Json.generateString(btreenode1, false);
                                enviar_mensaje("Arbol" + "#Jugador1#" + btreenodos1,socket);
                                System.out.println(btreenodos1);
                            } catch (JsonProcessingException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 2:
                            reset_BTree(btree2);
                            btree2 = new BTree();
                            try {
                                JsonNode btreenode2 = Json.toJson(btree2.preOrder());
                                String btreenodos2 = Json.generateString(btreenode2, false);
                                enviar_mensaje("Arbol" + "#Jugador2#" + btreenodos2,socket);
                                System.out.println(btreenodos2);
                            } catch (JsonProcessingException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 3:
                            reset_BTree(btree3);
                            btree3 = new BTree();
                            try {
                                JsonNode btreenode3 = Json.toJson(btree3.preOrder());
                                String btreenodos3 = Json.generateString(btreenode3, false);
                                enviar_mensaje("Arbol" + "#Jugador3#" + btreenodos3,socket);
                                System.out.println(btreenodos3);
                            } catch (JsonProcessingException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 4:
                            reset_BTree(btree4);
                            btree4 = new BTree();
                            try {
                                JsonNode btreenode4 = Json.toJson(btree4.preOrder());
                                String btreenodos4 = Json.generateString(btreenode4, false);
                                enviar_mensaje("Arbol" + "#Jugador4#" + btreenodos4,socket);
                                System.out.println(btreenodos4);
                            } catch (JsonProcessingException e) {
                                e.printStackTrace();
                            }
                            break;
                    }
                    break;
                    } else {
                        switch(player){
                            case 1:
                                reset_Splay(splay1);
                                splay1 = new Splay();
                                splay1.preorder();
                                try {
                                    JsonNode splaynode1 = Json.toJson(splay1.array);
                                    String splaynodos1 = Json.generateString(splaynode1, false);
                                    enviar_mensaje("Arbol" + "#Jugador1#" + splaynodos1,socket);
                                    System.out.println(splaynodos1);
                                } catch (JsonProcessingException e) {
                                    e.printStackTrace();
                                }
                                break;
                            case 2:
                                reset_Splay(splay2);
                                splay2 = new Splay();
                                splay2.preorder();
                                try {
                                    JsonNode splaynode2 = Json.toJson(splay2.array);
                                    String splaynodos2 = Json.generateString(splaynode2, false);
                                    enviar_mensaje("Arbol" + "#Jugador2#" + splaynodos2,socket);
                                    System.out.println(splaynodos2);
                                } catch (JsonProcessingException e) {
                                    e.printStackTrace();
                                }
                                break;
                            case 3:
                                reset_Splay(splay3);
                                splay3 = new Splay();
                                splay3.preorder();
                                try {
                                    JsonNode splaynode3 = Json.toJson(splay3.array);
                                    String splaynodos3 = Json.generateString(splaynode3, false);
                                    enviar_mensaje("Arbol" + "#Jugador3#" + splaynodos3,socket);
                                    System.out.println(splaynodos3);
                                } catch (JsonProcessingException e) {
                                    e.printStackTrace();
                                }
                                break;
                            case 4:
                                reset_Splay(splay4);
                                splay4 = new Splay();
                                splay4.preorder();
                                try {
                                    JsonNode splaynode4 = Json.toJson(splay4.array);
                                    String splaynodos4 = Json.generateString(splaynode4, false);
                                    enviar_mensaje("Arbol" + "#Jugador4#" + splaynodos4,socket);
                                    System.out.println(splaynodos4);
                                } catch (JsonProcessingException e) {
                                    e.printStackTrace();
                                }
                                break;

                        }

                    }

                }
                }
            case "AVL":
                if (bst_challenge == true){
                    switch(player){
                        case 1:
                            reset_BST(bst1);
                            bst1 = new BST();
                            bst1.preorder();
                            try {
                                JsonNode bstnode1 = Json.toJson(bst1.array);
                                String bstnodos1 = Json.generateString(bstnode1, false);
                                enviar_mensaje("Arbol" + "#Jugador1#" + bstnodos1,socket);
                                System.out.println(bstnodos1);
                            } catch (JsonProcessingException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 2:
                            reset_BST(bst2);
                            bst2 = new BST();
                            bst2.preorder();
                            try {
                                JsonNode bstnode2 = Json.toJson(bst2.array);
                                String bstnodos2 = Json.generateString(bstnode2, false);
                                enviar_mensaje("Arbol" + "#Jugador2#" + bstnodos2,socket);
                                System.out.println(bstnodos2);
                            } catch (JsonProcessingException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 3:
                            reset_BST(bst3);
                            bst3 = new BST();
                            bst3.preorder();
                            try {
                                JsonNode bstnode3 = Json.toJson(bst3.array);
                                String bstnodos3 = Json.generateString(bstnode3, false);
                                enviar_mensaje("Arbol" + "#Jugador3#" + bstnodos3,socket);
                                System.out.println(bstnodos3);
                            } catch (JsonProcessingException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 4:
                            reset_BST(bst4);
                            bst4 = new BST();
                            bst4.preorder();
                            try {
                                JsonNode bstnode4 = Json.toJson(bst4.array);
                                String bstnodos4 = Json.generateString(bstnode4, false);
                                enviar_mensaje("Arbol" + "#Jugador4#" + bstnodos4,socket);
                                System.out.println(bstnodos4);
                            } catch (JsonProcessingException e) {
                                e.printStackTrace();
                            }
                            break;
                    }
                    break;
                }
                else {
                    if (avl_challenge == true) {
                        switch(player){
                            case 1:
                                avl1.root = avl1.insert(avl1.root,token.get_valor());
                                avl1.addCount();
                                avl1.preorder();
                                try {
                                    JsonNode avlnode1 = Json.toJson(avl1.array);
                                    String avlnodos1 = Json.generateString(avlnode1, false);
                                    enviar_mensaje("Arbol" + "#Jugador1#" + avlnodos1,socket);
                                    System.out.println(avlnodos1);
                                } catch (JsonProcessingException e) {
                                    e.printStackTrace();
                                }
                                break;
                            case 2:
                                avl2.root = avl2.insert(avl2.root,token.get_valor());
                                avl2.addCount();
                                avl2.preorder();
                                try {
                                    JsonNode avlnode2 = Json.toJson(avl2.array);
                                    String avlnodos2 = Json.generateString(avlnode2, false);
                                    enviar_mensaje("Arbol" + "#Jugador2#" + avlnodos2,socket);
                                    System.out.println(avlnodos2);
                                } catch (JsonProcessingException e) {
                                    e.printStackTrace();
                                }
                                break;
                            case 3:
                                avl3.root = avl3.insert(avl3.root,token.get_valor());
                                avl3.addCount();
                                avl3.preorder();
                                try {
                                    JsonNode avlnode3 = Json.toJson(avl3.array);
                                    String avlnodos3 = Json.generateString(avlnode3, false);
                                    enviar_mensaje("Arbol" + "#Jugador3#" + avlnodos3,socket);
                                    System.out.println(avlnodos3);
                                } catch (JsonProcessingException e) {
                                    e.printStackTrace();
                                }
                                break;
                            case 4:
                                avl4.root = avl4.insert(avl4.root,token.get_valor());
                                avl4.addCount();
                                avl4.preorder();
                                try {
                                    JsonNode avlnode4 = Json.toJson(avl4.array);
                                    String avlnodos4 = Json.generateString(avlnode4, false);
                                    enviar_mensaje("Arbol" + "#Jugador4#" + avlnodos4,socket);
                                    System.out.println(avlnodos4);
                                } catch (JsonProcessingException e) {
                                    e.printStackTrace();
                                }
                                break;
                        }
                        break;
                    } else {
                        if (btree_challenge == true) {
                            switch (player) {
                                case 1:
                                    reset_BTree(btree1);
                                    btree1 = new BTree();
                                    try {
                                        JsonNode btreenode1 = Json.toJson(btree1.preOrder());
                                        String btreenodos1 = Json.generateString(btreenode1, false);
                                        enviar_mensaje("Arbol" + "#Jugador1#" + btreenodos1, socket);
                                        System.out.println(btreenodos1);
                                    } catch (JsonProcessingException e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                case 2:
                                    reset_BTree(btree2);
                                    btree2 = new BTree();
                                    try {
                                        JsonNode btreenode2 = Json.toJson(btree2.preOrder());
                                        String btreenodos2 = Json.generateString(btreenode2, false);
                                        enviar_mensaje("Arbol" + "#Jugador2#" + btreenodos2, socket);
                                        System.out.println(btreenodos2);
                                    } catch (JsonProcessingException e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                case 3:
                                    reset_BTree(btree3);
                                    btree3 = new BTree();
                                    try {
                                        JsonNode btreenode3 = Json.toJson(btree3.preOrder());
                                        String btreenodos3 = Json.generateString(btreenode3, false);
                                        enviar_mensaje("Arbol" + "#Jugador3#" + btreenodos3, socket);
                                        System.out.println(btreenodos3);
                                    } catch (JsonProcessingException e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                case 4:
                                    reset_BTree(btree4);
                                    btree4 = new BTree();
                                    try {
                                        JsonNode btreenode4 = Json.toJson(btree4.preOrder());
                                        String btreenodos4 = Json.generateString(btreenode4, false);
                                        enviar_mensaje("Arbol" + "#Jugador4#" + btreenodos4, socket);
                                        System.out.println(btreenodos4);
                                    } catch (JsonProcessingException e) {
                                        e.printStackTrace();
                                    }
                                    break;
                            }
                            break;
                        } else {
                            switch (player) {
                                case 1:
                                    reset_Splay(splay1);
                                    splay1 = new Splay();
                                    splay1.preorder();
                                    try {
                                        JsonNode splaynode1 = Json.toJson(splay1.array);
                                        String splaynodos1 = Json.generateString(splaynode1, false);
                                        enviar_mensaje("Arbol" + "#Jugador1#" + splaynodos1, socket);
                                        System.out.println(splaynodos1);
                                    } catch (JsonProcessingException e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                case 2:
                                    reset_Splay(splay2);
                                    splay2 = new Splay();
                                    splay2.preorder();
                                    try {
                                        JsonNode splaynode2 = Json.toJson(splay2.array);
                                        String splaynodos2 = Json.generateString(splaynode2, false);
                                        enviar_mensaje("Arbol" + "#Jugador2#" + splaynodos2, socket);
                                        System.out.println(splaynodos2);
                                    } catch (JsonProcessingException e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                case 3:
                                    reset_Splay(splay3);
                                    splay3 = new Splay();
                                    splay3.preorder();
                                    try {
                                        JsonNode splaynode3 = Json.toJson(splay3.array);
                                        String splaynodos3 = Json.generateString(splaynode3, false);
                                        enviar_mensaje("Arbol" + "#Jugador3#" + splaynodos3, socket);
                                        System.out.println(splaynodos3);
                                    } catch (JsonProcessingException e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                case 4:
                                    reset_Splay(splay4);
                                    splay4 = new Splay();
                                    splay4.preorder();
                                    try {
                                        JsonNode splaynode4 = Json.toJson(splay4.array);
                                        String splaynodos4 = Json.generateString(splaynode4, false);
                                        enviar_mensaje("Arbol" + "#Jugador4#" + splaynodos4, socket);
                                        System.out.println(splaynodos4);
                                    } catch (JsonProcessingException e) {
                                        e.printStackTrace();
                                    }
                                    break;
                            }
                        }
                    }
                }
            case "BTree":
                if (bst_challenge == true){
                    switch(player){
                        case 1:
                            reset_BST(bst1);
                            bst1 = new BST();
                            bst1.preorder();
                            try {
                                JsonNode bstnode1 = Json.toJson(bst1.array);
                                String bstnodos1 = Json.generateString(bstnode1, false);
                                enviar_mensaje("Arbol" + "#Jugador1#" + bstnodos1,socket);
                                System.out.println(bstnodos1);
                            } catch (JsonProcessingException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 2:
                            reset_BST(bst2);
                            bst2 = new BST();
                            bst2.preorder();
                            try {
                                JsonNode bstnode2 = Json.toJson(bst2.array);
                                String bstnodos2 = Json.generateString(bstnode2, false);
                                enviar_mensaje("Arbol" + "#Jugador2#" + bstnodos2,socket);
                                System.out.println(bstnodos2);
                            } catch (JsonProcessingException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 3:
                            reset_BST(bst3);
                            bst3 = new BST();
                            bst3.preorder();
                            try {
                                JsonNode bstnode3 = Json.toJson(bst3.array);
                                String bstnodos3 = Json.generateString(bstnode3, false);
                                enviar_mensaje("Arbol" + "#Jugador3#" + bstnodos3,socket);
                                System.out.println(bstnodos3);
                            } catch (JsonProcessingException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 4:
                            reset_BST(bst4);
                            bst4 = new BST();
                            bst4.preorder();
                            try {
                                JsonNode bstnode4 = Json.toJson(bst4.array);
                                String bstnodos4 = Json.generateString(bstnode4, false);
                                enviar_mensaje("Arbol" + "#Jugador4#" + bstnodos4,socket);
                                System.out.println(bstnodos4);
                            } catch (JsonProcessingException e) {
                                e.printStackTrace();
                            }
                            break;
                    }
                    break;
                }
                else {
                    if (avl_challenge == true) {
                        switch(player){
                            case 1:
                                reset_AVL(avl1);
                                avl1 = new AVL();
                                avl1.preorder();
                                try {
                                    JsonNode avlnode1 = Json.toJson(avl1.array);
                                    String avlnodos1 = Json.generateString(avlnode1, false);
                                    enviar_mensaje("Arbol" + "#Jugador1#" + avlnodos1,socket);
                                    System.out.println(avlnodos1);
                                } catch (JsonProcessingException e) {
                                    e.printStackTrace();
                                }
                                break;
                            case 2:
                                reset_AVL(avl2);
                                avl2 = new AVL();
                                avl2.preorder();
                                try {
                                    JsonNode avlnode2 = Json.toJson(avl2.array);
                                    String avlnodos2 = Json.generateString(avlnode2, false);
                                    enviar_mensaje("Arbol" + "#Jugador2#" + avlnodos2,socket);
                                    System.out.println(avlnodos2);
                                } catch (JsonProcessingException e) {
                                    e.printStackTrace();
                                }
                                break;
                            case 3:
                                reset_AVL(avl3);
                                avl3 = new AVL();
                                avl3.preorder();
                                try {
                                    JsonNode avlnode3 = Json.toJson(avl3.array);
                                    String avlnodos3 = Json.generateString(avlnode3, false);
                                    enviar_mensaje("Arbol" + "#Jugador3#" + avlnodos3,socket);
                                    System.out.println(avlnodos3);
                                } catch (JsonProcessingException e) {
                                    e.printStackTrace();
                                }
                                break;
                            case 4:
                                reset_AVL(avl4);
                                avl4 = new AVL();
                                avl4.preorder();
                                try {
                                    JsonNode avlnode4 = Json.toJson(avl4.array);
                                    String avlnodos4 = Json.generateString(avlnode4, false);
                                    enviar_mensaje("Arbol" + "#Jugador4#" + avlnodos4,socket);
                                    System.out.println(avlnodos4);
                                } catch (JsonProcessingException e) {
                                    e.printStackTrace();
                                }
                                break;
                        }
                        break;
                    } else {
                        if (btree_challenge == true) {
                            switch (player) {
                                case 1:
                                    btree1.insert(token.get_valor());
                                    try {
                                        JsonNode btreenode1 = Json.toJson(btree1.preOrder());
                                        String btreenodos1 = Json.generateString(btreenode1, false);
                                        enviar_mensaje("Arbol" + "#Jugador1#" + btreenodos1, socket);
                                        System.out.println(btreenodos1);
                                    } catch (JsonProcessingException e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                case 2:
                                    btree2.insert(token.get_valor());
                                    try {
                                        JsonNode btreenode2 = Json.toJson(btree2.preOrder());
                                        String btreenodos2 = Json.generateString(btreenode2, false);
                                        enviar_mensaje("Arbol" + "#Jugador2#" + btreenodos2, socket);
                                        System.out.println(btreenodos2);
                                    } catch (JsonProcessingException e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                case 3:
                                    btree3.insert(token.get_valor());
                                    try {
                                        JsonNode btreenode3 = Json.toJson(btree3.preOrder());
                                        String btreenodos3 = Json.generateString(btreenode3, false);
                                        enviar_mensaje("Arbol" + "#Jugador3#" + btreenodos3, socket);
                                        System.out.println(btreenodos3);
                                    } catch (JsonProcessingException e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                case 4:
                                    btree4.insert(token.get_valor());
                                    try {
                                        JsonNode btreenode4 = Json.toJson(btree4.preOrder());
                                        String btreenodos4 = Json.generateString(btreenode4, false);
                                        enviar_mensaje("Arbol" + "#Jugador4#" + btreenodos4, socket);
                                        System.out.println(btreenodos4);
                                    } catch (JsonProcessingException e) {
                                        e.printStackTrace();
                                    }
                                    break;
                            }
                            break;
                        } else {
                            switch (player) {
                                case 1:
                                    reset_Splay(splay1);
                                    splay1 = new Splay();
                                    splay1.preorder();
                                    try {
                                        JsonNode splaynode1 = Json.toJson(splay1.array);
                                        String splaynodos1 = Json.generateString(splaynode1, false);
                                        enviar_mensaje("Arbol" + "#Jugador1#" + splaynodos1, socket);
                                        System.out.println(splaynodos1);
                                    } catch (JsonProcessingException e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                case 2:
                                    reset_Splay(splay2);
                                    splay2 = new Splay();
                                    splay2.preorder();
                                    try {
                                        JsonNode splaynode2 = Json.toJson(splay2.array);
                                        String splaynodos2 = Json.generateString(splaynode2, false);
                                        enviar_mensaje("Arbol" + "#Jugador2#" + splaynodos2, socket);
                                        System.out.println(splaynodos2);
                                    } catch (JsonProcessingException e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                case 3:
                                    reset_Splay(splay3);
                                    splay3 = new Splay();
                                    splay3.preorder();
                                    try {
                                        JsonNode splaynode3 = Json.toJson(splay3.array);
                                        String splaynodos3 = Json.generateString(splaynode3, false);
                                        enviar_mensaje("Arbol" + "#Jugador3#" + splaynodos3, socket);
                                        System.out.println(splaynodos3);
                                    } catch (JsonProcessingException e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                case 4:
                                    reset_Splay(splay4);
                                    splay4 = new Splay();
                                    splay4.preorder();
                                    try {
                                        JsonNode splaynode4 = Json.toJson(splay4.array);
                                        String splaynodos4 = Json.generateString(splaynode4, false);
                                        enviar_mensaje("Arbol" + "#Jugador4#" + splaynodos4, socket);
                                        System.out.println(splaynodos4);
                                    } catch (JsonProcessingException e) {
                                        e.printStackTrace();
                                    }
                                    break;
                            }
                        }
                    }
                }
            case "Splay":
                if (bst_challenge == true){
                    switch(player){
                        case 1:
                            reset_BST(bst1);
                            bst1 = new BST();
                            bst1.preorder();
                            try {
                                JsonNode bstnode1 = Json.toJson(bst1.array);
                                String bstnodos1 = Json.generateString(bstnode1, false);
                                enviar_mensaje("Arbol" + "#Jugador1#" + bstnodos1,socket);
                                System.out.println(bstnodos1);
                            } catch (JsonProcessingException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 2:
                            reset_BST(bst2);
                            bst2 = new BST();
                            bst2.preorder();
                            try {
                                JsonNode bstnode2 = Json.toJson(bst2.array);
                                String bstnodos2 = Json.generateString(bstnode2, false);
                                enviar_mensaje("Arbol" + "#Jugador2#" + bstnodos2,socket);
                                System.out.println(bstnodos2);
                            } catch (JsonProcessingException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 3:
                            reset_BST(bst3);
                            bst3 = new BST();
                            bst3.preorder();
                            try {
                                JsonNode bstnode3 = Json.toJson(bst3.array);
                                String bstnodos3 = Json.generateString(bstnode3, false);
                                enviar_mensaje("Arbol" + "#Jugador3#" + bstnodos3,socket);
                                System.out.println(bstnodos3);
                            } catch (JsonProcessingException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 4:
                            reset_BST(bst4);
                            bst4 = new BST();
                            bst4.preorder();
                            try {
                                JsonNode bstnode4 = Json.toJson(bst4.array);
                                String bstnodos4 = Json.generateString(bstnode4, false);
                                enviar_mensaje("Arbol" + "#Jugador4#" + bstnodos4,socket);
                                System.out.println(bstnodos4);
                            } catch (JsonProcessingException e) {
                                e.printStackTrace();
                            }
                            break;
                    }
                    break;
                }
                else {
                    if (avl_challenge == true) {
                        switch(player){
                            case 1:
                                reset_AVL(avl1);
                                avl1 = new AVL();
                                avl1.preorder();
                                try {
                                    JsonNode avlnode1 = Json.toJson(avl1.array);
                                    String avlnodos1 = Json.generateString(avlnode1, false);
                                    enviar_mensaje("Arbol" + "#Jugador1#" + avlnodos1,socket);
                                    System.out.println(avlnodos1);
                                } catch (JsonProcessingException e) {
                                    e.printStackTrace();
                                }
                                break;
                            case 2:
                                reset_AVL(avl2);
                                avl2 = new AVL();
                                avl2.preorder();
                                try {
                                    JsonNode avlnode2 = Json.toJson(avl2.array);
                                    String avlnodos2 = Json.generateString(avlnode2, false);
                                    enviar_mensaje("Arbol" + "#Jugador2#" + avlnodos2,socket);
                                    System.out.println(avlnodos2);
                                } catch (JsonProcessingException e) {
                                    e.printStackTrace();
                                }
                                break;
                            case 3:
                                reset_AVL(avl3);
                                avl3 = new AVL();
                                avl3.preorder();
                                try {
                                    JsonNode avlnode3 = Json.toJson(avl3.array);
                                    String avlnodos3 = Json.generateString(avlnode3, false);
                                    enviar_mensaje("Arbol" + "#Jugador3#" + avlnodos3,socket);
                                    System.out.println(avlnodos3);
                                } catch (JsonProcessingException e) {
                                    e.printStackTrace();
                                }
                                break;
                            case 4:
                                reset_AVL(avl4);
                                avl4 = new AVL();
                                avl4.preorder();
                                try {
                                    JsonNode avlnode4 = Json.toJson(avl4.array);
                                    String avlnodos4 = Json.generateString(avlnode4, false);
                                    enviar_mensaje("Arbol" + "#Jugador4#" + avlnodos4,socket);
                                    System.out.println(avlnodos4);
                                } catch (JsonProcessingException e) {
                                    e.printStackTrace();
                                }
                                break;
                        }
                        break;
                    } else {
                        if (btree_challenge == true) {
                            switch (player) {
                                case 1:
                                    reset_BTree(btree1);
                                    btree1 = new BTree();
                                    try {
                                        JsonNode btreenode1 = Json.toJson(btree1.preOrder());
                                        String btreenodos1 = Json.generateString(btreenode1, false);
                                        enviar_mensaje("Arbol" + "#Jugador1#" + btreenodos1, socket);
                                        System.out.println(btreenodos1);
                                    } catch (JsonProcessingException e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                case 2:
                                    reset_BTree(btree2);
                                    btree2 = new BTree();
                                    try {
                                        JsonNode btreenode2 = Json.toJson(btree2.preOrder());
                                        String btreenodos2 = Json.generateString(btreenode2, false);
                                        enviar_mensaje("Arbol" + "#Jugador2#" + btreenodos2, socket);
                                        System.out.println(btreenodos2);
                                    } catch (JsonProcessingException e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                case 3:
                                    reset_BTree(btree3);
                                    btree3 = new BTree();
                                    try {
                                        JsonNode btreenode3 = Json.toJson(btree3.preOrder());
                                        String btreenodos3 = Json.generateString(btreenode3, false);
                                        enviar_mensaje("Arbol" + "#Jugador3#" + btreenodos3, socket);
                                        System.out.println(btreenodos3);
                                    } catch (JsonProcessingException e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                case 4:
                                    reset_BTree(btree4);
                                    btree4 = new BTree();
                                    try {
                                        JsonNode btreenode4 = Json.toJson(btree4.preOrder());
                                        String btreenodos4 = Json.generateString(btreenode4, false);
                                        enviar_mensaje("Arbol" + "#Jugador4#" + btreenodos4, socket);
                                        System.out.println(btreenodos4);
                                    } catch (JsonProcessingException e) {
                                        e.printStackTrace();
                                    }
                                    break;
                            }
                            break;
                        } else {
                            switch (player) {
                                case 1:
                                    splay1.insert(token.get_valor());
                                    splay1.preorder();
                                    try {
                                        JsonNode splaynode1 = Json.toJson(splay1.array);
                                        String splaynodos1 = Json.generateString(splaynode1, false);
                                        enviar_mensaje("Arbol" + "#Jugador1#" + splaynodos1,socket);
                                        System.out.println(splaynodos1);
                                    } catch (JsonProcessingException e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                case 2:
                                    splay2.insert(token.get_valor());
                                    splay2.preorder();
                                    try {
                                        JsonNode splaynode2 = Json.toJson(splay2.array);
                                        String splaynodos2 = Json.generateString(splaynode2, false);
                                        enviar_mensaje("Arbol" + "#Jugador2#" + splaynodos2,socket);
                                        System.out.println(splaynodos2);
                                    } catch (JsonProcessingException e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                case 3:
                                    splay3.insert(token.get_valor());
                                    splay3.preorder();
                                    try {
                                        JsonNode splaynode3 = Json.toJson(splay3.array);
                                        String splaynodos3 = Json.generateString(splaynode3, false);
                                        enviar_mensaje("Arbol" + "#Jugador3#" + splaynodos3,socket);
                                        System.out.println(splaynodos3);
                                    } catch (JsonProcessingException e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                case 4:
                                    splay4.insert(token.get_valor());
                                    splay4.preorder();
                                    try {
                                        JsonNode splaynode4 = Json.toJson(splay4.array);
                                        String splaynodos4 = Json.generateString(splaynode4, false);
                                        enviar_mensaje("Arbol" + "#Jugador4#" + splaynodos4,socket);
                                        System.out.println(splaynodos4);
                                    } catch (JsonProcessingException e) {
                                        e.printStackTrace();
                                    }
                                    break;

                            }
                        }
                    }
                }
        }
    }
    
    public void reset_BST(BST bst){
        bst = null;
    }
    
    public void reset_AVL(AVL avl){
        avl = null;
    }        
    
    public void reset_BTree(BTree btree){
        btree = null;
    }
    
    public void reset_Splay(Splay splay){
        splay = null;
    }
    
    public void create_challenge(int challenge_type){
        switch(challenge_type){
            case 0:
                avl1 = new AVL();
                avl2 = new AVL();
                avl3 = new AVL();
                avl4 = new AVL();
                avl_challenge = true;
                System.out.print("AVL challenge started" + "\n");
                //send AVL challenge start to to client 
                enviar_mensaje("Reto" + "#" + "AVL" + "#" + "Iniciar",socket);                 
                break;
            case 1:
                bst1 = new BST();
                bst2 = new BST();
                bst3 = new BST();
                bst4 = new BST();
                bst_challenge = true;
                System.out.print("BST challenge started" + "\n");
                //send BST challenge start to to client 
                enviar_mensaje("Reto" + "#" + "BST" + "#"+ "Iniciar",socket);                 
                break;
            case 2:
                btree1 = new BTree();
                btree2 = new BTree();
                btree3 = new BTree();
                btree4 = new BTree();
                btree_challenge = true;
                System.out.print("BTree challenge started" + "\n");
                //send BTree challenge start to to client 
                enviar_mensaje("Reto" + "#" + "BTree" + "#"+ "Iniciar",socket);                  
                break;
            case 3:
                splay1 = new Splay();
                splay2 = new Splay();
                splay3 = new Splay();
                splay4 = new Splay();
                splay_challenge = true;
                System.out.print("Splay challenge started" + "\n");
                //send Splay challenge start to to client 
                enviar_mensaje("Reto" + "#" + "Splay" + "#"+ "Iniciar",socket);            
                break;
                       
        } 
    }
    
    public void make_token(int id, int valor){
        if (valor == 0){
            valor = 5;
        }
        token.set_valor(valor);
        switch (id) {
            case 0:
                token.set_tipo("AVL");
                enviar_mensaje("Token" + "#" + token.get_tipo() + "#" + String.valueOf(token.get_valor()), socket);
                //send_token(token)
                break;
            case 1:
                token.set_tipo("BST");
                enviar_mensaje("Token" + "#" + token.get_tipo() + "#" + String.valueOf(token.get_valor()), socket);
                //send_token(token)
                break;
            case 2:
                token.set_tipo("BTree");
                enviar_mensaje("Token" + "#" + token.get_tipo() + "#" + String.valueOf(token.get_valor()), socket);
                //send_token(token)
                break;
            case 3:
                token.set_tipo("Splay");
                enviar_mensaje("Token" + "#" + token.get_tipo() + "#" + String.valueOf(token.get_valor()), socket);
                //send_token(token)
                break;
            case 4:
                token.set_tipo("Escudo");
                enviar_mensaje("Poder" + "#" + token.get_tipo() + "#" + String.valueOf(token.get_valor()), socket);
                //send_token(token)
                break;
            case 5:
                token.set_tipo("Ataque");
                enviar_mensaje("Poder" + "#" + token.get_tipo() + "#" + String.valueOf(token.get_valor()), socket);
                //send_token(token)
                break;
            case 6:
                token.set_tipo("Salto");
                enviar_mensaje("Poder" + "#" + token.get_tipo() + "#" + String.valueOf(token.get_valor()), socket);
                //send_token(token)
                break;
            case 7:
                token.set_tipo("Vida");
                enviar_mensaje("Poder" + "#" + token.get_tipo() + "#" + String.valueOf(token.get_valor()), socket);
                //send_token(token)
                break;
        }
    }
    
    public void enviar_mensaje(String mensaje, Socket socket){
        try{
            doutput = new DataOutputStream(socket.getOutputStream());
            byte[] toSendBytes = mensaje.getBytes();
            int toSendLen = toSendBytes.length;
            byte[] toSendLenBytes = new byte[4];
            toSendLenBytes[0] = (byte)(toSendLen & 0xff);
            toSendLenBytes[1] = (byte)((toSendLen >> 8) & 0xff);
            toSendLenBytes[2] = (byte)((toSendLen >> 16) & 0xff);
            toSendLenBytes[3] = (byte)((toSendLen >> 24) & 0xff);
            doutput.write(toSendLenBytes);
            doutput.write(toSendBytes);}
        catch (Exception ex){
            //Exceptions
        }
    }

    @Override
    public void update(Observable o, Object o1) {
        String mensplit = String.valueOf(o1);

        String[] mensaje = mensplit.split("#");


        if(mensaje[0].equals("Empezar")){
            startsending = true;
        }
        else if (mensaje[0].equals("Token")){
            Token tokenplayer = new Token();
            tokenplayer.set_tipo(mensaje[2]);
            tokenplayer.set_valor(Integer.parseInt(mensaje[3]));
            System.out.println(tokenplayer.get_tipo() + tokenplayer.get_valor());

            System.out.println("-----------------");
            System.out.println(mensplit);
            System.out.println("-----------------");

            switch (mensaje[1]) {
                case "Jugador1":
                    modify_tree(tokenplayer, 1);
                    System.out.println("Jugador1");
                    break;
                case "Jugador2":
                    modify_tree(tokenplayer, 2);
                    System.out.println("Jugador2");
                    break;
                case "Jugador3":
                    modify_tree(tokenplayer, 3);
                    System.out.println("Jugador3");
                    break;
                case "Jugador4":
                    modify_tree(tokenplayer, 4);
                    System.out.println("Jugador4");
                    break;
            }
        }


    }
    
    
}