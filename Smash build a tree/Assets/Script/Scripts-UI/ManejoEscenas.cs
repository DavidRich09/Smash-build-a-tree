using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class ManejoEscenas : MonoBehaviour
{
    public void CambiarNivel()
    {

        SceneManager.LoadScene("Escenario1");
        Debug.Log("Me estriparon");
    }
}
