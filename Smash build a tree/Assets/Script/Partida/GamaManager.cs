using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public enum EstadoJuego
{
    inGame,
    pause,
    gameOver
}

public class GamaManager : MonoBehaviour
{

    public static GamaManager instancia;
    public Cliente cliente;
    public FacadeGame facadeGame;
    

    public EstadoJuego gamestate = EstadoJuego.inGame;

    public bool BTS = false;
    public bool AVL = false;
    public bool B = false;
    public bool SPLAY = false;

    public float timeleft;
    public Text timer;
    public Text challengeText;
    public string currentChallenge;
    public Text ganadorDisplay;

    public int jugadores;

    public bool ejeccucion;


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

        cliente = FindObjectOfType<Cliente>();

        if (ControllerSelection3.GetInstancia().gameObject.activeSelf && ControllerSelection4.GetInstancia().gameObject.activeSelf)
        {
            jugadores = 4;
        }
        else if (ControllerSelection3.GetInstancia().gameObject.activeSelf && !ControllerSelection4.GetInstancia().gameObject.activeSelf)
        {
            jugadores = 3;
        }
        else
        {
            jugadores = 2;
        }
        
    }

    private void Update()
    {

        if (GamaManager.GetInstancia().ejeccucion)
        {
            cliente.EjeccucionCliente();
            GamaManager.GetInstancia().ejeccucion = false;
        }

        if (Input.GetKeyDown(KeyCode.P))
        {
            PauseGame();
        }

        timeleft -= 1 * Time.deltaTime;
        if (timeleft <= 0f)
        {
            timeleft = 0f;
        }
        int minutos = (int)(timeleft / 60);
        int segundos =(int) timeleft - (minutos * 60);

        if (segundos < 10)
        {
            timer.text = minutos.ToString() + ":" + "0" + segundos.ToString();
        }
        else
        {
            timer.text = minutos.ToString() + ":" + segundos.ToString();
        }


        challengeText.text =  currentChallenge;

        if(timeleft <= 0)
        {
            BTS = false;
            AVL = false;
            B = false;
            SPLAY = false;
            if (currentChallenge.Equals("BST"))
            {
                facadeGame.EndBTS(5);
                
            }
            else if (currentChallenge.Equals("AVL"))
            {
                facadeGame.EndAVL(6);
                
            }
            else if (currentChallenge.Equals("Splay"))
            {
                facadeGame.EndSPLAY(8);
                
            }
            else if (currentChallenge.Equals("BTree"))
            {
                facadeGame.EndB(7);
               
            }
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

    public void SetChallenge(string challengex)
    {
        currentChallenge = challengex;
    }

    public void SetTimer()
    {
        timeleft = 60f;
    }


    public void DisplayWinner(string ganadorx)
    {
        ganadorDisplay.text = ganadorx + "ha ganado!!";
    }

    public void QuitarWinner()
    {
        ganadorDisplay.text = "";
    }

    public int GetJugadores()
    {
        return jugadores;
    }
}
