using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class ManejoEscenas : MonoBehaviour
{

    public GameObject panelPrincipal;
    public GameObject panelSeleccion;

    void Start()
    {


    }
    public void CambiarNivel()
    {

        SceneManager.LoadScene("Escenario1");
        
    }

    public void CambiarSeleccion()
    {
        SceneManager.LoadScene("Seleccion");
    }
}
