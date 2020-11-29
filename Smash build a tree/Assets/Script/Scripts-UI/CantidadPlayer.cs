using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class CantidadPlayer : MonoBehaviour
{

    private GameObject seleccion3;
    private GameObject seleccion4;
    private GameObject jugador3;
    private GameObject jugador4;

    // Start is called before the first frame update
    void Start()
    {

        seleccion3 = GameObject.Find("Player3");
        seleccion4 = GameObject.Find("Player4");
        jugador3 = GameObject.Find("Jugador 3");
        jugador4 = GameObject.Find("Jugador 4");

    }

    public void Jugadores2()
    {
        seleccion3.SetActive(false);
        seleccion4.SetActive(false);
        jugador3.SetActive(false);
        jugador4.SetActive(false);
    }

    public void Jugadores3()
    {
        seleccion3.SetActive(true);
        seleccion4.SetActive(false);
        jugador3.SetActive(true);
        jugador4.SetActive(false);
    }

    public void Jugadores4()
    {
        seleccion3.SetActive(true);
        seleccion4.SetActive(true);
        jugador3.SetActive(true);
        jugador4.SetActive(true);
    }
}
