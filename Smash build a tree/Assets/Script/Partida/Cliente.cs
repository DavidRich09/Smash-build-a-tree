using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using System.Threading;
using System.Net;
using System.Net.Sockets;
using System;

public class Cliente : MonoBehaviour
{
    private Socket listen;
    private IPEndPoint connect;
    private String[] mensaje;
    private ControllerSelection player1;
    private ControllerSelection2 player2;
    private ControllerSelection3 player3;
    private ControllerSelection4 player4;
    private FacadeGame facadeGame;
    private int[] nodos;
    private ArregloInt arregloInt = new ArregloInt();
    private GamaManager gameManager;
    public TokenSpawner tokenSpawner;
    

    // Start is called before the first frame update
    void Start()
    {
        player1 = ControllerSelection.GetInstancia();
        player2 = ControllerSelection2.GetInstancia();
        player3 = ControllerSelection3.GetInstancia();
        player4 = ControllerSelection4.GetInstancia();

        
        gameManager = GamaManager.GetInstancia();

        facadeGame = FindObjectOfType<FacadeGame>();

        Thread hiloCliente = new Thread(ClienteLogic);
        hiloCliente.Start();

        
    }

    // Update is called once per frame
    void Update()
    {
        
    }

    public void ClienteLogic()
    {

        listen = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);
        connect = new IPEndPoint(IPAddress.Parse("127.0.0.1"), 5000);

        listen.Connect(connect);

        EnviarMensaje("Empezar#0#0");

