

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
    
    //lista de nodos que están activos (si existen y ´tienen valor)
    public void start() {
        
        /*
        bst1 = new BST();
        bst1.insert(10);
        bst1.insert(9);
        bst1.insert(11);
        bst1.insert(13);
        bst1.insert(12);
        bst1.insert(6);
        bst1.insert(7);
        bst1.insert(8);
        bst1.preorder();
        for (int i = 0; i < 15; i++){
            System.out.println(bst1.array[i]);}        
        /*
        /*
        bst1.preorder();
        JsonNode node = Json.toJson(bst1.array);
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
            dinput = new DataInputStream(socket.getInputStream());
            doutput = new DataOutputStream(socket.getOutputStream());
            socket = ss.accept();
            HiloServer hiloserver = new HiloServer(dinput,socket);
            hiloserver.addObserver(this);
            Thread t = new Thread(hiloserver);
            t.start();
            
        }
        catch (Exception e){
            //Exceptions
        }
        while (true){
            //while loop para challenges
            while (active_challenge != true){
                if (challenge_cooldown != true){
                    //cada loop, challenge starter se redefine como un número aleatorio entre 0 y 10000
                    challenge_starter = rand.nextInt(10001);
                    //cuando el número aleatorio es igual a 10000, se inicia un challenge de un árbol aleatorio
                    if (challenge_starter == 10000){
                        create_challenge(rand.nextInt(4));
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
                if (((challengeEndTime - challengestartTime)/1000000000) > 10){
                    System.out.print("Challenge ended" + "\n");
                    active_challenge = false;
                    challengecooldownstartTime = System.nanoTime();
                    if (bst_challenge == true){
                            bst_challenge = false;
                            System.out.print("Player 1 won the BST challenge" + "\n");
                            //award points to the most grown bst
                            reset_BST(bst1);
                            reset_BST(bst2);
                            reset_BST(bst3);
                            reset_BST(bst4);
                    }                    
                    else{ 
                        if (avl_challenge == true){
                            avl_challenge = false;
                            System.out.print("Player 2 won the AVL challenge" + "\n");
                            //award points to the most grown AVL
                            reset_AVL(avl1);
                            reset_AVL(avl2);
                            reset_AVL(avl3);
                            reset_AVL(avl4);
                        }            
                        else{
                            if (btree_challenge == true){
                                btree_challenge = false;
                                System.out.print("Player 3 won the BTree challenge" + "\n");
                                //award points to the most grown btree
                                reset_BTree(btree1);
                                reset_BTree(btree2);
                                reset_BTree(btree3);
                                reset_BTree(btree4);
                            }
                            else{
                                splay_challenge = false;  
                                System.out.print("Player 4 won the Splay challenge" + "\n");
                                //award points to the most grown splay
                                reset_Splay(splay1);
                                reset_Splay(splay2);
                                reset_Splay(splay3);
                                reset_Splay(splay4);
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
                            if (((tokencooldownEndTime - tokencooldownstartTime)/1000000000) > 1){
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
                switch(player){
                    case 1:
                        bst1.insert(token.get_valor());
                        bst1.preorder();
                        JsonNode node1 = Json.toJson(bst1.array);
                        try {
                            String nodos1 = Json.generateString(node1, false);
                            enviar_mensaje("Arbol" + "#Player1#" + nodos1,socket);
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 2:
                        bst2.insert(token.get_valor());
                        bst2.preorder();
                        JsonNode node2 = Json.toJson(bst2.array);
                        try {
                            String nodos2 = Json.generateString(node2, false);
                            enviar_mensaje("Arbol" + "#Player2#" + nodos2,socket);
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 3:
                        bst3.insert(token.get_valor());
                        bst3.preorder();
                        JsonNode node3 = Json.toJson(bst3.array);
                        try {
                            String nodos3 = Json.generateString(node3, false);
                            enviar_mensaje("Arbol" + "#Player3#" + nodos3,socket);
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 4:
                        bst4.insert(token.get_valor());
                        bst4.preorder();
                        JsonNode node4 = Json.toJson(bst4.array);
                        try {
                            String nodos4 = Json.generateString(node4, false);
                            enviar_mensaje("Arbol" + "#Player4#" + nodos4,socket);
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                        break;
                }
                break;
            case "AVL":
                switch(player){
                    case 1:
                        avl1.root = avl1.insert(avl1.root,token.get_valor());
                        break;
                    case 2:
                        avl2.root = avl2.insert(avl2.root,token.get_valor());
                        break;
                    case 3:
                        avl3.root = avl3.insert(avl3.root,token.get_valor());
                        break;
                    case 4:
                        avl4.root = avl4.insert(avl4.root,token.get_valor());
                        break;
                }
                break;
            case "BTree":
            case "Splay":
                
                /*if (bst1.search(bst1.root,70) == true){
                System.out.print("the element already exists");
                }
                else {
                bst1.insert(70);
                System.out.print("Inserted 70");
                }
                for printing nodes
                bst1.inorder();
                avl1.preOrder(avl1.root);
                */
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
        switch (id){
            case 0:
                token.set_tipo("AVL");
                enviar_mensaje("Token" + "#" + token.get_tipo() + "#" + String.valueOf(token.get_valor()),socket);   
                //send_token(token)
                break;
            case 1:
                token.set_tipo("BST");
                enviar_mensaje("Token" + "#" + token.get_tipo() + "#" + String.valueOf(token.get_valor()),socket);   
                //send_token(token)
                break;
            case 2:
                token.set_tipo("BTree");
                enviar_mensaje("Token" + "#" + token.get_tipo() + "#" + String.valueOf(token.get_valor()),socket);   
                //send_token(token)
                break;
            case 3:
                token.set_tipo("Splay");
                enviar_mensaje("Token" + "#" + token.get_tipo() + "#" + String.valueOf(token.get_valor()),socket);   
                //send_token(token)
                break;
            case 4:
                token.set_tipo("Escudo");
                enviar_mensaje("Poder" + "#" + token.get_tipo() + "#" + String.valueOf(token.get_valor()),socket);   
                //send_token(token)
                break;
            case 5:
                token.set_tipo("Ataque");
                enviar_mensaje("Poder" + "#" + token.get_tipo() + "#" + String.valueOf(token.get_valor()),socket);   
                //send_token(token)
                break;
            case 6:
                token.set_tipo("Salto");
                enviar_mensaje("Poder" + "#" + token.get_tipo() + "#" + String.valueOf(token.get_valor()),socket);   
                //send_token(token)
                break;
            case 7:
                token.set_tipo("Vida");
                enviar_mensaje("Poder" + "#" + token.get_tipo() + "#" + String.valueOf(token.get_valor()),socket);   
                //send_token(token)
                break;
        }
        token.set_valor(valor);
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
        String mensaje = String.valueOf(o1);
        System.out.println(mensaje);
    }
    
    
}