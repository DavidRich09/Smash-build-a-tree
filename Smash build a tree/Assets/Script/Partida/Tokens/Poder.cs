using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Poder : MonoBehaviour
{
    public Rigidbody2D rb;
    string tipo;
    

    // Update is called once per frame
    void Update()
    {
        if (rb.position.y < -2)
        {
            Destroy(gameObject);
        }
    }


    public void SetTipo(string tipox)
    {
        tipo = tipox;
    }

    public string GetTipo()
    {
        return tipo;
    }
}
