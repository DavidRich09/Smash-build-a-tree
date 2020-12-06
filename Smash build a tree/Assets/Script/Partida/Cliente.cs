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

    // Start is called before the first frame update
    void Start()
    {
        player1 = ControllerSelection.GetInstancia();
        player2 = ControllerSelection2.GetInstancia();
        player3 = ControllerSelection3.GetInstancia();
        player4 = ControllerSelection4.GetInstancia();

        facadeGame = FindObjectOfType<FacadeGame>();

        listen = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);
        connect = new IPEndPoint(IPAddress.Parse("127.0.0.1"), 5000);

        Thread hiloCliente = new Thread(ClienteLogic);
        hiloCliente.Start();

        
    }

    // Update is called once per frame
    void Update()
    {
        
    }

    public void ClienteLogic()
    {

        listen.Connect(connect);

        while (true)
        {
            byte[] rcvLenBytes = new byte[4];
            listen.Receive(rcvLenBytes);
            int rcvLen = System.BitConverter.ToInt32(rcvLenBytes, 0);
            byte[] rcvBytes = new byte[rcvLen];
            listen.Receive(rcvBytes);
            String recibido = System.Text.Encoding.ASCII.GetString(rcvBytes);

            Debug.Log(recibido);

            mensaje = recibido.Split('#');

            switch (mensaje[0])
            {
                case "Reto":

                    if (mensaje[1].Equals("BTS"))
                    {
                        GamaManager.GetInstancia().SetBTS(true);
                        facadeGame.BTS();

                    } else if (mensaje[1].Equals("AVL"))
                    {
                        GamaManager.GetInstancia().SetAVL(true);
                        facadeGame.AVL();

                    } else if (mensaje[1].Equals("B"))
                    {
                        GamaManager.GetInstancia().SetB(true);
                        facadeGame.B();

                    } else if (mensaje[1].Equals("SPLAY"))
                    {
                        GamaManager.GetInstancia().SetSPLAY(true);
                        facadeGame.SPLAY();

                    }

                    break;

                case "Token":
                    break;

                case "Poder":
                    break;

                case "Arbol":

                    if (GamaManager.GetInstancia().GetBTS())
                        
                    {
                        //facadeGame.LlenarArbolBTS();
                    } else if (GamaManager.GetInstancia().GetB())
                    {

                    } else if (GamaManager.GetInstancia().GetAVL())
                    {

                    } else if (GamaManager.GetInstancia().GetSPLAY())
                    {

                    }

                    break;
            }

        }


    }

    public void EnviarMensaje(string toSend)
    {
        int toSendLen = System.Text.Encoding.ASCII.GetByteCount(toSend);
        byte[] toSendBytes = System.Text.Encoding.ASCII.GetBytes(toSend);
        byte[] toSendLenBytes = System.BitConverter.GetBytes(toSendLen);
        listen.Send(toSendLenBytes);
        listen.Send(toSendBytes);
    }
}
