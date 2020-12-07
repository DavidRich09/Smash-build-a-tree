using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public enum EstadoJuego
{
    inGame,
    pause,
    gameOver
}

public class GamaManager : MonoBehaviour
{

    public static GamaManager instancia;

    public FacadeGame facadeGame;

    public EstadoJuego gamestate = EstadoJuego.inGame;

    public bool BTS = false;
    public bool AVL = false;
    public bool B = false;
    public bool SPLAY = false;


    void Awake()
    {
        if (instancia == null)
        {
            instancia = this;
        }
        else
        {
            Destroy(gameObject);
        }
    }

    void Start()
    {
        facadeGame = FindObjectOfType<FacadeGame>();
        facadeGame.IniciarPartida();
    }

    private void Update()
    {
        if (Input.GetKeyDown(KeyCode.P))
        {
            PauseGame();
        }

        
    
    }

    public void PauseGame()
    {
        SetGameState(EstadoJuego.pause);
    }

    public void SetGameState(EstadoJuego estado)
    {
        if (estado == EstadoJuego.pause)
        {
            gamestate = estado;
        }
    }

    public static GamaManager GetInstancia()
    {
        return instancia;
    }

    public void SetBTS(bool valor)
    {
        BTS = valor;
    }

    public void SetAVL(bool valor)
    {
        AVL = valor;
    }

    public void SetB(bool valor)
    {
        B = valor;
    }

    public void SetSPLAY(bool valor)
    {
        SPLAY = valor;
    }

    public bool GetBTS()
    {
        return BTS;
    }

    public bool GetAVL()
    {
        return AVL;
    }

    public bool GetB()
    {
        return B;
    }

    public bool GetSPLAY()
    {
        return SPLAY;
    }
}
