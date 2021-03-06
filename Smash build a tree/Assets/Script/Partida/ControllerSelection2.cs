﻿using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class ControllerSelection2 : MonoBehaviour
{
    public static ControllerSelection2 instancia;
    private GameObject[] personajeSeleccionado;
    private int index;
    public int vidas = 3;

    void Awake()
    {

        if (instancia == null)
        {
            instancia = this;
            DontDestroyOnLoad(gameObject);
            Hijos();

        }
        else if (instancia != this)
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

    }

    public static ControllerSelection2 GetInstancia()
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

    public void ArbolNodos(int index, int[] listaNodos)

    {
        Debug.Log("Llego Arboles Nodo");

        GameObject arbol = transform.GetChild(index).gameObject;

        if (index == 5)
        {
            arbol.GetComponent<BTS>().UpdateNodo(listaNodos);
        }
        else if (index == 6)
        {
            arbol.GetComponent<AVL>().UpdateNodo(listaNodos);
        }
        else if (index == 7)
        {
            arbol.GetComponent<B>().UpdateNodo(listaNodos);
        }
        else if (index == 8)
        {
            arbol.GetComponent<SPLAY>().UpdateNodo(listaNodos);
        }

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


    public void VaciarArbol(int index)
    {

        GameObject arbol = transform.GetChild(index).gameObject;

        if (index == 5)
        {
            arbol.GetComponent<BTS>().ReiniciarArbol();
        }
        else if (index == 6)
        {
            arbol.GetComponent<AVL>().ReiniciarArbol();
        }
        else if (index == 7)
        {
            arbol.GetComponent<B>().ReiniciarArbol();
        }
        else if (index == 8)
        {
            arbol.GetComponent<SPLAY>().ReiniciarArbol();
        }

    }

    public void ResetVida()
    {
        vidas = 3;
    }
}
