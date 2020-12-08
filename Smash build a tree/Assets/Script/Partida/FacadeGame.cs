using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class FacadeGame : MonoBehaviour
{
    private GameObject Objectplayer1;
    private GameObject Objectplayer2;
    private GameObject Objectplayer3;
    private GameObject Objectplayer4;
    private ControllerSelection instancia1;
    private ControllerSelection2 instancia2;
    private ControllerSelection3 instancia3;
    private ControllerSelection4 instancia4;


    // Start is called before the first frame update
    void Start()
    {
       
        Objectplayer1 = GameObject.Find("Jugador1");
        Objectplayer2 = GameObject.Find("Jugador2");
        Objectplayer3 = GameObject.Find("Jugador3");
        Objectplayer4 = GameObject.Find("Jugador4");
        instancia1 = ControllerSelection.GetInstancia();
        instancia2 = ControllerSelection2.GetInstancia();
        instancia3 = ControllerSelection3.GetInstancia();
        instancia4 = ControllerSelection4.GetInstancia();

    }

    // Update is called once per frame
    void Update()
    {

    }

    public void IniciarPartida()
    {

        if (Objectplayer1.activeSelf)
        {

            instancia1.GetPersonajeSeleccionado()[instancia1.GetIndex()].GetComponent<Rigidbody2D>().bodyType = RigidbodyType2D.Dynamic;
        }

        if (Objectplayer2.activeSelf)
        {
            instancia2.GetPersonajeSeleccionado()[instancia2.GetIndex()].GetComponent<Rigidbody2D>().bodyType = RigidbodyType2D.Dynamic;
        }

        if (Objectplayer3.activeSelf)
        {
            instancia3.GetPersonajeSeleccionado()[instancia3.GetIndex()].GetComponent<Rigidbody2D>().bodyType = RigidbodyType2D.Dynamic;
        }

        if (Objectplayer4.activeSelf)
        {
            instancia4.GetPersonajeSeleccionado()[instancia4.GetIndex()].GetComponent<Rigidbody2D>().bodyType = RigidbodyType2D.Dynamic;
        }

    }

    public void TerminarPartida()
    {
        Destroy(Objectplayer1);
        Destroy(Objectplayer2);
        Destroy(Objectplayer3);
        Destroy(Objectplayer4);
    }


    public void BTS()
    {
        if (Objectplayer1.activeSelf)
        {
            GameObject hijo = Objectplayer1.transform.GetChild(5).gameObject;

            hijo.SetActive(true);

        }

        if (Objectplayer2.activeSelf)
        {
            GameObject hijo = Objectplayer2.transform.GetChild(5).gameObject;

            hijo.SetActive(true);
        }

        if (ControllerSelection3.GetInstancia().gameObject.activeSelf)
        {
            GameObject hijo = Objectplayer3.transform.GetChild(5).gameObject;

            hijo.SetActive(true);
        }

        if (ControllerSelection4.GetInstancia().gameObject.activeSelf)
        {
            GameObject hijo = Objectplayer4.transform.GetChild(5).gameObject;

            hijo.SetActive(true);
        }
    }

    public void B()
    {
        if (Objectplayer1.activeSelf)
        {
            GameObject hijo = Objectplayer1.transform.GetChild(7).gameObject;

            hijo.SetActive(true);

        }

        if (Objectplayer2.activeSelf)
        {
            GameObject hijo = Objectplayer2.transform.GetChild(7).gameObject;

            hijo.SetActive(true);
        }

        if (ControllerSelection3.GetInstancia().gameObject.activeSelf)
        {
            GameObject hijo = Objectplayer3.transform.GetChild(7).gameObject;

            hijo.SetActive(true);
        }

        if (ControllerSelection4.GetInstancia().gameObject.activeSelf)
        {
            GameObject hijo = Objectplayer4.transform.GetChild(7).gameObject;

            hijo.SetActive(true);
        }
    }

    public void AVL()
    {
        if (Objectplayer1.activeSelf)
        {
            GameObject hijo = Objectplayer1.transform.GetChild(6).gameObject;

            hijo.SetActive(true);

        }

        if (Objectplayer2.activeSelf)
        {
            GameObject hijo = Objectplayer2.transform.GetChild(6).gameObject;

            hijo.SetActive(true);
        }

        if (ControllerSelection3.GetInstancia().gameObject.activeSelf)
        {
            GameObject hijo = Objectplayer3.transform.GetChild(6).gameObject;

            hijo.SetActive(true);
        }

        if (ControllerSelection4.GetInstancia().gameObject.activeSelf)
        {
            GameObject hijo = Objectplayer4.transform.GetChild(6).gameObject;

            hijo.SetActive(true);
        }
    }

    public void SPLAY()
    {
        if (Objectplayer1.activeSelf)
        {
            GameObject hijo = Objectplayer1.transform.GetChild(8).gameObject;

            hijo.SetActive(true);

        }

        if (Objectplayer2.activeSelf)
        {
            GameObject hijo = Objectplayer2.transform.GetChild(8).gameObject;

            hijo.SetActive(true);
        }

        if (ControllerSelection3.GetInstancia().gameObject.activeSelf)
        {
            GameObject hijo = Objectplayer3.transform.GetChild(8).gameObject;

            hijo.SetActive(true);
        }

        if (ControllerSelection4.GetInstancia().gameObject.activeSelf)
        {
            GameObject hijo = Objectplayer4.transform.GetChild(8).gameObject;

            hijo.SetActive(true);
        }
    }

    public void EndBTS(int index)
    {
        if (Objectplayer1.activeSelf)
        {

            GameObject hijo = Objectplayer1.transform.GetChild(5).gameObject;

            ControllerSelection.GetInstancia().VaciarArbol(index);

            hijo.SetActive(false);

        }

        if (Objectplayer2.activeSelf)
        {
            GameObject hijo = Objectplayer2.transform.GetChild(5).gameObject;

            ControllerSelection2.GetInstancia().VaciarArbol(index);

            hijo.SetActive(false);
        }

        if (ControllerSelection3.GetInstancia().gameObject.activeSelf)
        {
            GameObject hijo = Objectplayer3.transform.GetChild(5).gameObject;

            ControllerSelection3.GetInstancia().VaciarArbol(index);

            hijo.SetActive(false);
        }

        if (ControllerSelection4.GetInstancia().gameObject.activeSelf)
        {
            GameObject hijo = Objectplayer4.transform.GetChild(5).gameObject;

            ControllerSelection4.GetInstancia().VaciarArbol(index);

            hijo.SetActive(false);
        }
    }

    public void EndB(int index)
    {
        if (Objectplayer1.activeSelf)
        {
            GameObject hijo = Objectplayer1.transform.GetChild(7).gameObject;

            ControllerSelection.GetInstancia().VaciarArbol(index);

            hijo.SetActive(false);

        }

        if (Objectplayer2.activeSelf)
        {
            GameObject hijo = Objectplayer2.transform.GetChild(7).gameObject;

            ControllerSelection2.GetInstancia().VaciarArbol(index);

            hijo.SetActive(false);
        }

        if (Objectplayer3.activeSelf)
        {
            GameObject hijo = Objectplayer3.transform.GetChild(7).gameObject;

            ControllerSelection3.GetInstancia().VaciarArbol(index);

            hijo.SetActive(false);
        }

        if (Objectplayer4.activeSelf)
        {
            GameObject hijo = Objectplayer4.transform.GetChild(7).gameObject;

            ControllerSelection4.GetInstancia().VaciarArbol(index);

            hijo.SetActive(false);
        }
    }
    
    public void EndAVL(int index)
    {
        if (Objectplayer1.activeSelf)
        {
            GameObject hijo = Objectplayer1.transform.GetChild(6).gameObject;

            ControllerSelection.GetInstancia().VaciarArbol(index);

            hijo.SetActive(false);

        }

        if (Objectplayer2.activeSelf)
        {
            GameObject hijo = Objectplayer2.transform.GetChild(6).gameObject;

            ControllerSelection2.GetInstancia().VaciarArbol(index);

            hijo.SetActive(false);
        }

        if (Objectplayer3.activeSelf)
        {
            GameObject hijo = Objectplayer3.transform.GetChild(6).gameObject;

            ControllerSelection3.GetInstancia().VaciarArbol(index);

            hijo.SetActive(false);
        }

        if (Objectplayer4.activeSelf)
        {
            GameObject hijo = Objectplayer4.transform.GetChild(6).gameObject;

            ControllerSelection4.GetInstancia().VaciarArbol(index);

            hijo.SetActive(false);
        }
    }

    public void EndSPLAY(int index)
    {
        if (Objectplayer1.activeSelf)
        {
            GameObject hijo = Objectplayer1.transform.GetChild(8).gameObject;

            ControllerSelection.GetInstancia().VaciarArbol(index);

            hijo.SetActive(false);

        }

        if (Objectplayer2.activeSelf)
        {
            GameObject hijo = Objectplayer2.transform.GetChild(8).gameObject;

            ControllerSelection2.GetInstancia().VaciarArbol(index);

            hijo.SetActive(false);
        }

        if (Objectplayer3.activeSelf)
        {
            GameObject hijo = Objectplayer3.transform.GetChild(8).gameObject;

            ControllerSelection3.GetInstancia().VaciarArbol(index);

            hijo.SetActive(false);
        }

        if (Objectplayer4.activeSelf)
        {
            GameObject hijo = Objectplayer4.transform.GetChild(8).gameObject;

            ControllerSelection4.GetInstancia().VaciarArbol(index);

            hijo.SetActive(false);
        }
    }

    public void LlenarArbolBTS(string jugador, int[] nodos)
    {
        
        switch (jugador)
        {
            case "Jugador1":
                ControllerSelection.GetInstancia().ArbolNodos(5, nodos);
                break;

            case "Jugador2":
                ControllerSelection2.GetInstancia().ArbolNodos(5, nodos);
                break;

            case "Jugador3":
                ControllerSelection3.GetInstancia().ArbolNodos(5, nodos);
                break;

            case "Jugador4":
                ControllerSelection4.GetInstancia().ArbolNodos(5, nodos);
                break;
        }
    }

    public void LlenarArbolB(string jugador, int[] nodos)
    {
        switch (jugador)
        {
            case "Jugador1":
                ControllerSelection.GetInstancia().ArbolNodos(7, nodos);
                break;

            case "Jugador2":
                ControllerSelection2.GetInstancia().ArbolNodos(7, nodos);
                break;

            case "Jugador3":
                ControllerSelection3.GetInstancia().ArbolNodos(7, nodos);
                break;

            case "Jugador4":
                ControllerSelection4.GetInstancia().ArbolNodos(7, nodos);
                break;
        }
    }
    public void LlenarArbolAVL(string jugador, int[] nodos)
    {
        switch (jugador)
        {
            case "Jugador1":
                ControllerSelection.GetInstancia().ArbolNodos(6, nodos);
                break;

            case "Jugador2":
                ControllerSelection2.GetInstancia().ArbolNodos(6, nodos);
                break;

            case "Jugador3":
                ControllerSelection3.GetInstancia().ArbolNodos(6, nodos);
                break;

            case "Jugador4":
                ControllerSelection4.GetInstancia().ArbolNodos(6, nodos);
                break;
        }
    }

    public void LlenarArbolSPLAY(string jugador, int[] nodos)
    {
        switch (jugador)
        {
            case "Jugador1":
                ControllerSelection.GetInstancia().ArbolNodos(8, nodos);
                break;

            case "Jugador2":
                ControllerSelection2.GetInstancia().ArbolNodos(8, nodos);
                break;

            case "Jugador3":
                ControllerSelection3.GetInstancia().ArbolNodos(8, nodos);
                break;

            case "Jugador4":
                ControllerSelection4.GetInstancia().ArbolNodos(8, nodos);
                break;
        }
    }
}


