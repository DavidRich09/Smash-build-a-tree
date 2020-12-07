﻿using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Token : MonoBehaviour
{

    string tipo;
    int valor;

    public Rigidbody2D rb;
    public GameObject placer;
    TextMesh txt;


    // Start is called before the first frame update
    void Start()
    {

    }

    // Update is called once per frame
    void Update()
    {
        if (rb.position.y < -2)
        {
            Destroy(gameObject);
        }
    }

    public void SetToken(string tipox, int valorx)
    {
        tipo = tipox;
        valor = valorx;
        if (tipo.Equals("BST") || tipo.Equals("SPLAY")|| tipo.Equals("B")|| tipo.Equals("AVL"))
        {
            txt = placer.GetComponent<TextMesh>();
            txt.text = valor.ToString();
        }
    }
}
