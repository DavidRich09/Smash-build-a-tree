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

    // Start is called before the first frame update
    void Start()
    {

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
