using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class ControllerSelection3 : MonoBehaviour
{
    public static ControllerSelection3 instancia;
    private GameObject[] personajeSeleccionado;
    private int index;

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

            instancia = ControllerSelection3.GetInstancia();

            Debug.Log(instancia.personajeSeleccionado[instancia.index]);

            instancia.personajeSeleccionado[instancia.index].GetComponent<Rigidbody2D>().bodyType = RigidbodyType2D.Dynamic;
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

    public static ControllerSelection3 GetInstancia()
    {
        return instancia;
    }

    public void Cambiar(int i)
    {
        personajeSeleccionado[index].SetActive(false);
        index = i;
        personajeSeleccionado[index].SetActive(true);
    }
}
