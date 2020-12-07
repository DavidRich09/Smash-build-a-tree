using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class SPLAY : MonoBehaviour
{
    private GameObject[] nodos;

    // Start is called before the first frame update
    void Start()
    {
        nodos = new GameObject[transform.childCount];

        for (int i = 0; i < transform.childCount; i++)
        {
            nodos[i] = transform.GetChild(i).gameObject;
        }

        foreach (GameObject nodo in nodos)
        {
            nodo.SetActive(false);
        }

    }

    // Update is called once per frame
    void Update()
    {


    }

    public void UpdateNodo(int[] nodosActivos)
    {

        for (int i = 0; i < transform.childCount; i++)
        {

            nodos[i].SetActive(false);

            if (nodosActivos[i] != 0)
            {
                nodos[i].SetActive(true);
            }
        }
    }

    public void ReiniciarArbol()
    {
        foreach (GameObject x in nodos)
        {
            x.SetActive(false);
        }
    }
}
