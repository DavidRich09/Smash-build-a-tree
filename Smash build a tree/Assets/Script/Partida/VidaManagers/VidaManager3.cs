using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class VidaManager3 : MonoBehaviour
{
    private ControllerSelection3 controller;
    private GameObject jugador;

    public GameObject Tag;
    public GameObject vida1;
    public GameObject vida2;
    public GameObject vida3;


    // Start is called before the first frame update
    void Start()
    {
        controller = ControllerSelection3.GetInstancia();
        if (controller.gameObject.activeSelf)
        {
            jugador = controller.transform.GetChild(controller.GetIndex()).gameObject;
            Tag.SetActive(true);
            vida1.SetActive(true);
            vida2.SetActive(true);
            vida3.SetActive(true);
        }
        controller = controller.GetComponent<ControllerSelection3>();
    }

    // Update is called once per frame
    void Update()
    {
        if (controller.GetVidas() > 0 && !jugador.activeSelf)
        {
            jugador.SetActive(true);
        }

        if (jugador.transform.position.y < -2.5f)
        {
            controller.RestarVida();
            if (controller.GetVidas() > 0)
            {
                jugador.transform.position = new Vector3(-0.664f, 1.787f, -2.723413f);
            }
            else
            {
                jugador.transform.position = new Vector3(-0.664f, 1.787f, -2.723413f);
                jugador.SetActive(false);
            }

        }


        switch (controller.GetVidas())
        {
            case 0:
                vida1.SetActive(false);
                vida2.SetActive(false);
                vida3.SetActive(false);
                break;
            case 1:
                vida1.SetActive(true);
                vida2.SetActive(false);
                vida3.SetActive(false);
                break;
            case 2:
                vida1.SetActive(true);
                vida2.SetActive(true);
                vida3.SetActive(false);
                break;
            case 3:
                vida1.SetActive(true);
                vida2.SetActive(true);
                vida3.SetActive(true);
                break;
        }

    }
}
