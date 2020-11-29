using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class CambiarPersonaje : MonoBehaviour
{

    private GameObject[] cambiarPersonaje;
    private int index;
    public int numInstancia;

    // Start is called before the first frame update
    void Start()
    {

        cambiarPersonaje = new GameObject[transform.childCount];

        for (int i = 0; i < transform.childCount; i++)
        {
            cambiarPersonaje[i] = transform.GetChild(i).gameObject;

        }

        foreach (GameObject objx in cambiarPersonaje)
        {
            objx.SetActive(false);
        }

        if (cambiarPersonaje[index])
        {
            cambiarPersonaje[index].SetActive(true);
        }

    }

    public void BotonIz()
    {

        cambiarPersonaje[index].SetActive(false);
        index--;
        if(index < 0)
        {
            index = cambiarPersonaje.Length - 1;
        }

        cambiarPersonaje[index].SetActive(true);
        LlamarInstancia();
    }

    public void BotonDerecha()
    {

        cambiarPersonaje[index].SetActive(false);
        index++;
        if (index == cambiarPersonaje.Length)
        {
            index = 0;
        }

        cambiarPersonaje[index].SetActive(true);
        LlamarInstancia();
    }

    public void LlamarInstancia()
    {
        if (numInstancia == 1)
        {
            ControllerSelection.GetInstancia().Cambiar(index);
        } else if (numInstancia == 2)
        {
            ControllerSelection2.GetInstancia().Cambiar(index);
        } else if (numInstancia == 3)
        {
            ControllerSelection3.GetInstancia().Cambiar(index);
        } else if(numInstancia == 4)
        {
            ControllerSelection4.GetInstancia().Cambiar(index); 
        }
    }
}