        while (true)
        {
            byte[] rcvLenBytes = new byte[4];
            listen.Receive(rcvLenBytes);
            int rcvLen = System.BitConverter.ToInt32(rcvLenBytes, 0);
            byte[] rcvBytes = new byte[rcvLen];
            listen.Receive(rcvBytes);
            String recibido = System.Text.Encoding.ASCII.GetString(rcvBytes);

            //Debug.Log(recibido);

            mensaje = recibido.Split('#');

            GamaManager.GetInstancia().ejeccucion = true;
        }


    }

    public void EjeccucionCliente()
    {

        
        switch (mensaje[0])
        {
            case "Reto":
                GamaManager.GetInstancia().SetTimer();
                GamaManager.GetInstancia().QuitarWinner();

                RespawnJugadores(GamaManager.GetInstancia().GetJugadores());

                if (mensaje[1].Equals("BST"))
                {
                    GamaManager.GetInstancia().SetBTS(true);
                    GamaManager.GetInstancia().SetChallenge("BST");
                    facadeGame.BTS();
                    
                }
                else if (mensaje[1].Equals("AVL"))
                {
                    GamaManager.GetInstancia().SetAVL(true);
                    GamaManager.GetInstancia().SetChallenge("AVL");
                    facadeGame.AVL();

                }
                else if (mensaje[1].Equals("BTree"))
                {
                    GamaManager.GetInstancia().SetB(true);
                    GamaManager.GetInstancia().SetChallenge("BTree");
                    facadeGame.B();

                }
                else if (mensaje[1].Equals("Splay"))
                {
                    GamaManager.GetInstancia().SetSPLAY(true);
                    GamaManager.GetInstancia().SetChallenge("Splay");
                    facadeGame.SPLAY();

                }

                break;

            case "Token":

                if (mensaje[1].Equals("BST"))
                {
                    tokenSpawner.GetComponent<TokenSpawner>().spawntoken("BST", Convert.ToInt32(mensaje[2]));
                }
                else if (mensaje[1].Equals("AVL"))
                {
                    tokenSpawner.GetComponent<TokenSpawner>().spawntoken("AVL", Convert.ToInt32(mensaje[2]));
                }
                else if (mensaje[1].Equals("Splay"))
                {
                    tokenSpawner.GetComponent<TokenSpawner>().spawntoken("Splay", Convert.ToInt32(mensaje[2]));
                }
                else if (mensaje[1].Equals("BTree"))
                {
                    tokenSpawner.GetComponent<TokenSpawner>().spawntoken("BTree", Convert.ToInt32(mensaje[2]));
                }
                break;

            case "Poder":
                if (mensaje[1].Equals("Escudo"))
                {
                    tokenSpawner.GetComponent<TokenSpawner>().spawntoken("Esucudo", Convert.ToInt32(mensaje[2]));
                }
                else if (mensaje[1].Equals("Ataque"))
                {
                    tokenSpawner.GetComponent<TokenSpawner>().spawntoken("Ataque", Convert.ToInt32(mensaje[2]));
                }
                else if (mensaje[1].Equals("Salto"))
                {
                    tokenSpawner.GetComponent<TokenSpawner>().spawntoken("Salto", Convert.ToInt32(mensaje[2]));
                }
                else if (mensaje[1].Equals("Vida"))
                {
                    tokenSpawner.GetComponent<TokenSpawner>().spawntoken("Vida", Convert.ToInt32(mensaje[2]));
                }
                break;

            case "Arbol":

                Debug.Log(mensaje[2]);
                
                //arregloInt = JsonUtility.FromJson<ArregloInt>(mensaje[2]);

                string[] arreglo= mensaje[2].Split(',');


                arreglo[0] = arreglo[0].Trim('[');
                arreglo[arreglo.Length-1] = arreglo[arreglo.Length - 1].Trim(']');

              
                int[] nodoarray = new int[arreglo.Length];
                 
                for(int i = 0; i < arreglo.Length; i++)
                {
                    nodoarray[i] = Convert.ToInt32(arreglo[i].ToString());
                }

                
                nodos = nodoarray;

                if (GamaManager.GetInstancia().GetBTS())
                {   
                    facadeGame.LlenarArbolBTS(mensaje[1], nodos);
                   
                }
                else if (GamaManager.GetInstancia().GetB())
                {
                    facadeGame.LlenarArbolB(mensaje[1], nodos);
                }
                else if (GamaManager.GetInstancia().GetAVL())
                {
                    facadeGame.LlenarArbolAVL(mensaje[1], nodos);
                }
                else if (GamaManager.GetInstancia().GetSPLAY())
                {
                    facadeGame.LlenarArbolSPLAY(mensaje[1], nodos);
                }

                break;

            case "Ganador":
                Debug.Log(mensaje[1]);
                if (mensaje[1].Equals("Jugador1"))
                {
                    GamaManager.GetInstancia().DisplayWinner("Jugador 1");
                }
                else if (mensaje[1].Equals("Jugador2"))
                {
                    GamaManager.GetInstancia().DisplayWinner("Jugador 2");
                }
                else if (mensaje[1].Equals("Jugador3"))
                {
                    GamaManager.GetInstancia().DisplayWinner("Jugador 3");
                }
                else if (mensaje[1].Equals("Jugador4"))
                {
                    GamaManager.GetInstancia().DisplayWinner("Jugador 4");
                }
                break;
        }
    }

    public void EnviarMensaje(string toSend)
    {
        //Debug.Log(toSend);
        int toSendLen = System.Text.Encoding.ASCII.GetByteCount(toSend);
        byte[] toSendBytes = System.Text.Encoding.ASCII.GetBytes(toSend);
        byte[] toSendLenBytes = System.BitConverter.GetBytes(toSendLen);
        listen.Send(toSendLenBytes);
        listen.Send(toSendBytes);
    }


    public void RespawnJugadores(int jugadores)
    {
        switch (jugadores)
        {
            case 2:
                ControllerSelection.GetInstancia().GetPersonajeSeleccionado()[ControllerSelection.GetInstancia().GetIndex()].SetActive(true);
                ControllerSelection.GetInstancia().ResetVida();
                ControllerSelection2.GetInstancia().GetPersonajeSeleccionado()[ControllerSelection2.GetInstancia().GetIndex()].SetActive(true);
                ControllerSelection2.GetInstancia().ResetVida();
                break;
            case 3:
                ControllerSelection.GetInstancia().GetPersonajeSeleccionado()[ControllerSelection.GetInstancia().GetIndex()].SetActive(true);
                ControllerSelection.GetInstancia().ResetVida();
                ControllerSelection2.GetInstancia().GetPersonajeSeleccionado()[ControllerSelection2.GetInstancia().GetIndex()].SetActive(true);
                ControllerSelection2.GetInstancia().ResetVida();
                ControllerSelection3.GetInstancia().GetPersonajeSeleccionado()[ControllerSelection3.GetInstancia().GetIndex()].SetActive(true);
                ControllerSelection3.GetInstancia().ResetVida();
                break;
            case 4:
                ControllerSelection.GetInstancia().GetPersonajeSeleccionado()[ControllerSelection.GetInstancia().GetIndex()].SetActive(true);
                ControllerSelection.GetInstancia().ResetVida();
                ControllerSelection2.GetInstancia().GetPersonajeSeleccionado()[ControllerSelection2.GetInstancia().GetIndex()].SetActive(true);
                ControllerSelection2.GetInstancia().ResetVida();
                ControllerSelection3.GetInstancia().GetPersonajeSeleccionado()[ControllerSelection3.GetInstancia().GetIndex()].SetActive(true);
                ControllerSelection3.GetInstancia().ResetVida();
                ControllerSelection4.GetInstancia().GetPersonajeSeleccionado()[ControllerSelection4.GetInstancia().GetIndex()].SetActive(true);
                ControllerSelection4.GetInstancia().ResetVida();
                break;
        }
    }
}
