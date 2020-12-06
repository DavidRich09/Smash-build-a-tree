using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class ControllerSelection : MonoBehaviour
{

    public static ControllerSelection instancia;
    private GameObject[] personajeSeleccionado;
    private int index = 0;
    public int vidas = 3;

    void Awake()
    {

        if (instancia == null)
        {
            instancia = this;
            DontDestroyOnLoad(gameObject);
            Hijos();

        } else if (instancia != this)
        {

            Destroy(gameObject);

           
        }

    }

    public void Hijos()
    {

        personajeSeleccionado = new GameObject[transform.childCount];

        for (int i = 0; i < transform.childCount; i++)
        {
            personajeSeleccionado[i] = transform.GetChild(i).gameObject;
        }

        foreach (GameObject personaje in personajeSeleccionado)
        {
            personaje.SetActive(false);
        }

        if (personajeSeleccionado[index])
        {
            personajeSeleccionado[index].SetActive(true);
        }

    }

    
    void Update()
    {
        //ArbolNodos();
    }

    public static ControllerSelection GetInstancia()
    {
        return instancia;
    }

    public void Cambiar(int i)
    {
        personajeSeleccionado[index].SetActive(false);
        index = i;
        personajeSeleccionado[index].SetActive(true);
    }

    public GameObject[] GetPersonajeSeleccionado()
    {
        return personajeSeleccionado;
    }

    public int GetIndex()
    {
        return index;
    }

    public void ArbolNodos(int index)
    {
        GameObject arbol = transform.GetChild(index).gameObject;
        //arbol.GetComponent<BTS>().prueba();
    }


    public void SumarVida()
    {
        vidas += 1;
        if (vidas > 3)
        {
            vidas = 3;
        }
    }

    public void RestarVida()
    {
        vidas -= 1;
    }

    public int GetVidas()
    {
        return vidas;
    }
}
