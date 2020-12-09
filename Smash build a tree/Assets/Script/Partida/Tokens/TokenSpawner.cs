using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class TokenSpawner : MonoBehaviour {


    public GameObject BST, AVL, B, SPLAY, Shield,Ataque, Salto, Vida;

    public float RmoveSpeed = 1f;
    public float Lmovespeed = -1f;
    float movespeed;

    public Rigidbody2D rb;



    // Start is called before the first frame update
    void Start()
    {
        movespeed = RmoveSpeed;
    }

    // Update is called once per frame
    void Update()
    {
        if (rb.position.x >= 2f)
        {
            movespeed = Lmovespeed;
        }
        else if (rb.position.x <= -3f)
        {
            movespeed = RmoveSpeed;
        }

        rb.velocity = new Vector2(movespeed, 0);

        
    }

    public void spawntoken(string tipo,int valor)
    {
        Debug.Log(tipo);
        if (tipo.Equals("BST"))
        {
               
            Instantiate(BST, transform.position, Quaternion.identity).GetComponent<Token>().SetToken("BST", valor);
        }
        else if (tipo.Equals("AVL"))
        {
            Instantiate(AVL, transform.position, Quaternion.identity).GetComponent<Token>().SetToken("AVL", valor);
               
        }
        else if (tipo.Equals("Splay"))
        {
            Instantiate(SPLAY, transform.position, Quaternion.identity).GetComponent<Token>().SetToken("Splay", valor);
                
        }
        else if(tipo.Equals("BTree"))
        {
            Instantiate(B, transform.position, Quaternion.identity).GetComponent<Token>().SetToken("BTree", valor);
               
        }
        else if (tipo.Equals("Escudo"))
        {
            Instantiate(Shield, transform.position, Quaternion.identity).GetComponent<Poder>().SetTipo("Escudo");
        }
        else if (tipo.Equals("Ataque"))
        {
            Instantiate(Ataque, transform.position, Quaternion.identity).GetComponent<Poder>().SetTipo("Ataque");
        }
        else if (tipo.Equals("Salto"))
        {
            Instantiate(Salto, transform.position, Quaternion.identity).GetComponent<Poder>().SetTipo("Salto");
        }
        else if (tipo.Equals("Vida"))
        {
            Instantiate(Vida, transform.position, Quaternion.identity).GetComponent<Poder>().SetTipo("Vida");
        }

    }

}
