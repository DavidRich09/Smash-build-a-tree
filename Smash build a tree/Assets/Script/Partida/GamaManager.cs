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

    public EstadoJuego gamestate = EstadoJuego.inGame;

    public void Awake()
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

}
